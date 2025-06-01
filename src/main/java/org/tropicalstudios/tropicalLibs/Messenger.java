package org.tropicalstudios.tropicalLibs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.tropicalstudios.tropicalLibs.utils.ChatUtil;
import org.tropicalstudios.tropicalLibs.utils.PlayerUtil;

public class Messenger {

    // Broadcast message to all online players
    public static void broadcast(String message) {
        for (Player player : PlayerUtil.getOnlinePlayers())
            player.sendMessage(ChatUtil.c("%prefix% " + message));
    }

    // Broadcast message without plugin prefix to all online players
    public static void broadcastWithoutPrefix(String message) {
        for (Player player : PlayerUtil.getOnlinePlayers())
            player.sendMessage(ChatUtil.c(message));
    }

    // Broadcast message to players with the permission
    public static void broadcastPermission(String permission, String message) {
        for (Player p : PlayerUtil.getOnlinePlayers())
            if (p.hasPermission(permission))
                p.sendMessage(ChatUtil.c("%prefix%" + message));
    }

    // Send a success message in the console
    public static void success(String message) {
        Bukkit.getConsoleSender().sendMessage("[" + TropicalLibs.getPluginName() + "] " + ChatUtil.c("&a" + message));
    }

    // Send an info message in the console
    public static void info(String message) {
        Bukkit.getLogger().info("[" + TropicalLibs.getPluginName() + "] " + message);
    }

    // Send a warning message in the console
    public static void warn(String message) {
        Bukkit.getConsoleSender().sendMessage("[" + TropicalLibs.getPluginName() + "] " + ChatUtil.c("&e" + message));
    }

    // Send a severe warning message in the console
    public static void severe(String message) {
        Bukkit.getConsoleSender().sendMessage("[" + TropicalLibs.getPluginName() + "] " + ChatUtil.c("&c" + message));
    }
}
