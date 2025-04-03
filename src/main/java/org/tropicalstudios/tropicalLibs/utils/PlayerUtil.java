package org.tropicalstudios.tropicalLibs.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

import java.util.Collection;

public class PlayerUtil {

    // Static player variables
    public static final int PLAYER_INV_SIZE = 36;
    public static final double BASE_PLAYER_HEALTH = 20.0;

    // Format the ping of a player
    public static String getPing(Player player) {
        int ping =  player.getPing();
        return (ping <= 80) ? "&a" + ping :
                (ping <= 160) ? "&6" + ping :
                        "&c" + ping;
    }

    // Check if the player is vanished
    public static boolean isVanished(Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }

    // Get all online players
    public static Collection<? extends Player> getOnlinePlayers() {
        return Bukkit.getOnlinePlayers();
    }
}
