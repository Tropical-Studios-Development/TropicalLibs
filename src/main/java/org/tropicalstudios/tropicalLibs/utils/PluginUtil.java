package org.tropicalstudios.tropicalLibs.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;

public class PluginUtil {

    public static Plugin getCallingPlugin() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        // Start at index 2 to skip getCallingPlugin and getStackTrace methods
        for (int i = 2; i < stackTrace.length; i++) {
            String className = stackTrace[i].getClassName();
            try {
                Class<?> clazz = Class.forName(className);
                ClassLoader classLoader = clazz.getClassLoader();

                // Check all loaded plugins
                for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
                    if (plugin.getClass().getClassLoader() == classLoader) {
                        return plugin;
                    }
                }
            } catch (ClassNotFoundException e) {
                // Skip if class can't be found
                continue;
            }
        }
        return null; // Return null if no plugin found
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
