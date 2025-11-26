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
import org.bukkit.*;
import org.bukkit.entity.Player;

public class RegionUtil {

    // Remove a chunk
    public static void removeChunk(Location location) {
        Chunk chunk = location.getChunk();
        World world = location.getWorld();

        int startX = chunk.getX() << 4;
        int startZ = chunk.getZ() << 4;

        int minY = world.getMinHeight();
        int maxY = world.getMaxHeight();

        for (int x = startX; x < startX + 16; x++) {
            for (int z = startZ; z < startZ + 16; z++) {
                for (int y = minY; y < maxY; y++) {
                    world.getBlockAt(x, y, z).setType(Material.AIR, false);
                }
            }
        }
    }

    // Regenerate a chunk
    public static boolean regenerateChunk(Location location) {
        World world = location.getWorld();
        Chunk chunk = location.getChunk();

        return world.regenerateChunk(chunk.getX(), chunk.getZ());
    }

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
