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
     * Remove all blocks in a chunk
     *
     * @param location       The location where the chunk is
     * @param limit          Should it keep the last layer of the chunk (bedrock)
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
                for (Block block : toClear) {
                    block.setType(Material.AIR, false);
                }
            });
        });
    }

    // Regenerate a chunk
    public static void regenerateChunk(Location location) {
        World world = location.getWorld();
        Chunk chunk = location.getChunk();

        int cx = chunk.getX();
        int cz = chunk.getZ();

        world.unloadChunk(cx, cz, true);
        deleteChunkFromDisk(world, cx, cz);
        world.getChunkAt(cx, cz, true);
    }

    // Delete the chunk from the world folder
    private static void deleteChunkFromDisk(World world, int chunkX, int chunkZ) {
        File worldFolder = world.getWorldFolder();

        // Region coordinates (32x32 chunks per file)
        int regionX = chunkX >> 5;
        int regionZ = chunkZ >> 5;

        File regionFile = new File(
                worldFolder,
                "region" + File.separator + "r." + regionX + "." + regionZ + ".mca"
        );

        if (regionFile.exists()) {
            regionFile.delete();
        }
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
