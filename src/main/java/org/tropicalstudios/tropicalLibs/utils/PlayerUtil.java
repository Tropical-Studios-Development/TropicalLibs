package org.tropicalstudios.tropicalLibs.utils;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.tropicalstudios.tropicalLibs.Messenger;

import java.util.Collection;

public class PlayerUtil {

    // Static player variables
    public static final int PLAYER_INV_SIZE = 36;
    public static final double BASE_PLAYER_HEALTH = 20.0;
    public static final int BASE_SCALE = 1;

    // Format the ping of a player
    public static String getPing(Player player) {
        if (!VersionUtil.atLeast(VersionUtil.V.v1_17)) {
            Messenger.log(Messenger.LogLevel.WARN, "Not supported in current version!");
            return "";
        }

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

    // Get a player (automatically chooses if it should get it using their real name or display name)
    public static Player getPlayer(String playerName) {
        Player player = Bukkit.getPlayer(playerName);
        if (player == null) {
            player = getPlayerByDisplayName(playerName);
        }
        return player;
    }

    // Get a player from their displayName
    public static Player getPlayerByDisplayName(String displayName) {
        for (Player player : getOnlinePlayers()) {
            if (player.getDisplayName().equals(displayName)) {
                return player;
            }
        }
        return null;
    }

    /**
     * Set the base value of a player attribute
     *
     * @param player     The player which the attribute will be set for
     * @param attribute  The attribute that will be set
     * @param value      The value of the attribute
     */
    public static void setAttribute(Player player, Attribute attribute,double value) {
        player.getAttribute(attribute).setBaseValue(value);
    }

    // Get all online players
    public static Collection<? extends Player> getOnlinePlayers() {
        return Bukkit.getOnlinePlayers();
    }
}
