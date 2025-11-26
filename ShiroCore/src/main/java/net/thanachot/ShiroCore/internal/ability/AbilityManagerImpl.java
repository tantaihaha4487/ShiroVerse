package net.thanachot.ShiroCore.internal.ability;

import net.thanachot.shiroverse.api.ability.AbilityManager;
import net.thanachot.shiroverse.api.ability.ShiftAbility;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of the AbilityManager that handles ability registration and
 * activation.
 */
public class AbilityManagerImpl implements AbilityManager {

    private final Map<String, ShiftAbility> abilities = new ConcurrentHashMap<>();
    private final Map<UUID, String> activeAbilities = new ConcurrentHashMap<>();

    @Override
    public void registerAbility(@NotNull ShiftAbility ability) {
        abilities.put(ability.getId(), ability);
    }

    @Override
    public void unregisterAbility(@NotNull String abilityId) {
        abilities.remove(abilityId);
        // Deactivate for all players using this ability
        activeAbilities.entrySet().removeIf(entry -> entry.getValue().equals(abilityId));
    }

    @Override
    @NotNull
    public Optional<ShiftAbility> getAbility(@NotNull String abilityId) {
        return Optional.ofNullable(abilities.get(abilityId));
    }

    @Override
    public void activateAbility(@NotNull Player player, @NotNull String abilityId, @NotNull ItemStack item) {
        ShiftAbility ability = abilities.get(abilityId);
        if (ability == null) {
            throw new IllegalArgumentException("Unknown ability: " + abilityId);
        }

        // Deactivate any existing ability first
        deactivateAll(player);

        // Activate the new ability
        activeAbilities.put(player.getUniqueId(), abilityId);
        ability.onActivate(player, item);
    }

    @Override
    public void deactivateAbility(@NotNull Player player, @NotNull String abilityId) {
        String currentAbilityId = activeAbilities.get(player.getUniqueId());
        if (abilityId.equals(currentAbilityId)) {
            activeAbilities.remove(player.getUniqueId());
            ShiftAbility ability = abilities.get(abilityId);
            if (ability != null) {
                ability.onDeactivate(player);
            }
        }
    }

    @Override
    public boolean hasActiveAbility(@NotNull Player player) {
        return activeAbilities.containsKey(player.getUniqueId());
    }

    @Override
    @NotNull
    public Optional<ShiftAbility> getActiveAbility(@NotNull Player player) {
        String abilityId = activeAbilities.get(player.getUniqueId());
        if (abilityId == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(abilities.get(abilityId));
    }

    @Override
    public void deactivateAll(@NotNull Player player) {
        String abilityId = activeAbilities.remove(player.getUniqueId());
        if (abilityId != null) {
            ShiftAbility ability = abilities.get(abilityId);
            if (ability != null) {
                ability.onDeactivate(player);
            }
        }
    }

    /**
     * Finds an ability that can be used with the given item.
     *
     * @param item The item to check
     * @return Optional containing the ability if found
     */
    @NotNull
    public Optional<ShiftAbility> findAbilityForItem(@NotNull ItemStack item) {
        return abilities.values().stream()
                .filter(ability -> ability.canUse(item))
                .findFirst();
    }
}
