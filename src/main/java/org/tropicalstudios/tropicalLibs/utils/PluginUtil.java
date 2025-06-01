package org.tropicalstudios.tropicalLibs.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.tropicalstudios.tropicalLibs.Messenger;
import org.tropicalstudios.tropicalLibs.TropicalLibs;

import java.lang.reflect.Field;
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

    // Set the plugin prefix to the plugin using the lib
    public static void setPluginPrefix(String prefix) {
        String callerPluginInfo = getCallerPluginInfo();

        if (callerPluginInfo != null) {
            try {
                Map<String, String> customPrefixes = getCustomPrefixesMap();
                customPrefixes.put(callerPluginInfo, prefix);
            } catch (Exception e) {
                Messenger.warn("Failed to set plugin prefix via reflection: " + e.getMessage());
            }
        } else {
            Messenger.warn("Could not determine caller plugin for prefix: " + prefix);
        }
    }

    public static String getPluginPrefix() {
        String callerPluginInfo = getCallerPluginInfo();

        try {
            Map<String, String> customPrefixes = getCustomPrefixesMap();

            String prefix = customPrefixes.get(callerPluginInfo);
            if (prefix != null)
                return prefix;

            if (callerPluginInfo != null) {
                String callerPackage = callerPluginInfo.split("\\|")[0];
                for (Map.Entry<String, String> entry : customPrefixes.entrySet()) {
                    String storedPackage = entry.getKey().split("\\|")[0];

                    if (callerPackage.equals(storedPackage) ||
                            callerPackage.startsWith(storedPackage) ||
                            storedPackage.startsWith(callerPackage)) {
                        return entry.getValue();
                    }
                }
            }
        } catch (Exception e) {
            Messenger.warn("Failed to get plugin prefix via reflection: " + e.getMessage());
        }

        return "";
    }

    @SuppressWarnings("unchecked")
    private static Map<String, String> getCustomPrefixesMap() throws Exception {
        Class<?> chatUtilClass = Class.forName("org.tropicalstudios.tropicalLibs.utils.ChatUtil");
        Field customPrefixesField = chatUtilClass.getDeclaredField("customPrefixes");
        customPrefixesField.setAccessible(true);
        return (Map<String, String>) customPrefixesField.get(null);
    }

    private static String getCallerPluginInfo() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();

        String pluginClassName = null;
        String firstNonLibraryClass = null;

        for (int i = 3; i < stack.length; i++) {
            String className = stack[i].getClassName();

            if (className.startsWith("org.tropicalstudios.tropicalLibs"))
                continue;

            if (firstNonLibraryClass == null)
                firstNonLibraryClass = className;

            try {
                Class<?> clazz = Class.forName(className);
                if (JavaPlugin.class.isAssignableFrom(clazz)) {
                    pluginClassName = className;
                    break;
                }
            } catch (Exception ignored) {}
        }

        if (pluginClassName != null) {
            return pluginClassName + "|MAIN";
        } else if (firstNonLibraryClass != null) {
            String basePackage = extractBasePackage(firstNonLibraryClass);
            return basePackage + "|PACKAGE";
        }

        return null;
    }

    private static String extractBasePackage(String className) {
        String[] parts = className.split("\\.");
        if (parts.length >= 3)
            return parts[0] + "." + parts[1] + "." + parts[2];

        return className;
    }
}
