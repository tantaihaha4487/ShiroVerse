package net.thanachot.shiroverse.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Abstract base class for shift-related events.
 */
public abstract class ShiftEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();

    protected final Player player;
    protected final EquipmentSlot hand;
    protected ItemStack item;
    protected boolean cancelled = false;

    protected ShiftEvent(Player player, EquipmentSlot hand, ItemStack item) {
        this.player = player;
        this.hand = hand;
        this.item = item;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return player;
    }

    public EquipmentSlot getHand() {
        return hand;
    }

    public ItemStack getItem() {
        return item;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
