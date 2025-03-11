package org.tropicalstudios.tropicalLibs;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class TropicalLibs extends JavaPlugin {

    private static Plugin INSTANCE;

    public TropicalLibs(Plugin plugin) {
        TropicalLibs.INSTANCE = plugin;
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

    public static Plugin getINSTANCE() {
        return INSTANCE;
    }
}
