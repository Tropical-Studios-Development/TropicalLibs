package org.tropicalstudios.tropicalLibs.utils;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.tropicalstudios.tropicalLibs.Messenger;

public class HelperUtil {

    /**
     * Check if the command sender is console
     *
     * @param commandSender The sender to check
     */
    public static boolean isConsole(CommandSender commandSender) {
        return commandSender instanceof ConsoleCommandSender;
    }

    /**
     * Check if the command sender is player
     *
     * @param commandSender The sender to check
     */
    public static boolean isPlayer(CommandSender commandSender) {
        return commandSender instanceof Player;
    }

    /**
     * Check if the command sender is command block
     *
     * @param commandSender The sender to check
     */
    public static boolean isCommandBlock(CommandSender commandSender) {
        return commandSender instanceof CommandBlock;
    }

    /**
     * Check how the code is executed (sync/async)
     *
     * @return {@code "SYNC"} if on the primary server thread, otherwise {@code "ASYNC"}
     */
    public static void checkThreadExecution() {
        Messenger.log(Messenger.LogLevel.INFO, Bukkit.isPrimaryThread() ? "Code running SYNC" : "Code running ASYNC");
    }

    /**
     * Get a statistic
     *
     * @param name The enum name of the statistic (e.g., {@code MINE_BLOCK})
     */
    public static Statistic getStatistic(String name) {
        try {
            return Statistic.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
