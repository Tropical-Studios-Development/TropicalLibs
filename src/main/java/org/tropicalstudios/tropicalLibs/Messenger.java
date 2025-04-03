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

    // Send an info message in the console
    public static void info(String message) {
        Bukkit.getLogger().info(ChatUtil.c(message));
    }

    // Send a warning message in the console
    public static void warn(String message) {
        Bukkit.getLogger().warning(ChatUtil.c(message));
    }
}
