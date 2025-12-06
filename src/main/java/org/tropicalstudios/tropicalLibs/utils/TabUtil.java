package org.tropicalstudios.tropicalLibs.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TabUtil {

    private static List<String> cachedOfflinePlayers = new LinkedList<>();

    /**
     * Filter a list of completion strings by a case-insensitive prefix
     *
     * @param completions list of candidate strings to filter
     * @param prefix      prefix to match (case-insensitive)
     * @return list containing only entries that start with the given prefix
     */
    public static List<String> filter(List<String> completions, String prefix) {
        return completions.stream()
                .filter(s -> s.toLowerCase().startsWith(prefix.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Get cached offline player names matching a case-insensitive prefix
     *
     * @param prefix prefix to match (case-insensitive)
     * @return up to 20 offline player names that start with the given prefix
     */
    public static List<String> getFilteredOfflinePlayerNames(String prefix) {
        int MAX_SUGGESTIONS = 20;
        return cachedOfflinePlayers.stream()
                .filter(name -> name.toLowerCase().startsWith(prefix.toLowerCase()))
                .limit(MAX_SUGGESTIONS)
                .collect(Collectors.toList());
    }

    /**
     * Refresh the cache of offline player names from the server
     */
    public static void updateOfflinePlayerCache() {
        cachedOfflinePlayers = Arrays.stream(Bukkit.getOfflinePlayers())
                .map(OfflinePlayer::getName)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
