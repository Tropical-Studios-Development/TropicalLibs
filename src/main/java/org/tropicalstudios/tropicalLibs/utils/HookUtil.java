package org.tropicalstudios.tropicalLibs.utils;

import org.bukkit.Bukkit;
import org.tropicalstudios.tropicalLibs.Messenger;
import org.tropicalstudios.tropicalLibs.TropicalLibs;

public class HookUtil {

    // Check if a plugin is enabled
    public static void checkHook(String pluginName) {
        if (!Bukkit.getPluginManager().isPluginEnabled(pluginName)) {
            Messenger.warn("Disabled due to no {plugin} dependency found!"
                    .replace("{plugin}", pluginName));
            Bukkit.getPluginManager().disablePlugin(TropicalLibs.getINSTANCE());
            return;
        }
        Messenger.info("Successfully hooked into {plugin}"
                .replace("{plugin}", pluginName));
    }

    // Check if PlaceholderAPI is enabled
    public static void checkPAPI() {
        if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            Messenger.warn("Disabled due to no PlaceholderAPI dependency found!");
            Bukkit.getPluginManager().disablePlugin(TropicalLibs.getINSTANCE());
            return;
        }
        Messenger.info("Successfully hooked into PlaceholderAPI");
    }

    // Check if NBTAPI is enabled
    public static void checkNBT() {
        if (!Bukkit.getPluginManager().isPluginEnabled("NBTAPI")) {
            Messenger.warn("Disabled due to no NBTAPI dependency found!");
            Bukkit.getPluginManager().disablePlugin(TropicalLibs.getINSTANCE());
            return;
        }
        Messenger.info("Successfully hooked into NBTAPI");
    }

    // Check if Vault is enabled
    public static void checkVault() {
        if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            Messenger.warn("Disabled due to no Vault dependency found!");
            Bukkit.getPluginManager().disablePlugin(TropicalLibs.getINSTANCE());
            return;
        }
        Messenger.info("Successfully hooked into Vault");
    }
}
