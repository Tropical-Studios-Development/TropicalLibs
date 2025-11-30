package org.tropicalstudios.tropicalLibs;

import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.tropicalstudios.tropicalLibs.utils.ChatUtil;
import org.tropicalstudios.tropicalLibs.utils.PlayerUtil;

public class Messenger {

    // Send a message with MiniMessage format
    public static void sendMessage(CommandSender sender, String message) {
        if (sender == null || message == null)
            return;

        Audience audience = TropicalLibs.getAudiences().sender(sender);
        audience.sendMessage(ChatUtil.mm(message));
    }

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

    // Broadcast message to players with the specified permission
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

    /**
     * Logger function for TropicalLibs
     *
     * @param level            The log level (color based)
     * @param message          The message that should be sent in console
     */
    public static void log(LogLevel level, String message) {
        Bukkit.getConsoleSender().sendMessage(
                "[TropicalLibs] " + level.format(message)
        );
    }

    public enum LogLevel {
        SUCCESS("&a"),
        INFO("&e"),
        WARN("&c");

        private final String color;

        LogLevel(String color) {
            this.color = color;
        }

        public String format(String message) {
            return ChatUtil.c(color + message);
        }
    }

}
