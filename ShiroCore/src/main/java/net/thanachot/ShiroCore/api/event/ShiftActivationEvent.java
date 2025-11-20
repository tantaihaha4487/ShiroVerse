package net.thanachot.ShiroCore.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player successfully completes a shift-activation sequence.
 */
public class ShiftActivationEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final int loadingPercentage;
    private final long timestamp;
    private final EquipmentSlot hand;
    private ItemStack item;
    private boolean cancelled = false;

    public ShiftActivationEvent(Player player, int loadingPercentage, long timestamp, EquipmentSlot hand, ItemStack item) {
        this.player = player;
        this.loadingPercentage = loadingPercentage;
        this.timestamp = timestamp;
        this.hand = hand;
        this.item = item;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * Gets the player who triggered the event.
     *
     * @return The player.
     */
    public Player getPlayer() {
        return player;
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
     * Gets the hand that triggered this event.
     *
     * @return The hand (EquipmentSlot.HAND for main hand, EquipmentSlot.OFF_HAND for off-hand).
     */
    public EquipmentSlot getHand() {
        return hand;
    }

    /**
     * Gets the item that was used for the shift-activation.
     *
     * @return The item.
     */
    public ItemStack getItem() {
        return item;
    }

    /**
     * Sets the item in the player's hand.
     *
     * @param item The new item.
     */
    public void setItem(ItemStack item) {
        this.item = item;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public @NotNull String getEventName() {
        return super.getEventName();
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
