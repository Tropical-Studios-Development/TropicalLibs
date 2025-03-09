package org.tropicalstudios.tropicalLibs.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

public class PlayerUtil {

    public static final int PLAYER_INV_SIZE = 36;
    public static final double BASE_PLAYER_HEALTH = 20.0;

    public static String getPing(Player player) {
        int ping =  player.getPing();
        return (ping <= 80) ? "&a" + ping :
                (ping <= 160) ? "&6" + ping :
                        "&c" + ping;
    }

    public static Collection<? extends Player> getOnlinePlayers() {
        return Bukkit.getOnlinePlayers();
    }
}
