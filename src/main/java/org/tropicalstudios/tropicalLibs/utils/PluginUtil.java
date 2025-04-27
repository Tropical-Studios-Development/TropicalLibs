package org.tropicalstudios.tropicalLibs.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;

public class PluginUtil {

    // Set the plugin instance to the plugin using the lib
    public static void setPluginInstance(Plugin plugin) {
        try {
            // Get the TropicalLibs class
            Class<?> libsClass = Class.forName("org.tropicalstudios.tropicalLibs.TropicalLibs");

            // Get the instance field
            Field instanceField = libsClass.getDeclaredField("INSTANCE");

            // Get the plugin name field
            Field nameField = libsClass.getDeclaredField("pluginName");

            // Make it accessible even if it's private
            instanceField.setAccessible(true);
            nameField.setAccessible(true);

            // Set your instance
            instanceField.set(null, plugin);
            nameField.set(null, plugin.getName());
        } catch (Exception e) {
            Bukkit.getLogger().severe("Failed to set plugin instance: " + e.getMessage());
        }
    }

    // Set the plugin prefix
    public static void setPluginPrefix(String prefix) {
        try {
            // Get the ChatUtil class
            Class<?> chatUtilClass = Class.forName("org.tropicalstudios.tropicalLibs.utils.ChatUtil");

            // Get the customPrefix field
            Field customPrefixField = chatUtilClass.getDeclaredField("customPrefix");

            // Make it accessible even if it's private
            customPrefixField.setAccessible(true);

            // Set your custom prefix
            customPrefixField.set(null, prefix);
        } catch (Exception e) {
            Bukkit.getLogger().severe("Failed to set custom prefix: " + e.getMessage());
        }
    }
}
