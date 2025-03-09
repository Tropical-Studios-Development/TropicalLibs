package org.tropicalstudios.tropicalLibs;

import org.bukkit.plugin.java.JavaPlugin;

public final class TropicalLibs extends JavaPlugin {

    private static TropicalLibs INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
    }

    @Override
    public void onDisable() {
        INSTANCE = null;
    }

    public static TropicalLibs getINSTANCE() {
        return INSTANCE;
    }
}
