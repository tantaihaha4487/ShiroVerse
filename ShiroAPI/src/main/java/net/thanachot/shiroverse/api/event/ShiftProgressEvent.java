package net.thanachot.shiroverse.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ShiftProgressEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final int loadingPercentage;
    private final EquipmentSlot hand;
    private final ItemStack item;
    private boolean cancelled = false;
    private String actionBarMessage; // null means use default

    public ShiftProgressEvent(Player player, int loadingPercentage, EquipmentSlot hand, ItemStack item) {
        this.player = player;
        this.loadingPercentage = loadingPercentage;
        this.hand = hand;
        this.item = item;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public ItemStack getItem() {
        return item;
    }

    public EquipmentSlot getHand() {
        return hand;
    }

    public int getLoadingPercentage() {
        return loadingPercentage;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
