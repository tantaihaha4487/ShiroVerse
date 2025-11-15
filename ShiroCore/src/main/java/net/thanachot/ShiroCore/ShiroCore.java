package net.thanachot.ShiroCore;

import org.bukkit.plugin.java.JavaPlugin;

public class ShiroCore extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("ShiroCore Enabled");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("ShiroCore Disabled");
    }

}
