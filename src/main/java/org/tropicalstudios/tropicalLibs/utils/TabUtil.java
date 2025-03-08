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

    public static List<String> filter(List<String> completions, String prefix) {
        return completions.stream()
                .filter(s -> s.toLowerCase().startsWith(prefix.toLowerCase()))
                .collect(Collectors.toList());
    }

    public static List<String> getFilteredOfflinePlayerNames(String prefix) {
        int MAX_SUGGESTIONS = 20;
        return cachedOfflinePlayers.stream()
                .filter(name -> name.toLowerCase().startsWith(prefix.toLowerCase()))
                .limit(MAX_SUGGESTIONS)
                .collect(Collectors.toList());
    }

    public static void updateOfflinePlayerCache() {
        cachedOfflinePlayers = Arrays.stream(Bukkit.getOfflinePlayers())
                .map(OfflinePlayer::getName)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
