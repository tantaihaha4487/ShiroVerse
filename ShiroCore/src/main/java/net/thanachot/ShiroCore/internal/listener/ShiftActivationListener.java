package net.thanachot.ShiroCore.internal.listener;

import net.kyori.adventure.text.Component;
import net.thanachot.shiroverse.api.event.ShiftActivationEvent;
import net.thanachot.shiroverse.api.event.ShiftEvent;
import net.thanachot.shiroverse.api.event.ShiftProgressEvent;
import net.thanachot.shiroverse.api.text.ActionbarMessage;
import net.thanachot.shiroverse.api.handler.ShiftActivationHandler;
import net.thanachot.ShiroCore.internal.system.ShiftActivationService;
import net.thanachot.ShiroCore.internal.util.PlayerShiftTracker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Internal listener that detects player sneak events and fires the appropriate
 * shift-activation events.
 * This class is not intended for public use.
 */
public class ShiftActivationListener implements Listener {

    private final PlayerShiftTracker tracker = new PlayerShiftTracker();
    private final ShiftActivationService shiftActivationService;
    private final net.thanachot.ShiroCore.internal.ability.AbilityManagerImpl abilityManager;

    /**
     * Constructs a new ShiftActivationListener.
     *
     * @param shiftActivationService The service to use for checking listenable
     *                               items and getting handlers.
     * @param abilityManager         The ability manager to check for registered
     *                               abilities
     */
    public ShiftActivationListener(@NotNull ShiftActivationService shiftActivationService,
            @NotNull net.thanachot.ShiroCore.internal.ability.AbilityManagerImpl abilityManager) {
        this.shiftActivationService = shiftActivationService;
        this.abilityManager = abilityManager;
    }

    @EventHandler
    public void onShift(PlayerToggleSneakEvent event) {
        if (!event.isSneaking())
            return;

        final Player player = event.getPlayer();
        final HandledItem handledItem = getHandledItem(player);

        if (handledItem == null) {
            return;
        }

        final int currentPressCount = tracker.recordPress(player.getUniqueId());
        if (currentPressCount <= 0) {
            return; // On cooldown or no progress
        }

        int maxProgress = shiftActivationService.getMaxProgress();
        if (currentPressCount >= maxProgress) {
            handleActivation(player, handledItem.hand(), handledItem.item(), event);
        } else {
            handleProgress(player, currentPressCount, handledItem.hand(), handledItem.item());
        }
    }

    /**
     * Handles the final activation when progress is complete.
     */
    private void handleActivation(@NotNull Player player, @NotNull EquipmentSlot hand, @NotNull ItemStack item, @NotNull PlayerToggleSneakEvent p_event) {
        final ShiftActivationEvent activationEvent = new ShiftActivationEvent(player, 100, System.currentTimeMillis(),
                hand, item);

        if (!callEvent(activationEvent)) {
            return;
        }

        final ItemStack newItem = activationEvent.getItem();
        applyItemToHand(player, hand, newItem);

        final ShiftActivationHandler handler = shiftActivationService.getHandler(item.getType());
        if (handler != null) {
            handler.onActivation(player, item, p_event);
        }

        tracker.reset(player.getUniqueId());
    }

    /**
     * Handles the progress updates before activation is complete.
     */
    private void handleProgress(@NotNull Player player, int currentPressCount, @NotNull EquipmentSlot hand,
            @NotNull ItemStack item) {
        int maxProgress = shiftActivationService.getMaxProgress();
        final ShiftProgressEvent progressEvent = new ShiftProgressEvent(player, currentPressCount, maxProgress, hand,
                item);

        if (!callEvent(progressEvent)) {
            return;
        }

        Component msg = progressEvent.getActionBarMessage();
        if (msg == null) {
            msg = ActionbarMessage.getLoadingBar(progressEvent.getRawPercentage());
        }
        player.sendActionBar(msg);
    }

    /**
     * Calls an event and returns whether it should proceed (not cancelled).
     *
     * @param event The event to call.
     * @return True if the event is not cancelled, false otherwise.
     */
    private boolean callEvent(ShiftEvent event) {
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    /**
     * Determines which hand holds a listenable item.
     *
     * @return A {@link HandledItem} record if a listenable item is found, otherwise
     *         {@code null}.
     */
    @Nullable
    private HandledItem getHandledItem(@NotNull Player player) {
        final ItemStack main = player.getInventory().getItemInMainHand();
        if (isListenableItem(main)) {
            return new HandledItem(EquipmentSlot.HAND, main);
        }

        final ItemStack off = player.getInventory().getItemInOffHand();
        if (isListenableItem(off)) {
            return new HandledItem(EquipmentSlot.OFF_HAND, off);
        }

        return null;
    }

    private boolean isListenableItem(@NotNull ItemStack item) {
        // Check old system (Material registration)
        if (shiftActivationService.isRegistered(item.getType())) {
            return true;
        }

        // Check new system (AbilityManager)
        return abilityManager.findAbilityForItem(item).isPresent();
    }

    private void applyItemToHand(@NotNull Player player, @NotNull EquipmentSlot hand, @NotNull ItemStack item) {
        if (hand == EquipmentSlot.HAND) {
            player.getInventory().setItemInMainHand(item);
        } else {
            player.getInventory().setItemInOffHand(item);
        }
    }

    /**
     * A record to hold the hand and the item being handled.
     */
    private record HandledItem(@NotNull EquipmentSlot hand, @NotNull ItemStack item) {
    }
}
