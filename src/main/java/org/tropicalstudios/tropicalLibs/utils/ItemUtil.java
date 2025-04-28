package org.tropicalstudios.tropicalLibs.utils;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {

    // Apply durability loss
    public static void applyDurabilityLoss(Player player, ItemStack item) {

        if (player.getGameMode() == GameMode.CREATIVE)
            return;

        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta instanceof Damageable damageable) {
            int unbreakingLevel = item.getEnchantmentLevel(Enchantment.DURABILITY);
            if (unbreakingLevel <= 0 || Math.random() < (1.0 / (unbreakingLevel + 1))) {
                int newDamage = damageable.getDamage() + 1;
                int maxDurability = item.getType().getMaxDurability();
                if (newDamage >= maxDurability) {
                    player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
                    item.setAmount(0);
                    return;
                }
                damageable.setDamage(newDamage);
                item.setItemMeta(damageable);
            }
        }
    }

    // Check if an item is null
    public static boolean isNull(ItemStack item) {
        return item == null || item.getType() == Material.AIR;
    }

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

    // Check if an item is a bow
    public static boolean isBow(ItemStack item) {
        return item.getType() == Material.BOW;
    }

    // Check if an item is a fishing rod
    public static boolean isFishingRod(ItemStack item) {
        return item.getType() == Material.FISHING_ROD;
    }

    // Check if an item is armor
    public static boolean isArmor(ItemStack item) {
        return item.getType().name().endsWith("_HELMET")
                || item.getType().name().endsWith("_CHESTPLATE")
                || item.getType().name().endsWith("_LEGGINGS")
                || item.getType().name().endsWith("_BOOTS");
    }

    // Check if an item is helmet
    public static boolean isHelmet(ItemStack item) {
        return item.getType().name().endsWith("_HELMET");
    }

    // Check if an item is chestplate
    public static boolean isChestplate(ItemStack item) {
        return item.getType().name().endsWith("_CHESTPLATE");
    }

    // Check if an item is leggings
    public static boolean isLeggings(ItemStack item) {
        return item.getType().name().endsWith("_LEGGINGS");
    }

    // Check if an item is boots
    public static boolean isBoots(ItemStack item) {
        return item.getType().name().endsWith("_BOOTS");
    }
}
