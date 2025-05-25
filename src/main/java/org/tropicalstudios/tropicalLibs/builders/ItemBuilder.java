package org.tropicalstudios.tropicalLibs.builders;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.tropicalstudios.tropicalLibs.utils.ChatUtil;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private final ItemStack item;
    private final ItemMeta itemMeta;

    public ItemBuilder(ItemStack item, ItemMeta itemMeta) {
        this.item = item;
        this.itemMeta = itemMeta;
    }

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
        this.itemMeta = item.getItemMeta();
    }

    public ItemBuilder(ItemStack itemStack) {
        this.item = itemStack.clone();
        this.itemMeta = this.item.getItemMeta();
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

    public ItemStack build() {
        if (itemMeta != null)
            item.setItemMeta(itemMeta);

        return item;
    }
}
