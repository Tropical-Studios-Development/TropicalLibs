package org.tropicalstudios.tropicalLibs.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.tropicalstudios.tropicalLibs.toasts.AdvancementAccessor;
import org.tropicalstudios.tropicalLibs.toasts.ToastStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtil {

    // Plugin prefix
    private static String customPrefix = "";

    public ChatUtil(String customPrefix) {
        ChatUtil.customPrefix = customPrefix;
    }

    // Color the messages
    public static String c(String message) {
        return ChatColor.translateAlternateColorCodes('&', format(message));
    }

    // Color a list of messages and return them as a colored list
    public static List<String> c(List<String> messages) {
        List<String> coloredMessages = new ArrayList<>();
        for (String s : messages)
            coloredMessages.add(ChatColor.translateAlternateColorCodes('&', format(s)));
        return coloredMessages;
    }

    // Check for HEX pattern
    private static final Pattern HEX_PATTERN = Pattern.compile("&(#[A-Fa-f0-9]{6})");

    // Format the string
    private static String format(String string) {
        // Return empty string if input is null
        if (string == null) {
            return "";
        }

        string = string.replace("%prefix%", customPrefix);

        Matcher matcher = HEX_PATTERN.matcher(string);
        while (matcher.find())
            string = string.replace(matcher.group(),
                    "" + net.md_5.bungee.api.ChatColor.of(matcher.group().replace("&", "")));
        return string;
    }

    // Capitalize the first letters of words (separated by an '-')
    public static String capitalizeWords(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        String[] words = input.split("-");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1).toLowerCase()).append("-");
            }
        }
        return result.deleteCharAt(result.length() - 1).toString(); // Remove trailing "-"
    }

    // Execute command as a player
    public static void executePlayerCommand(Player player, String command) {
        player.performCommand(command);
    }

    // Execute multiple commands as a player
    public static void executePlayerCommand(Player player, List<String> commands) {
        for (String cmd : commands)
            player.performCommand(cmd);
    }

    // Execute console command
    public static void executeConsoleCommand(String command) {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        Bukkit.dispatchCommand(console, command);
    }

    // Execute multiple console commands
    public static void executeConsoleCommand(List<String> commands) {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        for (String cmd : commands)
            Bukkit.dispatchCommand(console, cmd);
    }

    // Send action-bar message to player
    public static void sendActionBarMessage(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy(ChatUtil.c(message)));
    }

    // Send title to player with predefined timings
    public static void sendTitle(Player player, String title, String subtitle) {
        player.sendTitle(ChatUtil.c(title), ChatUtil.c(subtitle), 10, 40, 10);
    }

    // Send title to player with custom timings
    public static void sendTitle(Player player, String title, String subtitle, int i1, int i2, int i3) {
        player.sendTitle(ChatUtil.c(title), ChatUtil.c(subtitle), i1, i2, i3);
    }

    // Send toast to player
    public static void sendToast(Player receiver, String message) {
        sendToast(receiver, message, Material.BOOK, ToastStyle.TASK);
    }

    public static void sendToast(Player receiver, String message, ToastStyle toastStyle) {
        sendToast(receiver, message, Material.BOOK, toastStyle);
    }

    public static void sendToast(Player receiver, String message, Material icon) {
        sendToast(receiver, message, icon, ToastStyle.TASK);
    }

    public static void sendToast(Player player, String message, Material icon, ToastStyle style) {
        (new AdvancementAccessor(message, icon.toString().toLowerCase(), style)).show(player);
    }
}
