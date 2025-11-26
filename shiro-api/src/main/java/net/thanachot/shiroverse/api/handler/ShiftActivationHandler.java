package net.thanachot.shiroverse.api.handler;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * A handler for the shift activation event.
 */
@FunctionalInterface
public interface ShiftActivationHandler {

    /**
     * Called when a player shift-activates an item.
     *
     * @param player The player who activated the item.
     * @param item   The item that was activated.
     * @param event  The event that triggered the activation.
     */
    void onActivation(@NotNull Player player, @NotNull ItemStack item, @NotNull PlayerToggleSneakEvent event);
}