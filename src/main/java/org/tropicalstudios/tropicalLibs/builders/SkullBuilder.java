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

public class SkullBuilder {

    private final ItemStack skull;
    private final SkullMeta skullMeta;
    private final Map<String, Object> nbtData;

    public SkullBuilder() {
        this.skull = new ItemStack(Material.PLAYER_HEAD);
        this.skullMeta = (SkullMeta) skull.getItemMeta();
        this.nbtData = new HashMap<>();
    }

    public SkullBuilder(ItemStack skullItem) {
        if (skullItem.getType() != Material.PLAYER_HEAD)
            throw new IllegalArgumentException("ItemStack must be a PLAYER_HEAD");

        this.skull = skullItem.clone();
        this.skullMeta = (SkullMeta) this.skull.getItemMeta();
        this.nbtData = new HashMap<>();
    }

    public SkullBuilder setOwner(String playerName) {
        if (skullMeta != null)
            skullMeta.setOwner(playerName);

        return this;
    }

    public SkullBuilder setOwner(OfflinePlayer player) {
        if (skullMeta != null)
            skullMeta.setOwningPlayer(player);

        return this;
    }

    public SkullBuilder setName(String name) {
        if (skullMeta != null)
            skullMeta.setDisplayName(ChatUtil.c(name));

        return this;
    }

    public SkullBuilder setLore(String lore) {
        if (skullMeta != null) {
            List<String> formattedLore = new ArrayList<>();
            formattedLore.add(ChatUtil.c(lore));
            skullMeta.setLore(formattedLore);
        }

        return this;
    }

    public SkullBuilder setLore(List<String> lore) {
        if (skullMeta != null) {
            lore = ChatUtil.c(lore);
            skullMeta.setLore(lore);
        }

        return this;
    }

    public SkullBuilder addEnchantment(Enchantment enchantment, int level) {
        if (skullMeta != null)
            skullMeta.addEnchant(enchantment, level, true);

        return this;
    }

    public SkullBuilder removeEnchantment(Enchantment enchantment) {
        if (skullMeta != null)
            skullMeta.removeEnchant(enchantment);

        return this;
    }

    public SkullBuilder addItemFlags(ItemFlag... flags) {
        if (skullMeta != null)
            skullMeta.addItemFlags(flags);

        return this;
    }

    public SkullBuilder setUnbreakable(boolean unbreakable) {
        if (skullMeta != null)
            skullMeta.setUnbreakable(unbreakable);

        return this;
    }

    public SkullBuilder setCustomModelData(int data) {
        if (skullMeta != null)
            skullMeta.setCustomModelData(data);

        return this;
    }

    public SkullBuilder setAmount(int amount) {
        skull.setAmount(amount);
        return this;
    }

    public SkullBuilder setNBT(String tag, String value) {
        nbtData.put(tag, value);
        return this;
    }

    public SkullBuilder setNBT(String tag, boolean value) {
        nbtData.put(tag, value);
        return this;
    }

    public SkullBuilder setNBT(String tag, int value) {
        nbtData.put(tag, value);
        return this;
    }

    public SkullBuilder setNBT(String tag, double value) {
        nbtData.put(tag, value);
        return this;
    }

    public SkullBuilder removeNBT(String tag) {
        nbtData.remove(tag);
        return this;
    }

    public SkullBuilder clearNBT() {
        nbtData.clear();
        return this;
    }

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