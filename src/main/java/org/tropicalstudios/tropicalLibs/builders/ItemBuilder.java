package org.tropicalstudios.tropicalLibs.builders;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.tropicalstudios.tropicalLibs.utils.ChatUtil;
import org.tropicalstudios.tropicalLibs.utils.ItemUtil;
import org.tropicalstudios.tropicalLibs.utils.NBTUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Fluent builder for constructing and customizing ItemStack instances
 *
 * Supports setting display properties (name, lore), flags, enchantments, model data,
 * unbreakable state, amount, and arbitrary NBT tags. Color codes in text are processed
 */
public class ItemBuilder {

    private final ItemStack item;
    private final ItemMeta itemMeta;
    private final Map<String, Object> nbtData;

    /**
     * Create a new builder for the given material
     *
     * @param material the base material (if null, {@link Material#AIR} will be used)
     */
    public ItemBuilder(Material material) {
        if (material == null)
            this.item = new ItemStack(Material.AIR);
        else
            this.item = new ItemStack(material);
        this.itemMeta = item.getItemMeta();
        this.nbtData = new HashMap<>();
    }

    /**
     * Create a new builder based on an existing item stack (cloned internally)
     *
     * @param itemStack the source item stack
     */
    public ItemBuilder(ItemStack itemStack) {
        this.item = itemStack.clone();
        this.itemMeta = this.item.getItemMeta();
        this.nbtData = new HashMap<>();
    }

    /**
     * Set the display name of the item
     *
     * @param name the display name (null becomes empty string)
     */
    public ItemBuilder setName(String name) {
        if (itemMeta != null) {
            String displayName = (name != null) ? name : "";
            itemMeta.setDisplayName(ChatUtil.c(displayName));
        }

        return this;
    }

    /**
     * Set single-line lore for the item
     *
     * @param lore a single line of lore (null becomes empty string)
     */
    public ItemBuilder setLore(String lore) {
        if (itemMeta != null) {
            List<String> formattedLore = new ArrayList<>();
            String safeLore = (lore != null) ? lore : "";
            formattedLore.add(ChatUtil.c(safeLore));
            itemMeta.setLore(formattedLore);
        }

        return this;
    }

    /**
     * Set multi-line lore for the item
     *
     * @param lore list of lore lines (null becomes empty list)
     */
    public ItemBuilder setLore(List<String> lore) {
        if (itemMeta != null) {
            List<String> safeLore = (lore != null) ? lore : new ArrayList<>();
            safeLore = ChatUtil.c(safeLore);
            itemMeta.setLore(safeLore);
        }

        return this;
    }

    /**
     * Add an enchantment to the item
     *
     * @param enchantment the enchantment
     * @param level       the level (force-applied)
     */
    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        if (itemMeta != null)
            itemMeta.addEnchant(enchantment, level, true);

        return this;
    }

    /**
     * Remove an enchantment from the item if present
     *
     * @param enchantment the enchantment to remove
     */
    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        if (itemMeta != null)
            itemMeta.removeEnchant(enchantment);

        return this;
    }

    /**
     * Add item flags to the item meta
     *
     * @param flags the flags to add
     */
    public ItemBuilder addItemFlags(ItemFlag... flags) {
        if (itemMeta != null)
            itemMeta.addItemFlags(flags);

        return this;
    }

    /**
     * Set the unbreakable state of the item
     *
     * @param unbreakable true to make unbreakable
     */
    public ItemBuilder setUnbreakable(boolean unbreakable) {
        if (itemMeta != null)
            itemMeta.setUnbreakable(unbreakable);

        return this;
    }

    /**
     * Set custom model data value
     *
     * @param data custom model data
     */
    public ItemBuilder setCustomModelData(int data) {
        if (itemMeta != null)
            itemMeta.setCustomModelData(data);

        return this;
    }

    /**
     * Set the stack amount
     *
     * @param amount desired amount
     */
    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    /**
     * Store a String NBT tag to be applied on build
     *
     * @param tag   tag key
     * @param value tag value
     */
    public ItemBuilder setNBT(String tag, String value) {
        nbtData.put(tag, value);
        return this;
    }

    /**
     * Store a boolean NBT tag to be applied on build
     *
     * @param tag   tag key
     * @param value tag value
     */
    public ItemBuilder setNBT(String tag, boolean value) {
        nbtData.put(tag, value);
        return this;
    }

    /**
     * Store an int NBT tag to be applied on build
     *
     * @param tag   tag key
     * @param value tag value
     */
    public ItemBuilder setNBT(String tag, int value) {
        nbtData.put(tag, value);
        return this;
    }

    /**
     * Store a double NBT tag to be applied on build
     *
     * @param tag   tag key
     * @param value tag value
     */
    public ItemBuilder setNBT(String tag, double value) {
        nbtData.put(tag, value);
        return this;
    }

    /**
     * Remove a previously stored NBT tag
     *
     * @param tag tag key to remove
     */
    public ItemBuilder removeNBT(String tag) {
        nbtData.remove(tag);
        return this;
    }

    /**
     * Clear all stored NBT tags queued for application
     */
    public ItemBuilder clearNBT() {
        nbtData.clear();
        return this;
    }

    /**
     * Apply meta and queued NBT tags and return the finalized item
     */
    public ItemStack build() {
        if (ItemUtil.isNull(item))
            return item;

        if (itemMeta != null)
            item.setItemMeta(itemMeta);

        for (Map.Entry<String, Object> entry : nbtData.entrySet()) {
            String tag = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof String) {
                NBTUtil.setString(item, tag, (String) value);
            } else if (value instanceof Boolean) {
                NBTUtil.setBool(item, tag, (Boolean) value);
            } else if (value instanceof Integer) {
                NBTUtil.setInt(item, tag, (Integer) value);
            } else if (value instanceof Double) {
                NBTUtil.setDouble(item, tag, (Double) value);
            }
        }

        return item;
    }
}
