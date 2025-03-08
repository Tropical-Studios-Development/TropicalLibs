package org.tropicalstudios.tropicalLibs.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemUtil {

    // Check if an item is a sword
    public static boolean isSword(ItemStack item) {
        return item.getType() == Material.WOODEN_SWORD || item.getType() == Material.STONE_SWORD ||
                item.getType() == Material.IRON_SWORD || item.getType() == Material.GOLDEN_SWORD ||
                item.getType() == Material.DIAMOND_SWORD || item.getType() == Material.NETHERITE_SWORD;
    }

    // Check if an item is a pickaxe
    public static boolean isPickaxe(ItemStack item) {
        return item.getType() == Material.WOODEN_PICKAXE || item.getType() == Material.STONE_PICKAXE ||
                item.getType() == Material.IRON_PICKAXE || item.getType() == Material.GOLDEN_PICKAXE ||
                item.getType() == Material.DIAMOND_PICKAXE || item.getType() == Material.NETHERITE_PICKAXE;
    }

    // Check if an item is an axe
    public static boolean isAxe(ItemStack item) {
        return item.getType() == Material.WOODEN_AXE || item.getType() == Material.STONE_AXE ||
                item.getType() == Material.IRON_AXE || item.getType() == Material.GOLDEN_AXE ||
                item.getType() == Material.DIAMOND_AXE || item.getType() == Material.NETHERITE_AXE;
    }

    // Check if an item is a shovel
    public static boolean isShovel(ItemStack item) {
        return item.getType() == Material.WOODEN_SHOVEL || item.getType() == Material.STONE_SHOVEL ||
                item.getType() == Material.IRON_SHOVEL || item.getType() == Material.GOLDEN_SHOVEL ||
                item.getType() == Material.DIAMOND_SHOVEL || item.getType() == Material.NETHERITE_SHOVEL;
    }

    // Check if an item is a hoe
    public static boolean isHoe(ItemStack item) {
        return item.getType() == Material.WOODEN_HOE || item.getType() == Material.STONE_HOE ||
                item.getType() == Material.IRON_HOE || item.getType() == Material.GOLDEN_HOE ||
                item.getType() == Material.DIAMOND_HOE || item.getType() == Material.NETHERITE_HOE;
    }

    public static boolean isBow(ItemStack item) {
        return item.getType() == Material.BOW;
    }

    // Check if an item is armor
    public static boolean isArmor(ItemStack item) {
        return item.getType().name().endsWith("_HELMET")
                || item.getType().name().endsWith("_CHESTPLATE")
                || item.getType().name().endsWith("_LEGGINGS")
                || item.getType().name().endsWith("_BOOTS");
    }
}
