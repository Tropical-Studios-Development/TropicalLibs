package org.tropicalstudios.tropicalLibs.utils;

import org.bukkit.Bukkit;
import org.tropicalstudios.tropicalLibs.Messenger;
import org.tropicalstudios.tropicalLibs.TropicalLibs;

public class HookUtil {

    /**
     * Verify that a required plugin is enabled
     *
     * Logs a warning and disables TropicalLibs if the dependency is missing;
     * otherwise logs a successful hook message
     *
     * @param pluginName the name of the dependency to check
     */
    public static void checkHook(String pluginName) {
        if (!Bukkit.getPluginManager().isPluginEnabled(pluginName)) {
            Messenger.log(Messenger.LogLevel.WARN, "Disabled due to no {plugin} dependency found!"
                    .replace("{plugin}", pluginName));
            Bukkit.getPluginManager().disablePlugin(TropicalLibs.getINSTANCE());
            return;
        }
        Messenger.log(Messenger.LogLevel.INFO, "Successfully hooked into {plugin}"
                .replace("{plugin}", pluginName));
    }

    /**
     * Ensure PlaceholderAPI is present and enabled
     *
     * Disables TropicalLibs when absent; logs a success message when available
     */
    public static void checkPAPI() {
        if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            Messenger.log(Messenger.LogLevel.WARN, "Disabled due to no PlaceholderAPI dependency found!");
            Bukkit.getPluginManager().disablePlugin(TropicalLibs.getINSTANCE());
            return;
        }
        Messenger.log(Messenger.LogLevel.INFO, "Successfully hooked into PlaceholderAPI");
    }

    /**
     * Ensure NBTAPI is present and enabled
     *
     * Disables TropicalLibs when absent; logs a success message when available
     */
    public static void checkNBT() {
        if (!Bukkit.getPluginManager().isPluginEnabled("NBTAPI")) {
            Messenger.log(Messenger.LogLevel.WARN, "Disabled due to no NBTAPI dependency found!");
            Bukkit.getPluginManager().disablePlugin(TropicalLibs.getINSTANCE());
            return;
        }
        Messenger.log(Messenger.LogLevel.INFO, "Successfully hooked into NBTAPI");
    }

    /**
     * Ensure Vault is present and enabled
     *
     * Disables TropicalLibs when absent; logs a success message when available
     */
    public static void checkVault() {
        if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            Messenger.log(Messenger.LogLevel.WARN, "Disabled due to no Vault dependency found!");
            Bukkit.getPluginManager().disablePlugin(TropicalLibs.getINSTANCE());
            return;
        }
        Messenger.log(Messenger.LogLevel.INFO, "Successfully hooked into Vault");
    }
}
