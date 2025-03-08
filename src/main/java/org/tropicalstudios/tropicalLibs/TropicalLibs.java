package org.tropicalstudios.tropicalLibs;

import org.bukkit.plugin.java.JavaPlugin;

public final class TropicalLibs extends JavaPlugin {

    private static TropicalLibs INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        getLogger().info("TropicalLibs has been enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("TropicalLibs has been disabled");
    }

    public static TropicalLibs getINSTANCE() {
        return INSTANCE;
    }
}
