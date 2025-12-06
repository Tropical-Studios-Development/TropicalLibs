package org.tropicalstudios.tropicalLibs.builders;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.tropicalstudios.tropicalLibs.utils.ChatUtil;
import org.tropicalstudios.tropicalLibs.utils.NBTUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Fluent builder for creating and customizing player head {@link ItemStack}
 *
 * Supports setting owner (by name or {@link org.bukkit.OfflinePlayer}), display name, lore,
 * enchantments, flags, unbreakable state, custom model data, amount, and queued NBT tags
 */
public class SkullBuilder {

    private final ItemStack skull;
    private final SkullMeta skullMeta;
    private final Map<String, Object> nbtData;

    /**
     * Create a new builder with a fresh PLAYER_HEAD item
     */
    public SkullBuilder() {
        this.skull = new ItemStack(Material.PLAYER_HEAD);
        this.skullMeta = (SkullMeta) skull.getItemMeta();
        this.nbtData = new HashMap<>();
    }

    /**
     * Create a builder based on an existing player head stack (cloned internally)
     *
     * @param skullItem existing {@link ItemStack} of type {@link Material#PLAYER_HEAD}
     * @throws IllegalArgumentException if the provided item is not a player head
     */
    public SkullBuilder(ItemStack skullItem) {
        if (skullItem.getType() != Material.PLAYER_HEAD)
            throw new IllegalArgumentException("ItemStack must be a PLAYER_HEAD");

        this.skull = skullItem.clone();
        this.skullMeta = (SkullMeta) this.skull.getItemMeta();
        this.nbtData = new HashMap<>();
    }

    /**
     * Set the head owner by player name
     *
     * @param playerName the player name
     */
    public SkullBuilder setOwner(String playerName) {
        if (skullMeta != null)
            skullMeta.setOwner(playerName);

        return this;
    }

    /**
     * Set the head owner by {@link OfflinePlayer}
     *
     * @param player the owning player
     */
    public SkullBuilder setOwner(OfflinePlayer player) {
        if (skullMeta != null)
            skullMeta.setOwningPlayer(player);

        return this;
    }

    /**
     * Set the display name
     *
     * @param name display name
     */
    public SkullBuilder setName(String name) {
        if (skullMeta != null)
            skullMeta.setDisplayName(ChatUtil.c(name));

        return this;
    }

    /**
     * Set a single-line lore
     *
     * @param lore a single line of lore
     */
    public SkullBuilder setLore(String lore) {
        if (skullMeta != null) {
            List<String> formattedLore = new ArrayList<>();
            formattedLore.add(ChatUtil.c(lore));
            skullMeta.setLore(formattedLore);
        }

        return this;
    }

    /**
     * Set multi-line lore
     *
     * @param lore list of lore lines
     */
    public SkullBuilder setLore(List<String> lore) {
        if (skullMeta != null) {
            lore = ChatUtil.c(lore);
            skullMeta.setLore(lore);
        }

        return this;
    }

    /**
     * Add an enchantment to the skull
     *
     * @param enchantment the enchantment
     * @param level       level (force-applied)
     */
    public SkullBuilder addEnchantment(Enchantment enchantment, int level) {
        if (skullMeta != null)
            skullMeta.addEnchant(enchantment, level, true);

        return this;
    }

    /**
     * Remove the given enchantment if present
     *
     * @param enchantment the enchantment to remove
     */
    public SkullBuilder removeEnchantment(Enchantment enchantment) {
        if (skullMeta != null)
            skullMeta.removeEnchant(enchantment);

        return this;
    }

    /**
     * Add item flags to the meta
     *
     * @param flags flags to add
     */
    public SkullBuilder addItemFlags(ItemFlag... flags) {
        if (skullMeta != null)
            skullMeta.addItemFlags(flags);

        return this;
    }

    /**
     * Set the unbreakable state
     *
     * @param unbreakable true to make unbreakable
     */
    public SkullBuilder setUnbreakable(boolean unbreakable) {
        if (skullMeta != null)
            skullMeta.setUnbreakable(unbreakable);

        return this;
    }

    /**
     * Set custom model data value
     *
     * @param data custom model data
     */
    public SkullBuilder setCustomModelData(int data) {
        if (skullMeta != null)
            skullMeta.setCustomModelData(data);

        return this;
    }

    /**
     * Set the stack amount
     *
     * @param amount desired amount
     */
    public SkullBuilder setAmount(int amount) {
        skull.setAmount(amount);
        return this;
    }

    /**
     * Queue a String NBT tag to be applied on build
     *
     * @param tag   key
     * @param value value
     */
    public SkullBuilder setNBT(String tag, String value) {
        nbtData.put(tag, value);
        return this;
    }

    /**
     * Queue a boolean NBT tag to be applied on build
     *
     * @param tag   key
     * @param value value
     */
    public SkullBuilder setNBT(String tag, boolean value) {
        nbtData.put(tag, value);
        return this;
    }

    /**
     * Queue an int NBT tag to be applied on build
     *
     * @param tag   key
     * @param value value
     */
    public SkullBuilder setNBT(String tag, int value) {
        nbtData.put(tag, value);
        return this;
    }

    /**
     * Queue a double NBT tag to be applied on build
     *
     * @param tag   key
     * @param value value
     */
    public SkullBuilder setNBT(String tag, double value) {
        nbtData.put(tag, value);
        return this;
    }

    /**
     * Remove a queued NBT tag by key
     *
     * @param tag key to remove
     */
    public SkullBuilder removeNBT(String tag) {
        nbtData.remove(tag);
        return this;
    }

    /**
     * Clear all queued NBT tags
     */
    public SkullBuilder clearNBT() {
        nbtData.clear();
        return this;
    }

    /**
     * Apply meta and queued NBT tags and return the finalized skull item
     *
     * @return the built ItemStack
     */
    public ItemStack build() {
        if (skullMeta != null)
            skull.setItemMeta(skullMeta);

        for (Map.Entry<String, Object> entry : nbtData.entrySet()) {
            String tag = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof String) {
                NBTUtil.setString(skull, tag, (String) value);
            } else if (value instanceof Boolean) {
                NBTUtil.setBool(skull, tag, (Boolean) value);
            } else if (value instanceof Integer) {
                NBTUtil.setInt(skull, tag, (Integer) value);
            } else if (value instanceof Double) {
                NBTUtil.setDouble(skull, tag, (Double) value);
            }
        }

        return skull;
    }
}