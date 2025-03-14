package org.tropicalstudios.tropicalLibs.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class BlockUtil {

    // Apply fortune logic to the item
    public static Collection<ItemStack> applyFortune(Collection<ItemStack> drops, int fortuneLevel) {
        Random rand = new Random();
        Collection<ItemStack> newDrops = new ArrayList<>();

        for (ItemStack drop : drops) {
            int amount = drop.getAmount();
            int bonus = 0;

            for (int i = 0; i < fortuneLevel; i++)
                if (rand.nextFloat() < 0.33)
                    bonus++;

            amount += bonus;
            drop.setAmount(amount);
            newDrops.add(drop);
        }

        return newDrops;
    }

    // Get the xp according to the broken block
    public static int getExpFromBlock(Material material) {
        return switch (material) {
            case COAL_ORE, NETHER_GOLD_ORE -> 1;
            case DIAMOND_ORE, EMERALD_ORE -> 5;
            case LAPIS_ORE, NETHER_QUARTZ_ORE -> 3;
            case REDSTONE_ORE -> 2;
            default -> 0;
        };
    }

    // Check if the material is an ore
    public static boolean isOre(Material material) {
        return material.name().endsWith("_ORE") || material == Material.NETHER_QUARTZ_ORE;
    }

    public static boolean isLog(Material material) {
        return material == Material.OAK_LOG ||
                material == Material.SPRUCE_LOG ||
                material == Material.BIRCH_LOG ||
                material == Material.JUNGLE_LOG ||
                material == Material.ACACIA_LOG ||
                material == Material.DARK_OAK_LOG ||
                material == Material.STRIPPED_OAK_LOG ||
                material == Material.STRIPPED_SPRUCE_LOG ||
                material == Material.STRIPPED_BIRCH_LOG ||
                material == Material.STRIPPED_JUNGLE_LOG ||
                material == Material.STRIPPED_ACACIA_LOG ||
                material == Material.STRIPPED_DARK_OAK_LOG ||
                material == Material.MANGROVE_LOG ||
                material == Material.STRIPPED_MANGROVE_LOG ||
                material == Material.CHERRY_LOG ||
                material == Material.STRIPPED_CHERRY_LOG ||
                material == Material.CRIMSON_STEM ||
                material == Material.WARPED_STEM ||
                material == Material.STRIPPED_CRIMSON_STEM ||
                material == Material.STRIPPED_WARPED_STEM;
    }
}
