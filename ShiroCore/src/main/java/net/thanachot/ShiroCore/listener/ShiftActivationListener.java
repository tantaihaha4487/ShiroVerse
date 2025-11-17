package net.thanachot.ShiroCore.listener;

import net.thanachot.ShiroCore.event.ShiftActivationEvent;
import net.thanachot.ShiroCore.event.ShiftProgressEvent;
import net.thanachot.ShiroCore.handler.ShiftActivationHandler;
import net.thanachot.ShiroCore.system.ShiftActivationService;
import net.thanachot.ShiroCore.util.PlayerShiftTracker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Internal listener that detects player sneak events and fires the appropriate shift-activation events.
 * This class is not intended for public use.
 */
public class ShiftActivationListener implements Listener {

    private final PlayerShiftTracker tracker = new PlayerShiftTracker();
    private final ShiftActivationService shiftActivationService;

    /**
     * Constructs a new ShiftActivationListener.
     *
     * @param shiftActivationService The service to use for checking listenable items and getting handlers.
     */
    public ShiftActivationListener(@NotNull ShiftActivationService shiftActivationService) {
        this.shiftActivationService = shiftActivationService;
    }

    private static void applyItemToHand(@NotNull Player player, @NotNull EquipmentSlot hand, ItemStack item) {
        if (hand == EquipmentSlot.HAND) {
            player.getInventory().setItemInMainHand(item);
        } else {
            player.getInventory().setItemInOffHand(item);
        }
    }

    @EventHandler
    public void onShift(PlayerToggleSneakEvent event) {
        if (!event.isSneaking()) return;

        Player player = event.getPlayer();

        ItemStack main = player.getInventory().getItemInMainHand();
        ItemStack off = player.getInventory().getItemInOffHand();

        EquipmentSlot hand;
        ItemStack item;

        if (isListenableItem(main)) {
            hand = EquipmentSlot.HAND;
            item = main;
        } else if (isListenableItem(off)) {
            hand = EquipmentSlot.OFF_HAND;
            item = off;
        } else {
            return;
        }

        // Track progress (0..100)
        int percent = tracker.recordPress(player.getUniqueId());
        if (percent <= 0) {
            // either on cooldown or no progress — don't fire progress event
            return;
        }

        // Activation reached
        if (percent >= 100) {
            ShiftActivationEvent activationEvent = new ShiftActivationEvent(
                    player,
                    100, // loadingPercentage
                    System.currentTimeMillis(), // timestamp
                    hand,
                    item
            );
            Bukkit.getPluginManager().callEvent(activationEvent);

            if (!activationEvent.isCancelled()) {
                // write back changed item if listener replaced it
                ItemStack newItem = activationEvent.getItem();
                applyItemToHand(player, hand, newItem);

                // Execute the handler for the item
                ShiftActivationHandler handler = shiftActivationService.getHandler(item.getType());
                if (handler != null) {
                    handler.onActivate(activationEvent);
                }

                tracker.reset(player.getUniqueId());
            }
            return;
        }

        // Fire progress event (listeners may override or cancel)
        ShiftProgressEvent progressEvent = new ShiftProgressEvent(player, percent, hand, item);
        Bukkit.getPluginManager().callEvent(progressEvent);

        if (!progressEvent.isCancelled()) {
            String msg = progressEvent.getActionBarMessage();
            if (msg == null) msg = "Loading: " + percent + "%";
            player.sendActionBar(msg);
        }
    }

    private boolean isListenableItem(@NotNull ItemStack item) {
        return shiftActivationService.isRegistered(item.getType());
    }
}
