package org.tropicalstudios.tropicalLibs;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.tropicalstudios.tropicalLibs.utils.PluginUtil;

public final class TropicalLibs extends JavaPlugin {

    private static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = PluginUtil.getPlugin();
    }

    @Override
    public void onDisable() {
        plugin = null;
    }

    public static Plugin getPlugin() {
        return plugin;
    }
}
