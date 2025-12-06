package org.tropicalstudios.tropicalLibs.builders;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.tropicalstudios.tropicalLibs.Messenger;
import org.tropicalstudios.tropicalLibs.utils.ChatUtil;

import java.util.*;

/**
 * Builder for configurable GUI menus from a file
 *
 * Handles placeholder replacement, title/size configuration, and populating inventory slots
 * from a config section
 */
public class MenuBuilder {

    private static final Map<UUID, MenuBuilder> openMenus = new HashMap<>();
    private final FileConfiguration config;
    private Inventory inventory;
    private String title;
    private int size;
    private String targetName;
    private final Map<String, String> placeholders;

    /**
     * Create a new menu builder from the given configuration
     *
     * Expected keys:
     * - title: String (menu title)
     * - size: int (inventory size, multiple of 9, up to 54)
     * - items: section defining items and their slots/properties
     *
     * @param config the menu configuration
     */
    public MenuBuilder(FileConfiguration config) {
        this.config = config;
        this.placeholders = new HashMap<>();
        this.size = 27; // Default size
        this.title = "Menu"; // Default title
        this.targetName = "";

        // Load basic menu properties
        loadMenuProperties();
    }

    private void loadMenuProperties() {
        if (config != null) {
            this.title = config.getString("title", "Menu");
            this.size = config.getInt("size", 27);
        }
    }

    /**
     * Set the raw title
     *
     * @param title menu title, defaults to "Menu" when null
     */
    public MenuBuilder setTitle(String title) {
        this.title = (title != null) ? title : "Menu";
        return this;
    }

    /**
     * Set an optional target name that will be written into each built item's NBT
     *
     * @param targetName value for the "target" NBT tag
     */
    public MenuBuilder setTarget(String targetName) {
        this.targetName = targetName;
        return this;
    }

    /**
     * Set the inventory size. Must be a positive multiple of 9 and <= 54
     * Falls back to 27 with a logged warning if invalid
     *
     * @param size requested size
     */
    public MenuBuilder setSize(int size) {
        // Validate inventory size
        if (size % 9 == 0 && size > 0 && size <= 54) {
            this.size = size;
        } else {
            Messenger.log(Messenger.LogLevel.WARN, "Invalid inventory size: " + size + ". Using default size 27");
            this.size = 27;
        }
        return this;
    }

    /**
     * Add a single placeholder for title/lore replacement
     *
     * @param key   placeholder key without braces
     * @param value replacement value (null becomes empty string)
     */
    public MenuBuilder addPlaceholder(String key, String value) {
        this.placeholders.put(key, value != null ? value : "");
        return this;
    }

    /**
     * Add multiple placeholders at once
     *
     * @param placeholders key/value map (ignored if null)
     */
    public MenuBuilder addPlaceholders(Map<String, String> placeholders) {
        if (placeholders != null)
            this.placeholders.putAll(placeholders);

        return this;
    }

    private String replacePlaceholders(String text) {
        if (text == null)
            return "";

        String result = text;
        for (Map.Entry<String, String> entry : placeholders.entrySet())
            result = result.replace("{" + entry.getKey() + "}", entry.getValue());

        return result;
    }

    private List<String> replacePlaceholders(List<String> textList) {
        if (textList == null)
            return new ArrayList<>();

        List<String> result = new ArrayList<>();
        for (String line : textList)
            result.add(replacePlaceholders(line));

        return result;
    }

