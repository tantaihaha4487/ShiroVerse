package net.thanachot.shirocore;

import net.thanachot.shiroverse.api.ShiftActivation;
import net.thanachot.shiroverse.api.ability.AbilityManager;
import net.thanachot.shirocore.internal.ability.AbilityListener;
import net.thanachot.shirocore.internal.ability.StandardAbilityManager;
import net.thanachot.shirocore.internal.listener.ShiftActivationListener;
import net.thanachot.shirocore.internal.system.ShiftActivationManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main plugin class for ShiroCore.
 */
public final class ShiroCorePlugin extends JavaPlugin {

    private ShiftActivationManager shiftActivationManager;

    @Override
    public void onEnable() {
        // Initialize Ability system first (needed by ShiftActivationListener)
        StandardAbilityManager abilityManager = new StandardAbilityManager();
        getServer().getServicesManager().register(AbilityManager.class, abilityManager, this,
                ServicePriority.Normal);

        // Initialize ShiftActivation system (now with ability support)
        shiftActivationManager = new ShiftActivationManager();
        getServer().getServicesManager().register(ShiftActivation.class, shiftActivationManager, this,
                ServicePriority.Normal);
        getServer().getPluginManager()
                .registerEvents(new ShiftActivationListener(shiftActivationManager, abilityManager), this);

        // Register ability-specific listener
        getServer().getPluginManager().registerEvents(new AbilityListener(abilityManager), this);

        getLogger().info("ShiroCore enabled with ShiftActivation and AbilityManager!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Gets the instance of the ShiftActivationManager.
     *
     * @return The ShiftActivationManager instance.
     */
    public ShiftActivationManager getShiftActivationManager() {
        return shiftActivationManager;
    }
}
