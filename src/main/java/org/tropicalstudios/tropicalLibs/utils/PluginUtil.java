package org.tropicalstudios.tropicalLibs.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;

public class PluginUtil {

    public static void setPluginInstance(Plugin plugin) {
        try {
            // Get the ChatUtil class
            Class<?> chatUtilClass = Class.forName("org.tropicalstudios.tropicalLibs.TropicalLibs");

            // Get the customPrefix field
            Field customPrefixField = chatUtilClass.getDeclaredField("INSTANCE");

            // Make it accessible even if it's private
            customPrefixField.setAccessible(true);

            // Set your custom prefix
            customPrefixField.set(null, plugin);
        } catch (Exception e) {
            Bukkit.getLogger().severe("Failed to set plugin instance: " + e.getMessage());
        }
    }

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
