package org.tropicalstudios.tropicalLibs;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class TropicalLibs extends JavaPlugin {

    private static TropicalLibs self;
    private static final Map<String, Plugin> pluginInstances = new HashMap<>();
    private static final Map<String, String> pluginNames = new HashMap<>();

    @Override
    public void onEnable() {
        self = this;
        Messenger.log(Messenger.LogLevel.INFO, "Successfully enabled!");
    }

    @Override
    public void onDisable() {
        Messenger.log(Messenger.LogLevel.INFO, "Successfully disabled!");
    }

    public static Plugin getINSTANCE() {
        String callerClassName = getCallerClassName();
        Plugin plugin = (callerClassName != null) ? pluginInstances.get(callerClassName) : null;

        // If detection failed, fall back to TropicalLibs plugin itself
        if (plugin == null)
            return TropicalLibs.self;

        return plugin;
    }

    public static String getPluginName() {
        String callerClassName = getCallerClassName();
        String name = (callerClassName != null) ? pluginNames.get(callerClassName) : null;

        // Fallback to TropicalLibs if detection fails
        if (name == null)
            return TropicalLibs.self.getName();

        return name;
    }

    public static void registerPlugin(String pluginClassName, Plugin plugin) {
        pluginInstances.put(pluginClassName, plugin);
        pluginNames.put(pluginClassName, plugin.getName());
    }

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
}
