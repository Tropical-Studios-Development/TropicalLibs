package org.tropicalstudios.tropicalLibs.utils;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class HelperUtil {

    // Check if the command sender is console
    public static boolean isConsole(CommandSender commandSender) {
        return commandSender instanceof ConsoleCommandSender;
    }

    // Check if the command sender is player
    public static boolean isPlayer(CommandSender commandSender) {
        return commandSender instanceof Player;
    }

    // Check if the command sender is command block
    public static boolean isCommandBlock(CommandSender commandSender) {
        return commandSender instanceof CommandBlock;
    }

    // Check how is the code executed (sync/async)
    public static String checkThreadExecution() {
        return Bukkit.isPrimaryThread() ? "SYNC" : "ASYNC";
    }

    // Get a statistic
    public static Statistic getStatistic(String name) {
        try {
            return Statistic.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
