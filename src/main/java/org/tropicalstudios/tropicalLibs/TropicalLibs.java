package org.tropicalstudios.tropicalLibs;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.tropicalstudios.tropicalLibs.utils.PluginUtil;

public final class TropicalLibs extends JavaPlugin {

    private static TropicalLibs self;
    private static BukkitAudiences audiences;

    @Override
    public void onEnable() {
        self = this;
        audiences = BukkitAudiences.create(this);
        Messenger.log(Messenger.LogLevel.SUCCESS, "Successfully enabled!");
    }

    @Override
    public void onDisable() {
        if (audiences != null)
            audiences.close();

        Messenger.log(Messenger.LogLevel.SUCCESS, "Successfully disabled!");
    }

    /**
     * Resolves the plugin instance that invoked the current call
     *
     * Attempts to detect the caller's plugin using {@link PluginUtil#resolveCallerPlugin()}
     * If the caller cannot be identified (e.g., reflection or non-plugin source),
     * this method falls back to returning the TropicalLibs plugin instance
     *
     * @return The calling plugin instance, or TropicalLibs itself if resolution fails
     */
    public static Plugin getINSTANCE() {
        Plugin plugin = PluginUtil.resolveCallerPlugin();
        return plugin != null ? plugin : TropicalLibs.self;
    }

    public static BukkitAudiences getAudiences() {
        return audiences;
    }
}
