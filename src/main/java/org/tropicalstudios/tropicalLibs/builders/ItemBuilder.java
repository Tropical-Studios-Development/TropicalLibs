package org.tropicalstudios.tropicalLibs.builders;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.tropicalstudios.tropicalLibs.utils.ChatUtil;
import org.tropicalstudios.tropicalLibs.utils.NBTUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemBuilder {

    private final ItemStack item;
    private final ItemMeta itemMeta;
    private final Map<String, Object> nbtData;

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
        this.itemMeta = item.getItemMeta();
        this.nbtData = new HashMap<>();
    }

    public ItemBuilder(ItemStack itemStack) {
        this.item = itemStack.clone();
        this.itemMeta = this.item.getItemMeta();
        this.nbtData = new HashMap<>();
    }

    public ItemBuilder setName(String name) {
        if (itemMeta != null)
            itemMeta.setDisplayName(ChatUtil.c(name));

        return this;
    }

    public ItemBuilder setLore(String lore) {
        if (itemMeta != null) {
            List<String> formattedLore = new ArrayList<>();
            formattedLore.add(ChatUtil.c(lore));
            itemMeta.setLore(formattedLore);
        }

        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        if (itemMeta != null) {
            lore = ChatUtil.c(lore);
            itemMeta.setLore(lore);
        }

        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        if (itemMeta != null)
            itemMeta.addEnchant(enchantment, level, true);

        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        if (itemMeta != null)
            itemMeta.removeEnchant(enchantment);

        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... flags) {
        if (itemMeta != null)
            itemMeta.addItemFlags(flags);

        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        if (itemMeta != null)
            itemMeta.setUnbreakable(unbreakable);

        return this;
    }

    public ItemBuilder setCustomModelData(int data) {
        if (itemMeta != null)
            itemMeta.setCustomModelData(data);

        return this;
    }

    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder setNBT(String tag, String value) {
        nbtData.put(tag, value);
        return this;
    }

    public ItemBuilder setNBT(String tag, boolean value) {
        nbtData.put(tag, value);
        return this;
    }

    public ItemBuilder setNBT(String tag, int value) {
        nbtData.put(tag, value);
        return this;
    }

    public ItemBuilder setNBT(String tag, double value) {
        nbtData.put(tag, value);
        return this;
    }

    public ItemBuilder removeNBT(String tag) {
        nbtData.remove(tag);
        return this;
    }

    public ItemBuilder clearNBT() {
        nbtData.clear();
        return this;
    }

    public ItemStack build() {
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
