package org.tropicalstudios.tropicalLibs.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class RegionUtil {

    public static boolean isLocationProtected(Player player, Location location) {

        if (WorldEditAllow.isPluginEnabled())
            return WorldEditAllow.isLocationProtected(player, location);

        return false;
    }

    private static class WorldEditAllow {

        private static boolean isPluginEnabled() {
            return Bukkit.getServer().getPluginManager().getPlugin("WorldGuard") != null;
        }

        private static boolean isLocationProtected(Player player, Location defaultLocation) {
            com.sk89q.worldedit.util.Location location = BukkitAdapter.adapt(defaultLocation);
            LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = container.createQuery();
            boolean result = query.testState(location, localPlayer, Flags.BLOCK_BREAK);
            return !result;
        }
    }
}
