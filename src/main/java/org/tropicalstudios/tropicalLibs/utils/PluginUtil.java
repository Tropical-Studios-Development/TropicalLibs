package org.tropicalstudios.tropicalLibs.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.tropicalstudios.tropicalLibs.TropicalLibs;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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

    // Set the plugin prefix for the calling plugin
    public static void setPluginPrefix(String prefix) {
        try {
            String callerClassName = getCallerPluginClassName();

            Class<?> chatUtilClass = Class.forName("org.tropicalstudios.tropicalLibs.utils.ChatUtil");

            Field customPrefixField = chatUtilClass.getDeclaredField("customPrefixes");
            customPrefixField.setAccessible(true);

            @SuppressWarnings("unchecked")
            Map<String, String> prefixes = (Map<String, String>) customPrefixField.get(null);
            if (prefixes == null) {
                prefixes = new HashMap<>();
                customPrefixField.set(null, prefixes);
            }

            prefixes.put(callerClassName, prefix);

        } catch (Exception e) {
            Bukkit.getLogger().severe("Failed to set custom prefix: " + e.getMessage());
        }
    }

    private static String getCallerPluginClassName() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (int i = 3; i < stack.length; i++) {
            String className = stack[i].getClassName();
            if (!className.startsWith("org.tropicalstudios.tropicalLibs")) {
                try {
                    Class<?> clazz = Class.forName(className);
                    if (org.bukkit.plugin.java.JavaPlugin.class.isAssignableFrom(clazz)) {
                        return className;
                    }
                } catch (Exception ignored) {}
            }
        }
        return null;
    }
}
