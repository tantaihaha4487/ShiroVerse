package net.thanachot.ShiroCore.api.ability;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * The main API for managing shift-activated abilities in ShiroCore.
 * This provides a simplified interface for plugins to register and manage
 * abilities.
 */
public interface AbilityManager {

    /**
     * Gets the AbilityManager API from the Bukkit services manager.
     *
     * @return An Optional containing the AbilityManager instance if available
     */
    @NotNull
    static Optional<AbilityManager> get() {
        RegisteredServiceProvider<AbilityManager> provider = Bukkit.getServicesManager()
                .getRegistration(AbilityManager.class);
        return Optional.ofNullable(provider).map(RegisteredServiceProvider::getProvider);
    }

    /**
     * Gets the AbilityManager API from the Bukkit services manager.
     *
     * @return The AbilityManager instance
     * @throws IllegalStateException if the service is not registered
     */
    @NotNull
    static AbilityManager getOrThrow() {
        return get().orElseThrow(
                () -> new IllegalStateException("AbilityManager service not found! Is ShiroCore enabled?"));
    }

    /**
     * Registers a shift-activated ability.
     *
     * @param ability The ability to register
     */
    void registerAbility(@NotNull ShiftAbility ability);

    /**
     * Unregisters a shift-activated ability.
     *
     * @param abilityId The ID of the ability to unregister
     */
    void unregisterAbility(@NotNull String abilityId);

    /**
     * Gets an ability by its ID.
     *
     * @param abilityId The ability ID
     * @return Optional containing the ability if found
     */
    @NotNull
    Optional<ShiftAbility> getAbility(@NotNull String abilityId);

    /**
     * Activates an ability for a player.
     *
     * @param player    The player
     * @param abilityId The ability ID
     * @param item      The item being used
     */
    void activateAbility(@NotNull Player player, @NotNull String abilityId, @NotNull ItemStack item);

    /**
     * Deactivates an ability for a player.
     *
     * @param player    The player
     * @param abilityId The ability ID
     */
    void deactivateAbility(@NotNull Player player, @NotNull String abilityId);

    /**
     * Checks if a player has any ability active.
     *
     * @param player The player
     * @return true if any ability is active
     */
    boolean hasActiveAbility(@NotNull Player player);

    /**
     * Gets the active ability for a player.
     *
     * @param player The player
     * @return Optional containing the active ability if any
     */
    @NotNull
    Optional<ShiftAbility> getActiveAbility(@NotNull Player player);

    /**
     * Deactivates all abilities for a player.
     *
     * @param player The player
     */
    void deactivateAll(@NotNull Player player);
}
