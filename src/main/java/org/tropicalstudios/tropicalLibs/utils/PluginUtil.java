package org.tropicalstudios.tropicalLibs.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;

public class PluginUtil {

    public static Plugin getPlugin() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        Plugin callingPlugin = null;

        for (StackTraceElement element : stackTrace) {
            try {
                Class<?> clazz = Class.forName(element.getClassName());
                ClassLoader classLoader = clazz.getClassLoader();

                for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
                    if (classLoader == plugin.getClass().getClassLoader()) {
                        callingPlugin = plugin;
                        break;
                    }
                }

                if (callingPlugin != null) break;
            } catch (ClassNotFoundException e) {
                // Ignore
            }
        }

        return callingPlugin;
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
