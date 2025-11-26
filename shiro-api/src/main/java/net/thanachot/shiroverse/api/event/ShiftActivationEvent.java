package net.thanachot.shiroverse.api.event;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 * Called when a player successfully completes a shift-activation sequence.
 */
public class ShiftActivationEvent extends ShiftEvent {
    private final int loadingPercentage;
    private final long timestamp;

    public ShiftActivationEvent(Player player, int loadingPercentage, long timestamp, EquipmentSlot hand,
            ItemStack item) {
        super(player, hand, item);
        this.loadingPercentage = loadingPercentage;
        this.timestamp = timestamp;
    }

    /**
     * Gets the final loading percentage, which should be 100.
     *
     * @return The loading percentage.
     */
    public int getLoadingPercentage() {
        return loadingPercentage;
    }

    /**
     * Gets the timestamp of when the event occurred.
     *
     * @return The timestamp.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the item in the player's hand.
     *
     * @param item The new item.
     */
    public void setItem(ItemStack item) {
        this.item = item;
    }
}
