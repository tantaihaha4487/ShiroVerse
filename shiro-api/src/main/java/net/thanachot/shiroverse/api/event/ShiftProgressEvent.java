package net.thanachot.shiroverse.api.event;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 * Called when a player makes progress in a shift-activation sequence.
 */
public class ShiftProgressEvent extends ShiftEvent {
    private final int currentProgress;
    private final int maxProgress;
    private final float rawPercentage;
    private Component message;

    public ShiftProgressEvent(Player player, int currentProgress, int maxProgress, EquipmentSlot hand, ItemStack item) {
        super(player, hand, item);
        this.currentProgress = currentProgress;
        this.maxProgress = maxProgress;
        this.rawPercentage = (maxProgress <= 0) ? 0 : (float) currentProgress / maxProgress * 100.0f;
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
        return (int) Math.round(rawPercentage);
    }

    /**
     * Gets the raw progress as a percentage.
     *
     * @return The raw progress percentage.
     */
    public float getRawPercentage() {
        return rawPercentage;
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
}
