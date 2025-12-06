package org.tropicalstudios.tropicalLibs;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.tropicalstudios.tropicalLibs.utils.ChatUtil;
import org.tropicalstudios.tropicalLibs.utils.PluginUtil;
import org.tropicalstudios.tropicalLibs.utils.PlayerUtil;

public class Messenger {

    /**
     * Send a formated message to command sender
     *
     * @param commandSender  The command sender to receive the message
     * @param message The message that should be sent
     */
    public static void sendMessage(CommandSender commandSender, String message) {
        commandSender.sendMessage(ChatUtil.c(message));
    }

    /**
     * Send MiniMessage message from string
     *
     * @param commandSender  The command sender (audience) to receive the message
     * @param message The MiniMessage-formatted string to send
     */
    public static void sendMM(CommandSender commandSender, String message) {
        if (commandSender == null || message == null)
            return;

        Audience audience = TropicalLibs.getAudiences().sender(commandSender);
        audience.sendMessage(ChatUtil.mm(message));
    }

    /**
     * Send MiniMessage message from a component
     *
     * @param commandSender    The command sender (audience) to receive the message
     * @param component The Adventure component to send
     */
    public static void sendMM(CommandSender commandSender, Component component) {
        if (commandSender == null || component == null)
            return;

        Audience audience = TropicalLibs.getAudiences().sender(commandSender);
        audience.sendMessage(component);
    }

    /**
     * Broadcast message to all online players
     *
     * @param message The message to broadcast (color codes supported)
     */
    public static void broadcast(String message) {
        for (Player player : PlayerUtil.getOnlinePlayers())
            player.sendMessage(ChatUtil.c("%prefix% " + message));
    }

    /**
     * Broadcast message without plugin prefix to all online players
     *
     * @param message The message to broadcast (color codes supported)
     */
    public static void broadcastWithoutPrefix(String message) {
        for (Player player : PlayerUtil.getOnlinePlayers())
            player.sendMessage(ChatUtil.c(message));
    }

    /**
     * Broadcast message to players with the specified permission
     *
     * @param permission The permission required to receive the message
     * @param message    The message to broadcast (color codes supported)
     */
    public static void broadcastPermission(String permission, String message) {
        for (Player p : PlayerUtil.getOnlinePlayers())
            if (p.hasPermission(permission))
                p.sendMessage(ChatUtil.c("%prefix%" + message));
    }

    /**
     * Logger function
     *
     * @param level      The log level (affects color)
     * @param message    The message to send to the console
     * @param pluginName Optional flag: if true, use the caller plugin name; otherwise defaults to TropicalLibs
     */
    public static void log(LogLevel level, String message, boolean... pluginName) {
        boolean useCallerName = pluginName.length > 0 && pluginName[0];
        String prefix = useCallerName ? "[" + PluginUtil.getPluginName() + "] " : "[TropicalLibs] ";
        Bukkit.getConsoleSender().sendMessage(prefix + level.format(message));
    }

    public enum LogLevel {
        SUCCESS("&a"),
        INFO("&e"),
        WARN("&c");

        private final String color;

        LogLevel(String color) {
            this.color = color;
        }

        private String format(String message) {
            return ChatUtil.c(color + message);
        }
    }

}
