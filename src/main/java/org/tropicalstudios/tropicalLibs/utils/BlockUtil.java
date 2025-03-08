package org.tropicalstudios.tropicalLibs.utils;

import org.bukkit.Material;

public class BlockUtil {

    // Check if the material is an ore
    public static boolean isOre(Material material) {
        return material.name().endsWith("_ORE") || material == Material.NETHER_QUARTZ_ORE;
    }
}
