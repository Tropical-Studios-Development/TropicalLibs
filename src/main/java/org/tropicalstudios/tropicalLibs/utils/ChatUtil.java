package org.tropicalstudios.tropicalLibs.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.tropicalstudios.tropicalLibs.Messenger;
import org.tropicalstudios.tropicalLibs.convertors.TextConvertor;
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
    private static final MiniMessage MINI = MiniMessage.miniMessage();
    private static final Pattern HEX_PATTERN = Pattern.compile("&(#[A-Fa-f0-9]{6})");

    /**
     * Colorize a message, supporting both Legacy color codes and MiniMessage tags.
     *
     * Replaces the "%prefix%" placeholder with the calling plugin's prefix.
     * If MiniMessage tags are detected, deserializes via MiniMessage and
     * re-serializes to legacy section codes; otherwise uses legacy translation
     *
     * @param message the raw message (may contain legacy codes or MiniMessage tags)
     * @return the colorized message string (legacy section codes), or empty string if input is null
     */
    public static String c(String message) {
        if (message == null)
            return "";

        message = message.replace("%prefix%", PluginUtil.getPluginPrefix());
        if (containsMiniMessageTags(message)) {
            Component component = MINI.deserialize(message);
            return LegacyComponentSerializer.legacySection().serialize(component);
        }

        return ChatColor.translateAlternateColorCodes('&', format(message));
    }

    /**
     * Colorize a list of messages using {@link #c(String)} for each entry
     *
     * @param messages the list of raw messages
     * @return a new list containing colorized messages
     */
    public static List<String> c(List<String> messages) {
        List<String> coloredMessages = new ArrayList<>();
        for (String s : messages)
            coloredMessages.add(c(s));

        return coloredMessages;
    }

    /**
     * Deserialize a MiniMessage string into an Adventure Component
     *
     * @param message MiniMessage-formatted text
     * @return the deserialized component, or an empty component if input is null
     */
    public static Component mm(String message) {
        if (message == null)
            return Component.empty();

        return MINI.deserialize(message);
    }

    /**
     * Heuristically detect if a string likely contains MiniMessage tags
     *
     * @param message the text to inspect
     * @return true if angle brackets are present, suggesting MiniMessage tags
     */
    private static boolean containsMiniMessageTags(String message) {
        return message.contains("<") && message.contains(">");
    }

    /**
     * Apply HEX color formatting for legacy strings when supported by the server version
     *
     * Replaces "%prefix%" placeholder and converts occurrences of patterns like "&#RRGGBB"
     * to the appropriate legacy representation when HEX colors are available
     *
     * @param string the input string (may be null)
     * @return the formatted string with HEX colors applied when supported
     */
    private static String format(String string) {
        if (string == null)
            return "";

        string = string.replace("%prefix%", PluginUtil.getPluginPrefix());
        Matcher matcher = HEX_PATTERN.matcher(string);

        if (VersionUtil.supportsFeature(VersionUtil.Feature.HEX)) {
            while (matcher.find())
                string = string.replace(matcher.group(),
                        "" + net.md_5.bungee.api.ChatColor.of(matcher.group().replace("&", "")));
        }

        return string;
    }

    /**
     * Capitalize the first letter of words and replace the separators with spaces
     *
     * @param message     The message to be beautified
     * @param separator   The separator to be replaced
     */
    public static String beautifyName(String message, String separator) {
        if (message != null && !message.isEmpty()) {
            if (!message.contains(separator))
                return Character.toUpperCase(message.charAt(0)) + message.substring(1).toLowerCase();
            else {
                String[] words = message.split(separator);
                StringBuilder result = new StringBuilder();

                for(int i = 0; i < words.length; ++i) {
                    String word = words[i];
                    if (!word.isEmpty()) {
                        result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1).toLowerCase());
                        if (i < words.length - 1)
                            result.append(" ");
                    }
                }

                return result.toString();
            }
        } else
            return message;
    }

    /**
     * Execute a command as a player
     *
     * @param player      The player that should execute the command
     * @param command     The command to be executed
     */
    public static void executePlayerCommand(Player player, String command) {
        player.performCommand(command);
    }

    /**
     * Execute multiple commands a player
     *
     * @param player      The player that should execute the commands
     * @param commands    The commands to be executed
     */
    public static void executePlayerCommand(Player player, List<String> commands) {
        for (String cmd : commands)
            player.performCommand(cmd);
    }

    /**
     * Execute a single command as the console sender
     *
     * @param command the command to dispatch
     */
    public static void executeConsoleCommand(String command) {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        Bukkit.dispatchCommand(console, command);
    }

    /**
     * Execute multiple commands as the console sender
     *
     * @param commands the list of commands to dispatch in order
     */
    public static void executeConsoleCommand(List<String> commands) {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        for (String cmd : commands)
            Bukkit.dispatchCommand(console, cmd);
    }

    /**
     * Send an action-bar message to a player
     *
     * @param player      The player that the message should be sent to
     * @param message     The message to be sent
     */
    public static void sendActionBarMessage(Player player, String message) {
        if (!VersionUtil.supportsFeature(VersionUtil.Feature.ACTION_BAR)) {
            Messenger.log(Messenger.LogLevel.WARN, "Not supported in current version!");
            return;
        }

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy(ChatUtil.c(message)));
    }

    /**
     * Send a title message to a player (custom timings)
     *
     * @param player      The player that the message should be sent to
     * @param title       The message to be sent
     * @param subtitle    The subtitle to be sent
     * @param i1          Fade-in duration
     * @param i2          How long should the title stay
     * @param i3          Fade-out duration
     */
    public static void sendTitle(Player player, String title, String subtitle, int i1, int i2, int i3) {
        if (!VersionUtil.supportsFeature(VersionUtil.Feature.TITLE)) {
            Messenger.log(Messenger.LogLevel.WARN, "Not supported in current version!");
            return;
        }

        player.sendTitle(ChatUtil.c(title), ChatUtil.c(subtitle), i1, i2, i3);
    }

    /**
     * Send a title message to a player (preset timings)
     *
     * @param player        The player that the message should be sent to
     * @param title         The title to be sent
     * @param subtitle      The subtitle to be sent
     */
    public static void sendTitle(Player player, String title, String subtitle) {
        sendTitle(player, title, subtitle, 10, 40, 10);
    }

    /**
     * Send a title message to all online players
     *
     * @param title         The title to be sent
     * @param subtitle      The subtitle to be sent
     */
    public static void sendTitle(String title, String subtitle) {
        for (Player player : PlayerUtil.getOnlinePlayers())
            sendTitle(player, title, subtitle, 10, 40, 10);
    }

    /**
     * Send a clickable message to a player with customizable action type
     *
     * @param player            The player that the message should be sent to
     * @param actionType        The type of action (OPEN_URL, RUN_COMMAND, SUGGEST_COMMAND, COPY_TO_CLIPBOARD)
     * @param text              The text content of the message
     * @param actionValue       The URL or command for the action
     * @param bold              Whether the text should be bold
     * @param italic            Whether the text should be italic
     * @param underlined        Whether the text should be underlined
     */
    public static void sendClickableMessage(Player player, ClickEvent.Action actionType, String text,
                                            String actionValue, boolean bold, boolean italic, boolean underlined) {
        if (!VersionUtil.supportsFeature(VersionUtil.Feature.CLICKABLE_MESSAGES)) {
            Messenger.log(Messenger.LogLevel.WARN, "Not supported in current version!");
            return;
        }

        TextComponent message = new TextComponent(text);
        message.setClickEvent(new ClickEvent(actionType, actionValue));
        message.setBold(bold);
        message.setItalic(italic);
        message.setUnderlined(underlined);

        player.spigot().sendMessage(message);
    }

    /**
     * Send a small-caps message to a player
     *
     * @param player        The player to send the message to
     * @param message       The message to be sent
     */
    public static void sendSmallCapsMessage(Player player, String message) {
        String formattedMessage = TextConvertor.toSmallCaps(message);
        player.sendMessage(formattedMessage);
    }

    /**
     * Send a toast notification to a player with default icon and style
     *
     * @param receiver the player to receive the toast
     * @param message  the toast text
     */
    public static void sendToast(Player receiver, String message) {
        sendToast(receiver, message, Material.BOOK, ToastStyle.TASK);
    }

    /**
     * Send a toast notification to a player with a custom style and default icon
     *
     * @param receiver  the player to receive the toast
     * @param message   the toast text
     * @param toastStyle the visual style of the toast
     */
    public static void sendToast(Player receiver, String message, ToastStyle toastStyle) {
        sendToast(receiver, message, Material.BOOK, toastStyle);
    }

    /**
     * Send a toast notification to a player with a custom icon and default style
     *
     * @param receiver the player to receive the toast
     * @param message  the toast text
     * @param icon     the item icon to display
     */
    public static void sendToast(Player receiver, String message, Material icon) {
        sendToast(receiver, message, icon, ToastStyle.TASK);
    }

    /**
     * Send a toast notification to a player with a custom icon and style
     *
     * @param player  the player to receive the toast
     * @param message the toast text
     * @param icon    the item icon to display
     * @param style   the visual style of the toast
     */
    public static void sendToast(Player player, String message, Material icon, ToastStyle style) {
        (new AdvancementAccessor(message, icon.toString().toLowerCase(), style)).show(player);
    }
}
