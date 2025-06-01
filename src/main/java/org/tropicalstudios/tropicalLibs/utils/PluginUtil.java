package org.tropicalstudios.tropicalLibs.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.tropicalstudios.tropicalLibs.TropicalLibs;

public class PluginUtil {

    // Set the plugin instance to the plugin using the lib
    public static void setPluginInstance(Plugin plugin) {
        try {
            // Register the plugin with TropicalLibs
            TropicalLibs.registerPlugin(plugin.getClass().getName(), plugin);

            Bukkit.getLogger().info("Successfully registered plugin: " + plugin.getName());
        } catch (Exception e) {
            Bukkit.getLogger().severe("Failed to set plugin instance: " + e.getMessage());
        }
    }
}
