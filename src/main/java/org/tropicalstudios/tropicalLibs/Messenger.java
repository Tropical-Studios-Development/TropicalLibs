package org.tropicalstudios.tropicalLibs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.tropicalstudios.tropicalLibs.utils.ChatUtil;
import org.tropicalstudios.tropicalLibs.utils.PlayerUtil;

public class Messenger {

    public static void broadcast(String message) {
        for (Player player : PlayerUtil.getOnlinePlayers())
            player.sendMessage(ChatUtil.c("%prefix% " + message));
    }

    public static void broadcastPermission(Player player, String permission, String message) {
        for (Player p : PlayerUtil.getOnlinePlayers())
            if (p.hasPermission(permission))
                p.sendMessage(ChatUtil.c("%prefix%" + message));
    }

    public static void info(String message) {
        Bukkit.getLogger().info(ChatUtil.c(message));
    }

    public static void warn(String message) {
        Bukkit.getLogger().warning(ChatUtil.c(message));
    }
}
