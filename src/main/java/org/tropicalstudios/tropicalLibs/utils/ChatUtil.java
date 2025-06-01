package org.tropicalstudios.tropicalLibs.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.tropicalstudios.tropicalLibs.Messenger;
import org.tropicalstudios.tropicalLibs.toasts.AdvancementAccessor;
import org.tropicalstudios.tropicalLibs.toasts.ToastStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtil {

    private static Map<String, String> customPrefixes = new HashMap<>();


    public static void setPluginPrefix(String prefix) {
        String callerPluginInfo = getCallerPluginInfo();
        System.out.println("DEBUG: Caller plugin info: " + callerPluginInfo);
        System.out.println("DEBUG: Prefix value: " + prefix);

        if (callerPluginInfo != null) {
            customPrefixes.put(callerPluginInfo, prefix);
            System.out.println("DEBUG: Stored prefix for " + callerPluginInfo);
        } else {
            Messenger.warn("Could not determine caller plugin for prefix: " + prefix);
        }
    }

    public static String getPrefix() {
        String callerPluginInfo = getCallerPluginInfo();
        System.out.println("DEBUG getPrefix: Caller plugin info: " + callerPluginInfo);

        String prefix = customPrefixes.get(callerPluginInfo);
        if (prefix != null) {
            return prefix;
        }

        // Fallback: try to find by package name matching
        if (callerPluginInfo != null) {
            String callerPackage = callerPluginInfo.split("\\|")[0]; // Get the package part
            for (Map.Entry<String, String> entry : customPrefixes.entrySet()) {
                String storedPackage = entry.getKey().split("\\|")[0]; // Get the package part

                // Check if they're from the same plugin (base package match)
                if (callerPackage.equals(storedPackage) ||
                        callerPackage.startsWith(storedPackage) ||
                        storedPackage.startsWith(callerPackage)) {
                    System.out.println("DEBUG: Found prefix via package matching: " + entry.getKey() + " -> " + callerPluginInfo);
                    return entry.getValue();
                }
            }
        }

        System.out.println("DEBUG: No prefix found, returning empty string");
        return ""; // Return empty string instead of null to avoid null issues
    }

    private static String getCallerPluginInfo() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();

        // Debug: Print stack trace for troubleshooting
        System.out.println("DEBUG: Stack trace analysis:");
        for (int i = 0; i < Math.min(stack.length, 8); i++) {
            System.out.println("  [" + i + "] " + stack[i].getClassName() + "." + stack[i].getMethodName());
        }

        // First, try to find the actual JavaPlugin class in the stack
        String pluginClassName = null;
        String firstNonLibraryClass = null;

        for (int i = 3; i < stack.length; i++) {
            String className = stack[i].getClassName();

            // Skip our library classes
            if (className.startsWith("org.tropicalstudios.tropicalLibs")) {
                continue;
            }

            // Remember the first non-library class we encounter
            if (firstNonLibraryClass == null) {
                firstNonLibraryClass = className;
            }

            try {
                Class<?> clazz = Class.forName(className);
                if (org.bukkit.plugin.java.JavaPlugin.class.isAssignableFrom(clazz)) {
                    pluginClassName = className;
                    break;
                }
            } catch (Exception ignored) {}
        }

        if (pluginClassName != null) {
            // We found the actual plugin class, use it with a marker
            return pluginClassName + "|MAIN";
        } else if (firstNonLibraryClass != null) {
            // We didn't find the plugin class, but we have a class from the plugin
            // Extract the base package name (assuming plugin structure like org.author.pluginname.*)
            String basePackage = extractBasePackage(firstNonLibraryClass);
            return basePackage + "|PACKAGE";
        }

        return null;
    }

    private static String extractBasePackage(String className) {
        String[] parts = className.split("\\.");
        if (parts.length >= 3) {
            // Return the first 3 parts: org.tropicalstudios.tropicalStaff
            return parts[0] + "." + parts[1] + "." + parts[2];
        }
        return className;
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

        string = string.replace("%prefix%", getPrefix());

        Matcher matcher = HEX_PATTERN.matcher(string);
        while (matcher.find())
            string = string.replace(matcher.group(),
                    "" + net.md_5.bungee.api.ChatColor.of(matcher.group().replace("&", "")));
        return string;
    }

    // Capitalize the first letters of words and replace separators with spaces
    public static String beautifyName(String input, String separator) {
        if (input != null && !input.isEmpty()) {
            if (!input.contains(separator)) {
                return Character.toUpperCase(input.charAt(0)) + input.substring(1).toLowerCase();
            } else {
                String[] words = input.split(separator);
                StringBuilder result = new StringBuilder();

                for(int i = 0; i < words.length; ++i) {
                    String word = words[i];
                    if (!word.isEmpty()) {
                        result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1).toLowerCase());
                        if (i < words.length - 1) {
                            result.append(" ");
                        }
                    }
                }

                return result.toString();
            }
        } else {
            return input;
        }
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

    // Send title to player with custom timings
    public static void sendTitle(Player player, String title, String subtitle, int i1, int i2, int i3) {
        player.sendTitle(ChatUtil.c(title), ChatUtil.c(subtitle), i1, i2, i3);
    }

    // Send title to player with predefined timings
    public static void sendTitle(Player player, String title, String subtitle) {
        sendTitle(player, title, subtitle, 10, 40, 10);
    }

    // Send title to all online players
    public static void sendTitle(String title, String subtitle) {
        for (Player player : PlayerUtil.getOnlinePlayers())
            sendTitle(player, title, subtitle, 10, 40, 10);
    }

    /**
     * Send a clickable message to a player with customizable action type
     *
     * @param player      The player to send the message to
     * @param actionType  The type of action (OPEN_URL, RUN_COMMAND, SUGGEST_COMMAND, COPY_TO_CLIPBOARD)
     * @param text        The text content of the message
     * @param actionValue The URL or command for the action
     * @param bold        Whether the text should be bold
     * @param italic      Whether the text should be italic
     * @param underlined  Whether the text should be underlined
     */
    public static void sendClickableMessage(Player player, ClickEvent.Action actionType, String text,
                                            String actionValue, boolean bold, boolean italic, boolean underlined) {
        TextComponent message = new TextComponent(text);
        message.setClickEvent(new ClickEvent(actionType, actionValue));
        message.setBold(bold);
        message.setItalic(italic);
        message.setUnderlined(underlined);

        player.spigot().sendMessage(message);
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
