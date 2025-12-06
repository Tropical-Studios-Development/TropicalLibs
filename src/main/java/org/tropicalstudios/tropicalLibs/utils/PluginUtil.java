package org.tropicalstudios.tropicalLibs.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.tropicalstudios.tropicalLibs.Messenger;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import java.util.HashMap;

public class PluginUtil {
    
    private static final Map<String, Plugin> pluginInstances = new HashMap<>();
    private static final Map<String, String> pluginNames = new HashMap<>();
    private static final Map<String, String> pluginVersions = new HashMap<>();

    /**
     * Set the plugin instance to the plugin using the lib
     *
     * @param plugin The plugin instance to register
     */
    public static void setPluginInstance(Plugin plugin) {
        try {
            // Register the plugin with the library registry
            PluginUtil.registerPlugin(plugin.getClass().getName(), plugin);

            Bukkit.getLogger().info("Successfully registered plugin: " + plugin.getName());
        } catch (Exception e) {
            Bukkit.getLogger().severe("Failed to set plugin instance: " + e.getMessage());
        }
    }

    /**
     * Register a plugin by its main class name
     *
     * @param pluginClassName fully-qualified main class name
     * @param plugin          the plugin instance
     */
    private static void registerPlugin(String pluginClassName, Plugin plugin) {
        pluginInstances.put(pluginClassName, plugin);
        pluginNames.put(pluginClassName, plugin.getName());
        try {
            pluginVersions.put(pluginClassName, plugin.getDescription().getVersion());
        } catch (Throwable ignored) {}
    }

    /**
     * Get the caller plugin name based on the stack trace. Falls back to TropicalLibs name
     */
    public static String getPluginName() {
        String callerClassName = getCallerClassName();
        String name = (callerClassName != null) ? pluginNames.get(callerClassName) : null;

        if (name == null) {
            Plugin lib = Bukkit.getPluginManager().getPlugin("TropicalLibs");
            return lib != null ? lib.getName() : "TropicalLibs";
        }
        return name;
    }

    /**
     * Get the caller plugin version based on the stack trace. Falls back to TropicalLibs version
     */
    public static String getVersion() {
        String callerClassName = getCallerClassName();
        String version = (callerClassName != null) ? pluginVersions.get(callerClassName) : null;

        if (version == null) {
            Plugin lib = Bukkit.getPluginManager().getPlugin("TropicalLibs");
            return lib != null ? lib.getDescription().getVersion() : "";
        }
        return version;
    }

    /**
     * Resolve the registered plugin instance for the current caller, or null if not found
     */
    public static Plugin resolveCallerPlugin() {
        String callerClassName = getCallerClassName();
        return (callerClassName != null) ? pluginInstances.get(callerClassName) : null;
    }

