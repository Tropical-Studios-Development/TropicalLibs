package org.tropicalstudios.tropicalLibs.utils;

import org.bukkit.Bukkit;

import java.lang.reflect.Field;

public class PluginUtil {

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

            Bukkit.getLogger().info("Custom prefix set successfully!");
        } catch (Exception e) {
            Bukkit.getLogger().severe("Failed to set custom prefix: " + e.getMessage());
        }
    }
}
