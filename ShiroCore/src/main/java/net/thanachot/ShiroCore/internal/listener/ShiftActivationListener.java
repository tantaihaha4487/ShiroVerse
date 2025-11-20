package net.thanachot.ShiroCore.internal.listener;

import net.kyori.adventure.text.Component;
import net.thanachot.ShiroCore.api.event.ShiftActivationEvent;
import net.thanachot.ShiroCore.api.event.ShiftProgressEvent;
import net.thanachot.ShiroCore.api.text.ActionbarMessage;
import net.thanachot.ShiroCore.internal.handler.ShiftActivationHandler;
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
 * Internal listener that detects player sneak events and fires the appropriate shift-activation events.
 * This class is not intended for public use.
 */
public class ShiftActivationListener implements Listener {

    private final PlayerShiftTracker tracker = new PlayerShiftTracker();
    private final ShiftActivationService shiftActivationService;
    private final int maxProgress;

    /**
     * Constructs a new ShiftActivationListener.
     *
     * @param shiftActivationService The service to use for checking listenable items and getting handlers.
     * @param maxProgress            The number of shift presses required for activation.
     */
    public ShiftActivationListener(@NotNull ShiftActivationService shiftActivationService, int maxProgress) {
        this.shiftActivationService = shiftActivationService;
        this.maxProgress = maxProgress;
    }

    @EventHandler
    public void onShift(PlayerToggleSneakEvent event) {
        if (!event.isSneaking()) return;

        final Player player = event.getPlayer();
        final HandledItem handledItem = getHandledItem(player);

        if (handledItem == null) {
            return;
        }

        final int currentProgress = tracker.recordPress(player.getUniqueId());
        if (currentProgress <= 0) {
            return; // On cooldown or no progress
        }

        if (currentProgress >= maxProgress) {
            handleActivation(player, handledItem.hand(), handledItem.item());
        } else {
            handleProgress(player, currentProgress, handledItem.hand(), handledItem.item());
        }
    }

    /**
     * Handles the final activation when progress is complete.
     */
    private void handleActivation(@NotNull Player player, @NotNull EquipmentSlot hand, @NotNull ItemStack item) {
        final ShiftActivationEvent activationEvent = new ShiftActivationEvent(player, maxProgress, System.currentTimeMillis(), hand, item);
        Bukkit.getPluginManager().callEvent(activationEvent);

        if (activationEvent.isCancelled()) {
            return;
        }

        final ItemStack newItem = activationEvent.getItem();
        applyItemToHand(player, hand, newItem);

        final ShiftActivationHandler handler = shiftActivationService.getHandler(item.getType());
        if (handler != null) {
            handler.onActivate(activationEvent);
        }

        tracker.reset(player.getUniqueId());
    }

    /**
     * Handles the progress updates before activation is complete.
     */
    private void handleProgress(@NotNull Player player, int currentProgress, @NotNull EquipmentSlot hand, @NotNull ItemStack item) {
        final ShiftProgressEvent progressEvent = new ShiftProgressEvent(player, currentProgress, maxProgress, hand, item);
        Bukkit.getPluginManager().callEvent(progressEvent);

        if (progressEvent.isCancelled()) {
            return;
        }

        Component msg = progressEvent.getActionBarMessage();
        if (msg == null) {
            msg = ActionbarMessage.getLoadingBar(progressEvent.getRawPercentage());
        }
        player.sendActionBar(msg);
    }

    /**
     * Determines which hand holds a listenable item.
     *
     * @return A {@link HandledItem} record if a listenable item is found, otherwise {@code null}.
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
        return shiftActivationService.isRegistered(item.getType());
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