    /**
     * Determine the calling plugin's main class name by inspecting the current thread stack
     *
     * Scans the stack trace for the first class outside TropicalLibs, then resolves it to a
     * registered plugin main class
     */
    private static String getCallerClassName() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (int i = 4; i < stack.length; i++) {
            String className = stack[i].getClassName();

            if (!className.startsWith("org.tropicalstudios.tropicalLibs")) {
                String pluginMainClass = getPluginMainClass(className);
                if (pluginMainClass != null && pluginInstances.containsKey(pluginMainClass))
                    return pluginMainClass;
            }
        }
        return null;
    }

    /**
     * Resolve the registered plugin main class for a given class name
     *
     * If the provided class name is itself a registered plugin main class (or extends JavaPlugin),
     * it is returned. Otherwise, this attempts to match by package against known plugin main classes
     *
     * @param className a fully-qualified class name, typically from the call stack
     */
    private static String getPluginMainClass(String className) {
        try {
            if (pluginInstances.containsKey(className))
                return className;

            Class<?> clazz = Class.forName(className);
            if (JavaPlugin.class.isAssignableFrom(clazz))
                return className;

            String packageName = clazz.getPackage().getName();
            for (String registeredClassName : pluginInstances.keySet()) {
                try {
                    Class<?> registeredClass = Class.forName(registeredClassName);
                    String registeredPackage = registeredClass.getPackage().getName();
                    if (packageName.startsWith(registeredPackage))
                        return registeredClassName;
                } catch (Exception ignored) {}
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Set the plugin prefix to the plugin using the lib
     *
     * @param prefix The prefix to associate with the calling plugin
     */
    public static void setPluginPrefix(String prefix) {
        String callerPluginInfo = getCallerPluginInfo();

        if (callerPluginInfo != null) {
            try {
                Map<String, String> customPrefixes = getCustomPrefixesMap();
                customPrefixes.put(callerPluginInfo, prefix);
            } catch (Exception e) {
                Messenger.log(Messenger.LogLevel.WARN, "Failed to set plugin prefix via reflection: " + e.getMessage());
            }
        } else {
            Messenger.log(Messenger.LogLevel.WARN, "Could not determine caller plugin for prefix: " + prefix);
        }
    }

    /**
     * Get the prefix configured for the calling plugin (or package)
     *
     * @return the resolved prefix, or empty string if none is configured
     */
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
            Messenger.log(Messenger.LogLevel.WARN, "Failed to get plugin prefix via reflection: " + e.getMessage());
        }

        return "";
    }

    /**
     * Corrupt the classes of the given plugin JAR by randomly mutating bytes
     *
     * @param clazz A class from the target JAR (used to locate the file)
     * @throws IOException if an I/O error occurs while corrupting
     */
    public static void corruptPlugin(Class clazz) throws IOException {
        File file = getCurrentJarFile(clazz);

        File temp = new File(file.getParentFile(), "_" + file.getName());
        Files.copy(Paths.get(file.toURI()), Paths.get(temp.toURI()), StandardCopyOption.REPLACE_EXISTING);

        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(temp));
             ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file))) {

            byte[] buffer = new byte[8192];
            Random random = new Random();
            ZipEntry entry;

            while ((entry = zin.getNextEntry()) != null) {
                out.putNextEntry(new ZipEntry(entry.getName()));

                if (entry.getName().endsWith(".class")) {
                    // Read the entire class file
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int len;
                    while ((len = zin.read(buffer)) > 0) {
                        baos.write(buffer, 0, len);
                    }
                    byte[] classData = baos.toByteArray();

                    // Corrupt the class file
                    for (int i = 0; i < classData.length; i += 50) {
                        classData[i] = (byte) random.nextInt(256);
                    }

                    // Write corrupted class file
                    out.write(classData);
                } else {
                    // Copy non-class files as-is
                    int len;
                    while ((len = zin.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }
                }

                out.closeEntry();
            }
        }

        Files.delete(temp.toPath());
    }


    @SuppressWarnings("unchecked")
    /**
     * Resolve the internal custom prefixes map from ChatUtil via reflection
     *
     * @return the mutable map of custom prefixes
     * @throws Exception if reflection access fails
     */
    private static Map<String, String> getCustomPrefixesMap() throws Exception {
        Class<?> chatUtilClass = Class.forName("org.tropicalstudios.tropicalLibs.utils.ChatUtil");
        Field customPrefixesField = chatUtilClass.getDeclaredField("customPrefixes");
        customPrefixesField.setAccessible(true);
        return (Map<String, String>) customPrefixesField.get(null);
    }

    /**
     * Attempt to determine the calling plugin's identity from the stack trace
     */
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

    /**
     * Extract a base package name from a fully qualified class name
     *
     * @param className The fully qualified class name
     */
    private static String extractBasePackage(String className) {
        String[] parts = className.split("\\.");
        if (parts.length >= 3)
            return parts[0] + "." + parts[1] + "." + parts[2];

        return className;
    }

    /**
     * Resolve the JAR file that contains the given class
     *
     * @param clazz The class to inspect
     * @return a File pointing to the JAR containing the class
     */
    private static File getCurrentJarFile(Class clazz) {
        try {
            return new File(clazz.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
