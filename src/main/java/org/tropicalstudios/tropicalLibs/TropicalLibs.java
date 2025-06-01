package org.tropicalstudios.tropicalLibs;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class TropicalLibs extends JavaPlugin {

    private static final Map<String, Plugin> pluginInstances = new HashMap<>();
    private static final Map<String, String> pluginNames = new HashMap<>();

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

    public static Plugin getINSTANCE() {
        String callerClassName = getCallerClassName();
        return pluginInstances.get(callerClassName);
    }

    public static String getPluginName() {
        String callerClassName = getCallerClassName();
        return pluginNames.get(callerClassName);
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
