package net.thanachot.ShiroCore.internal.ability;

import net.thanachot.ShiroCore.api.ability.ShiftAbility;
import net.thanachot.ShiroCore.api.event.ShiftActivationEvent;
import net.thanachot.ShiroCore.api.event.ShiftProgressEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * Internal listener that automatically manages ability activation/deactivation.
 */
public class AbilityListener implements Listener {

    private final AbilityManagerImpl abilityManager;

    public AbilityListener(AbilityManagerImpl abilityManager) {
        this.abilityManager = abilityManager;
    }

    @EventHandler
    public void onShiftProgress(ShiftProgressEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        // Check if any ability can use this item
        Optional<ShiftAbility> ability = abilityManager.findAbilityForItem(item);

        if (ability.isEmpty()) {
            // No ability for this item, cancel progress
            event.setCancelled(true);
            return;
        }

        // If an ability is already active, cancel progress
        if (abilityManager.hasActiveAbility(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onShiftActivation(ShiftActivationEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        // Find the ability for this item
        Optional<ShiftAbility> abilityOpt = abilityManager.findAbilityForItem(item);

        if (abilityOpt.isEmpty()) {
            return;
        }

        ShiftAbility ability = abilityOpt.get();

        // Don't activate if already active
        if (ability.isActive(player)) {
            return;
        }

        // Activate the ability
        abilityManager.activateAbility(player, ability.getId(), item);
    }

    @EventHandler
    public void onItemSwap(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

        // Deactivate all abilities when item is swapped
        if (abilityManager.hasActiveAbility(player)) {
            abilityManager.deactivateAll(player);
        }
    }
}
