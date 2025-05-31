package org.tropicalstudios.tropicalLibs.builders;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.tropicalstudios.tropicalLibs.utils.ChatUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuBuilder {

    private final FileConfiguration config;
    private Inventory inventory;
    private String title;
    private int size;
    private final Map<String, String> placeholders;

    public MenuBuilder(FileConfiguration config) {
        this.config = config;
        this.placeholders = new HashMap<>();
        this.size = 27; // Default size
        this.title = "Menu"; // Default title

        // Load basic menu properties
        loadMenuProperties();
    }

    private void loadMenuProperties() {
        if (config != null) {
            this.title = config.getString("title", "Menu");
            this.size = config.getInt("size", 27);
        }
    }

    public MenuBuilder setTitle(String title) {
        this.title = (title != null) ? title : "Menu";
        return this;
    }

    public MenuBuilder setSize(int size) {
        // Validate inventory size
        if (size % 9 == 0 && size > 0 && size <= 54) {
            this.size = size;
        } else {
            System.out.println("Invalid inventory size: " + size + ". Using default size 27.");
            this.size = 27;
        }
        return this;
    }

    public MenuBuilder addPlaceholder(String key, String value) {
        this.placeholders.put(key, value != null ? value : "");
        return this;
    }

    public MenuBuilder addPlaceholders(Map<String, String> placeholders) {
        if (placeholders != null) {
            this.placeholders.putAll(placeholders);
        }
        return this;
    }

    private String replacePlaceholders(String text) {
        if (text == null) return "";

        String result = text;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            result = result.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return result;
    }

    private List<String> replacePlaceholders(List<String> textList) {
        if (textList == null) return new ArrayList<>();

        List<String> result = new ArrayList<>();
        for (String line : textList) {
            result.add(replacePlaceholders(line));
        }
        return result;
    }

    public Inventory build() {
        if (config == null) {
            System.out.println("Configuration is null, cannot build menu.");
            return Bukkit.createInventory(null, 9, "Error");
        }

        // Create inventory with processed title
        String processedTitle = ChatUtil.c(replacePlaceholders(title));
        inventory = Bukkit.createInventory(null, size, processedTitle);

        // Load items from configuration
        loadItemsFromConfig();

        return inventory;
    }

    private void loadItemsFromConfig() {
        ConfigurationSection itemsSection = config.getConfigurationSection("items");

        if (itemsSection == null) {
            System.out.println("No items section found in configuration.");
            return;
        }

        // Loop through all items in the configuration
        for (String key : itemsSection.getKeys(false)) {
            ConfigurationSection itemSection = itemsSection.getConfigurationSection(key);
            if (itemSection == null) continue;

            String materialName = itemSection.getString("material", "STONE");
            String itemName = itemSection.getString("name", "");
            List<String> lore = itemSection.getStringList("lore");
            String action = itemSection.getString("action", "");

            // Check for single slot or multiple slots
            List<Integer> slots = new ArrayList<>();
            if (itemSection.contains("slot")) {
                slots.add(itemSection.getInt("slot"));
            } else if (itemSection.contains("slots")) {
                slots = itemSection.getIntegerList("slots");
            }

            // Skip items without valid slots or AIR materials
            if (slots.isEmpty() || materialName.equals("AIR"))
                continue;

            Material material;
            try {
                material = Material.valueOf(materialName.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid material: " + materialName + " for item " + key);
                continue;
            }

            // Create the item with placeholders replaced
            ItemStack item = new ItemBuilder(material)
                    .setName(ChatUtil.c(replacePlaceholders(itemName)))
                    .setLore(replacePlaceholders(lore).stream()
                            .map(ChatUtil::c)
                            .collect(java.util.stream.Collectors.toList()))
                    .setNBT("menu-action", action) // Set action as NBT
                    .build();

            // Set the item in all specified slots
            for (int slot : slots) {
                if (slot >= 0 && slot < inventory.getSize()) {
                    inventory.setItem(slot, item);
                }
            }
        }
    }

    public void openFor(Player player) {
        if (inventory == null) {
            build();
        }

        player.openInventory(inventory);
    }

    public Inventory getInventory() {
        return inventory;
    }

    // Get current title
    public String getTitle() {
        return title;
    }

    // Get current size
    public int getSize() {
        return size;
    }
}