package net.thanachot.ShiroCore;

import net.thanachot.ShiroCore.api.ShiftActivation;
import net.thanachot.ShiroCore.api.ability.AbilityManager;
import net.thanachot.ShiroCore.internal.ability.AbilityListener;
import net.thanachot.ShiroCore.internal.ability.AbilityManagerImpl;
import net.thanachot.ShiroCore.internal.listener.ShiftActivationListener;
import net.thanachot.ShiroCore.internal.system.ShiftActivationService;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main plugin class for ShiroCore.
 */
public final class ShiroCore extends JavaPlugin {

    private ShiftActivationService shiftActivationService;
    private AbilityManagerImpl abilityManager;

    @Override
    public void onEnable() {
        // Initialize Ability system first (needed by ShiftActivationListener)
        abilityManager = new AbilityManagerImpl();
        getServer().getServicesManager().register(AbilityManager.class, abilityManager, this,
                ServicePriority.Normal);

        // Initialize ShiftActivation system (now with ability support)
        shiftActivationService = new ShiftActivationService();
        getServer().getServicesManager().register(ShiftActivation.class, shiftActivationService, this,
                ServicePriority.Normal);
        getServer().getPluginManager()
                .registerEvents(new ShiftActivationListener(shiftActivationService, abilityManager), this);

        // Register ability-specific listener
        getServer().getPluginManager().registerEvents(new AbilityListener(abilityManager), this);

        getLogger().info("ShiroCore enabled with ShiftActivation and AbilityManager!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Gets the instance of the ShiftActivationService.
     *
     * @return The ShiftActivationService instance.
     */
    public ShiftActivationService getShiftActivationService() {
        return shiftActivationService;
    }
}
