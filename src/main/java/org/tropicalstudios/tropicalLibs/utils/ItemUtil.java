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

    /**
     * Apply one point of durability loss to the given item held by the player
     *
     * Respects Creative mode (no damage) and the Unbreaking enchantment chance
     * Plays the break sound and removes the item if it reaches max durability
     *
     * @param player the owning player (used for game mode and sounds)
     * @param item   the item to damage
     */
    public static void applyDurabilityLoss(Player player, ItemStack item) {

        if (player.getGameMode() == GameMode.CREATIVE)
            return;

        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta instanceof Damageable damageable) {
            int unbreakingLevel = item.getEnchantmentLevel(Enchantment.UNBREAKING);
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

    /**
     * Check whether an item is null or air
     *
     * @param item the item to test
     */
    public static boolean isNull(ItemStack item) {
        return item == null || item.getType() == Material.AIR;
    }

    /**
     * Check whether the item is any common tool or weapon type
     *
     * @param item the item to test
     */
    public static boolean isTool(ItemStack item) {
        return isSword(item)
                || isPickaxe(item)
                || isAxe(item)
                || isShovel(item)
                || isHoe(item)
                || isTrident(item)
                || isFishingRod(item)
                || isBow(item)
                || isMace(item)
                || isShield(item);
    }

    /**
     * Check whether the item is a sword
     *
     * @param item the item to test
     */
    public static boolean isSword(ItemStack item) {
        return item.getType().name().endsWith("_SWORD");
    }

    /**
     * Check whether the item is a pickaxe
     *
     * @param item the item to test
     */
    public static boolean isPickaxe(ItemStack item) {
        return item.getType().name().endsWith("_PICKAXE");
    }

    /**
     * Check whether the item is an axe
     *
     * @param item the item to test
     */
    public static boolean isAxe(ItemStack item) {
        return item.getType().name().endsWith("_AXE");
    }

    /**
     * Check whether the item is a shovel
     *
     * @param item the item to test
     */
    public static boolean isShovel(ItemStack item) {
        return item.getType().name().endsWith("_SHOVEL");
    }

    /**
     * Check whether the item is a hoe
     *
     * @param item the item to test
     */
    public static boolean isHoe(ItemStack item) {
        return item.getType().name().endsWith("_HOE");
    }

    /**
     * Check whether the item is a bow
     *
     * @param item the item to test
     */
    public static boolean isBow(ItemStack item) {
        return item.getType() == Material.BOW;
    }

    /**
     * Check whether the item is a fishing rod
     *
     * @param item the item to test
     */
    public static boolean isFishingRod(ItemStack item) {
        return item.getType() == Material.FISHING_ROD;
    }

    /**
        * Check whether the item is a shield
        *
        * @param item the item to test
        */
    public static boolean isShield(ItemStack item) {
        return item.getType() == Material.SHIELD;
    }

    /**
     * Check whether the item is a trident
     *
     * @param item the item to test
     */
    public static boolean isTrident(ItemStack item) {
        return item.getType() == Material.TRIDENT;
    }

    /**
     * Check whether the item is a mace
     *
     * @param item the item to test
     */
    public static boolean isMace(ItemStack item) {
        return item.getType() == Material.MACE;
    }

    /**
     * Check whether the item is a piece of armor
     *
     * @param item the item to test
     */
    public static boolean isArmor(ItemStack item) {
        return item.getType().name().endsWith("_HELMET")
                || item.getType().name().endsWith("_CHESTPLATE")
                || item.getType().name().endsWith("_LEGGINGS")
                || item.getType().name().endsWith("_BOOTS");
    }

    /**
     * Check whether the item is a helmet
     *
     * @param item the item to test
     */
    public static boolean isHelmet(ItemStack item) {
        return item.getType().name().endsWith("_HELMET");
    }

    /**
     * Check whether the item is a chestplate
     *
     * @param item the item to test
     */
    public static boolean isChestplate(ItemStack item) {
        return item.getType().name().endsWith("_CHESTPLATE");
    }

    /**
     * Check whether the item is a pair of leggings
     *
     * @param item the item to test
     */
    public static boolean isLeggings(ItemStack item) {
        return item.getType().name().endsWith("_LEGGINGS");
    }

    /**
     * Check whether the item is a pair of boots
     *
     * @param item the item to test
     */
    public static boolean isBoots(ItemStack item) {
        return item.getType().name().endsWith("_BOOTS");
    }
}
