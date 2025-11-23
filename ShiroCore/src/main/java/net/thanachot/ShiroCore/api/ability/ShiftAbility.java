package net.thanachot.ShiroCore.api.ability;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

/**
 * Represents a shift-activated ability that can be registered with ShiroCore.
 * This provides a simplified API for creating abilities that activate via
 * shift-spam.
 */
public abstract class ShiftAbility {

    private final String id;
    private final Predicate<ItemStack> itemValidator;

    /**
     * Creates a new shift ability.
     *
     * @param id            Unique identifier for this ability
     * @param itemValidator Predicate to validate if an item can use this ability
     */
    protected ShiftAbility(@NotNull String id, @NotNull Predicate<ItemStack> itemValidator) {
        this.id = id;
        this.itemValidator = itemValidator;
    }

    /**
     * Gets the unique ID of this ability.
     *
     * @return The ability ID
     */
    @NotNull
    public String getId() {
        return id;
    }

    /**
     * Validates if the given item can use this ability.
     *
     * @param item The item to validate
     * @return true if the item can use this ability
     */
    public boolean canUse(@NotNull ItemStack item) {
        return itemValidator.test(item);
    }

    /**
     * Called when the ability is activated via shift-spam.
     *
     * @param player The player who activated the ability
     * @param item   The item they were holding
     */
    public abstract void onActivate(@NotNull Player player, @NotNull ItemStack item);

    /**
     * Called when the ability is deactivated (e.g., item swap).
     *
     * @param player The player whose ability was deactivated
     */
    public abstract void onDeactivate(@NotNull Player player);

    /**
     * Checks if the ability is currently active for the given player.
     *
     * @param player The player to check
     * @return true if the ability is active
     */
    public abstract boolean isActive(@NotNull Player player);
}
