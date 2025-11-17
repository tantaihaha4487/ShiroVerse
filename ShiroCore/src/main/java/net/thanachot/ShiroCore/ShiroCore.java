package net.thanachot.ShiroCore;

import net.thanachot.ShiroCore.api.ShiftActivation;
import net.thanachot.ShiroCore.listener.ShiftActivationListener;
import net.thanachot.ShiroCore.system.ShiftActivationService;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main plugin class for ShiroCore.
 */
public final class ShiroCore extends JavaPlugin {

    private static ShiroCore instance;
    private ShiftActivationService shiftActivationService;

    /**
     * Gets the singleton instance of the ShiroCore plugin.
     *
     * @return The ShiroCore instance.
     */
    public static ShiroCore getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        shiftActivationService = new ShiftActivationService();
        ShiftActivation.setService(shiftActivationService);
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
