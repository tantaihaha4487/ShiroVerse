package net.thanachot.ShiroCore;

import net.thanachot.ShiroCore.api.ShiftActivation;
import net.thanachot.ShiroCore.listener.ShiftActivationListener;
import net.thanachot.ShiroCore.system.ShiftActivationService;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main plugin class for ShiroCore.
 */
public final class ShiroCore extends JavaPlugin {

    private ShiftActivationService shiftActivationService;

    @Override
    public void onEnable() {
        shiftActivationService = new ShiftActivationService();
        getServer().getServicesManager().register(ShiftActivation.class, shiftActivationService, this, ServicePriority.Normal);
        getServer().getPluginManager().registerEvents(new ShiftActivationListener(shiftActivationService), this);
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
