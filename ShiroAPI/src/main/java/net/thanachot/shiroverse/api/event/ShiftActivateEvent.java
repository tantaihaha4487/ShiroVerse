package net.thanachot.shiroverse.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ShiftActivateEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final int loadingPercentage;
    private final long timestamp;
    private final EquipmentSlot hand;
    private ItemStack item;
    private boolean cancelled = false;

    public ShiftActivateEvent(Player player, int loadingPercentage, long timestamp, EquipmentSlot hand, ItemStack item) {
        this.player = player;
        this.loadingPercentage = loadingPercentage;
        this.timestamp = timestamp;
        this.hand = hand;
        this.item = item;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return player;
    }

    public int getLoadingPercentage() {
        return loadingPercentage;
    }

    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Which hand triggered this event (EquipmentSlot.HAND = main hand, OFF_HAND = off-hand).
     */
    public EquipmentSlot getHand() {
        return hand;
    }

    public ItemStack getItem() {
        return item;
    }

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
