package net.thanachot.ShiroCore.api.event;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player makes progress in a shift-activation sequence.
 */
public class ShiftProgressEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final int currentProgress;
    private final int maxProgress;
    private final EquipmentSlot hand;
    private final ItemStack item;
    private boolean cancelled = false;
    private Component message;

    public ShiftProgressEvent(Player player, int currentProgress, int maxProgress, EquipmentSlot hand, ItemStack item) {
        this.player = player;
        this.currentProgress = currentProgress;
        this.maxProgress = maxProgress;
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

    /**
     * Gets the item that was used for the shift-activation.
     *
     * @return The item.
     */
    public ItemStack getItem() {
        return item;
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
     * Gets the current progress.
     *
     * @return The current progress.
     */
    public int getCurrentProgress() {
        return currentProgress;
    }

    /**
     * Gets the maximum progress required for activation.
     *
     * @return The maximum progress.
     */
    public int getMaxProgress() {
        return maxProgress;
    }

    /**
     * Gets the current progress as a percentage.
     *
     * @return The progress percentage (0-100).
     */
    public int getPercentage() {
        if (maxProgress <= 0) {
            return 0;
        }
        return (int) Math.round((double) currentProgress / maxProgress * 100.0);
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
     * Gets the action bar message that will be displayed to the player.
     *
     * @return The action bar message.
     */
    public Component getActionBarMessage() {
        return message;
    }

    /**
     * Sets the action bar message that will be displayed to the player.
     *
     * @param message The new message.
     */
    public void setMessage(Component message) {
        this.message = message;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public record ActionBarData(Component component, String message) {
    }
}
