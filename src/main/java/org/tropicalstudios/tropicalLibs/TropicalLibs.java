package org.tropicalstudios.tropicalLibs;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class TropicalLibs extends JavaPlugin {

    private static Plugin INSTANCE;
    private static String pluginName;

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

    public static Plugin getINSTANCE() {
        return INSTANCE;
    }

    public static String getPluginName() {
        return pluginName;
    }
}
