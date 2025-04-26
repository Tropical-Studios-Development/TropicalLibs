package org.tropicalstudios.tropicalLibs;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class TropicalLibs extends JavaPlugin {

    private static Plugin INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
    }

    @Override
    public void onDisable() {}

    public static Plugin getINSTANCE() {
        return INSTANCE;
    }
}