    /**
     * Build the inventory according to the configuration and placeholders
     */
    public Inventory build() {
        if (config == null) {
            Messenger.log(Messenger.LogLevel.WARN, "Configuration is null, cannot build menu!");
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
            Messenger.log(Messenger.LogLevel.WARN, "No items section found in configuration!");
            return;
        }

        // Loop through all items in the configuration
        for (String key : itemsSection.getKeys(false)) {
            ConfigurationSection itemSection = itemsSection.getConfigurationSection(key);
            if (itemSection == null)
                continue;

            String materialName = itemSection.getString("material", "STONE");
            String itemName = itemSection.getString("name", "");
            List<String> lore = itemSection.getStringList("lore");
            String action = itemSection.getString("action", "");

            // Check for single slot or multiple slots
            List<Integer> slots = new ArrayList<>();
            if (itemSection.contains("slot")) {
                slots.add(itemSection.getInt("slot"));
            } else if (itemSection.contains("slots")) {
                List<String> slotStrings = itemSection.getStringList("slots");
                for (String slotString : slotStrings) {
                    if (slotString.contains("-")) {
                        // Handle range notation (e.g., "0-9")
                        String[] parts = slotString.split("-");
                        if (parts.length == 2) {
                            try {
                                int start = Integer.parseInt(parts[0].trim());
                                int end = Integer.parseInt(parts[1].trim());
                                for (int i = start; i <= end; i++) {
                                    slots.add(i);
                                }
                            } catch (NumberFormatException e) {
                                Messenger.log(Messenger.LogLevel.WARN, "Invalid slot range: " + slotString + " for item " + key);
                            }
                        } else {
                            Messenger.log(Messenger.LogLevel.WARN, "Invalid slot range format: " + slotString + " for item " + key);
                        }
                    } else {
                        // Handle individual slot numbers
                        try {
                            slots.add(Integer.parseInt(slotString.trim()));
                        } catch (NumberFormatException e) {
                            Messenger.log(Messenger.LogLevel.WARN, "Invalid slot number: " + slotString + " for item " + key);
                        }
                    }
                }
            }

            // Skip items without valid slots or AIR materials
            if (slots.isEmpty() || materialName.equals("AIR"))
                continue;

            Material material;
            try {
                material = Material.valueOf(materialName.toUpperCase());
            } catch (IllegalArgumentException e) {
                Messenger.log(Messenger.LogLevel.WARN, "Invalid material: " + materialName + " for item " + key);
                continue;
            }

            // Create the item with placeholders replaced
            ItemStack item = new ItemBuilder(material)
                    .setName(ChatUtil.c(replacePlaceholders(itemName)))
                    .setLore(replacePlaceholders(lore).stream()
                            .map(ChatUtil::c)
                            .collect(java.util.stream.Collectors.toList()))
                    .setNBT("menu-action", action) // Set action as NBT
                    .setNBT("target", targetName) // Set target as NBT
                    .build();

            // Set the item in all specified slots
            for (int slot : slots)
                if (slot >= 0 && slot < inventory.getSize())
                    inventory.setItem(slot, item);
        }
    }

    /**
     * Open this menu for the specified player and register it as open
     *
     * @param player the player to open for
     */
    public void openFor(Player player) {
        if (inventory == null)
            build();

        registerOpenMenu(player, this);
        player.openInventory(inventory);
    }

    /**
     * Register a menu as currently open for a player
     *
     * @param player the player
     * @param menu   the open menu instance
     */
    public static void registerOpenMenu(Player player, MenuBuilder menu) {
        openMenus.put(player.getUniqueId(), menu);
    }

    /**
     * Get the currently registered open menu for a player
     *
     * @param player the player
     * @return the menu, or null if none
     */
    public static MenuBuilder getMenu(Player player) {
        return openMenus.get(player.getUniqueId());
    }

    /**
     * Unregister the open menu for a player, if present
     *
     * @param player the player
     */
    public static void unregisterMenu(Player player) {
        openMenus.remove(player.getUniqueId());
    }

    /**
     * Get the built inventory instance
     *
     * @return the inventory or null
     */
    public Inventory getInventory() {
        return inventory;
    }

    // Get current title
    /**
     * Get the current raw title (before placeholder/color processing)
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    // Get current size
    /**
     * Get the current inventory size
     *
     * @return size
     */
    public int getSize() {
        return size;
    }
}