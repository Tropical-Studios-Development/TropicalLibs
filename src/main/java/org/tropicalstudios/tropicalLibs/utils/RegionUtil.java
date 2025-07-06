package org.tropicalstudios.tropicalLibs.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class RegionUtil {

    /**
     * Check if the location is protected (WorldGuard)
     *
     * @param player        The player that should be checked
     * @param location      The location that should be checked
     */
    public static boolean isLocationProtected(Player player, Location location) {

        if (WorldEditAllow.isPluginEnabled())
            return WorldEditAllow.isLocationProtected(player, location);

        return false;
    }

    /**
     * Check if a player is in a region
     *
     * @param player        The player that should be checked
     * @param regionName    The region that should be checked
     */
    public static boolean isInRegion(Player player, String regionName) {
        if (WorldEditAllow.isPluginEnabled())
            return WorldEditAllow.isInRegion(player, regionName);

        return false;
    }

    private static class WorldEditAllow {

        // Check if WorldGuard is enabled
        private static boolean isPluginEnabled() {
            return Bukkit.getServer().getPluginManager().getPlugin("WorldGuard") != null;
        }

        private static boolean isLocationProtected(Player player, Location defaultLocation) {
            com.sk89q.worldedit.util.Location location = BukkitAdapter.adapt(defaultLocation);
            LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();

            if (container == null)
                return false;

            RegionQuery query = container.createQuery();
            if (query.getApplicableRegions(location).size() == 0)
                return false;

            boolean result = query.testState(location, localPlayer, Flags.BLOCK_BREAK);
            return !result;
        }

        private static boolean isInRegion(Player player, String regionName) {
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();

            if (container == null)
                return false;

            RegionManager regionManager = container.get(BukkitAdapter.adapt(player.getWorld()));

            if (regionManager == null)
                return false;

            com.sk89q.worldedit.util.Location location = BukkitAdapter.adapt(player.getLocation());
            ApplicableRegionSet regions = container.createQuery().getApplicableRegions(location);

            for (ProtectedRegion region : regions) {
                if (region.getId().equalsIgnoreCase(regionName)) {
                    return true;
                }
            }

            return false;
        }
    }
}
