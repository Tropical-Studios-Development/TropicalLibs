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

    /**
     * Format a player's ping with color codes based on latency
     *
     * Uses green for <= 80 ms, gold for <= 160 ms, and red otherwise. Returns an empty string
     * if running on a server version prior to 1.17 where ping isn't supported
     *
     * @param player the player whose ping should be formatted
     * @return a color-coded string of the player's ping, or an empty string if unsupported
     */
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

    /**
     * Check whether the player is vanished via the "vanished" metadata flag
     *
     * @param player the player to check
     */
    public static boolean isVanished(Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }

    /**
     * Resolve an online player by exact name or, if not found, by display name
     *
     * @param playerName the player's exact name or display name
     * @return the matching online player, or null if none is found
     */
    public static Player getPlayer(String playerName) {
        Player player = Bukkit.getPlayer(playerName);
        if (player == null)
            player = getPlayerByDisplayName(playerName);

        return player;
    }

    /**
     * Find an online player by exact display name match
     *
     * @param displayName the display name to match (case-sensitive)
     * @return the matching player, or null if none matches
     */
    public static Player getPlayerByDisplayName(String displayName) {
        for (Player player : getOnlinePlayers())
            if (player.getDisplayName().equals(displayName))
                return player;

        return null;
    }

    /**
     * Set the base value of a player attribute
     *
     * @param player    the player whose attribute will be modified
     * @param attribute the attribute to set
     * @param value     the new base value for the attribute
     */
    public static void setAttribute(Player player, Attribute attribute,double value) {
        player.getAttribute(attribute).setBaseValue(value);
    }

    /**
     * Get all currently online players
     *
     * @return a collection view of online players
     */
    public static Collection<? extends Player> getOnlinePlayers() {
        return Bukkit.getOnlinePlayers();
    }
}
