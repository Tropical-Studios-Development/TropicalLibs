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
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.tropicalstudios.tropicalLibs.schedulers.TropicalScheduler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RegionUtil {

    /**
     * Remove all blocks in the chunk at the given location.
     *
     * @param location the location within the target chunk
     * @param limit    whether to keep the lowest layer (bedrock)
     */
    public static void removeChunk(Location location, boolean limit) {
        TropicalScheduler.async().run(() -> {
            Chunk chunk = location.getChunk();
            World world = location.getWorld();

            int startX = chunk.getX() << 4;
            int startZ = chunk.getZ() << 4;

            int minY = world.getMinHeight() + 1;
            int maxY = world.getMaxHeight();

            List<Block> toClear = new ArrayList<>(16 * 16 * (maxY - minY));

            for (int x = startX; x < startX + 16; x++) {
                for (int z = startZ; z < startZ + 16; z++) {
                    for (int y = minY; y < maxY; y++) {
                        Block block = world.getBlockAt(x, y, z);
                        toClear.add(block);
                    }
                }
            }

            TropicalScheduler.sync().run(() -> {
                for (Block block : toClear)
                    block.setType(Material.AIR, false);
            });
        });
    }

    /**
     * Regenerate the chunk at the given location by unloading, deleting its region file entry,
     * and forcing the server to load it again
     *
     * @param location the location within the chunk to regenerate
     */
    public static void regenerateChunk(Location location) {
        World world = location.getWorld();
        Chunk chunk = location.getChunk();

        int cx = chunk.getX();
        int cz = chunk.getZ();

        world.unloadChunk(cx, cz, true);
        deleteChunkFromDisk(world, cx, cz);
        world.getChunkAt(cx, cz, true);
    }

    /**
     * Delete the target chunk's data from disk by removing its entry from the corresponding
     * region file (32x32 chunks per file)
     *
     * @param world  the world containing the chunk
     * @param chunkX the chunk X coordinate
     * @param chunkZ the chunk Z coordinate
     */
    private static void deleteChunkFromDisk(World world, int chunkX, int chunkZ) {
        File worldFolder = world.getWorldFolder();

        int regionX = chunkX >> 5;
        int regionZ = chunkZ >> 5;

        File regionFile = new File(
                worldFolder,
                "region" + File.separator + "r." + regionX + "." + regionZ + ".mca"
        );

        if (regionFile.exists())
            regionFile.delete();
    }

    /**
     * Check whether a location is protected for a given player (WorldGuard)
     *
     * @param player   the player to test permissions for
     * @param location the location to check
     */
    public static boolean isLocationProtected(Player player, Location location) {
        if (WorldEditAllow.isPluginEnabled())
            return WorldEditAllow.isLocationProtected(player, location);

        return false;
    }

    /**
     * Check whether a player is inside a WorldGuard region by name
     *
     * @param player     the player to check
     * @param regionName the region identifier (case-insensitive)
     */
    public static boolean isInRegion(Player player, String regionName) {
        if (WorldEditAllow.isPluginEnabled())
            return WorldEditAllow.isInRegion(player, regionName);

        return false;
    }

    private static class WorldEditAllow {

        /**
         * Determine whether the WorldGuard plugin is installed and enabled
         *
         * @return true if WorldGuard is available, false otherwise
         */
        private static boolean isPluginEnabled() {
            return Bukkit.getServer().getPluginManager().getPlugin("WorldGuard") != null;
        }

        /**
         * Test whether the given location is protected against the specified player's actions
         * Uses WorldGuard region state and the BLOCK_BREAK flag as a proxy for protection
         *
         * @param player          the player to test
         * @param defaultLocation the location to check
         */
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

        /**
         * Determine whether a player is located within a specific WorldGuard region
         *
         * @param player     the player to check
         * @param regionName the region identifier (case-insensitive)
         */
        private static boolean isInRegion(Player player, String regionName) {
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            if (container == null)
                return false;

            RegionManager regionManager = container.get(BukkitAdapter.adapt(player.getWorld()));
            if (regionManager == null)
                return false;

            com.sk89q.worldedit.util.Location location = BukkitAdapter.adapt(player.getLocation());
            ApplicableRegionSet regions = container.createQuery().getApplicableRegions(location);

            for (ProtectedRegion region : regions)
                if (region.getId().equalsIgnoreCase(regionName))
                    return true;

            return false;
        }
    }
}
