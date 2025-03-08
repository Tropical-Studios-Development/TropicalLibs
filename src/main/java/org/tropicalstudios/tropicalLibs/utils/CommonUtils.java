package org.tropicalstudios.tropicalLibs.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.tropicalstudios.tropicalLibs.toasts.AdvancementAccessor;
import org.tropicalstudios.tropicalLibs.toasts.ToastStyle;

import java.util.HashMap;
import java.util.Map;

public class CommonUtils {

    public static void sendToast(Player receiver, String message) {
        sendToast(receiver, message, Material.BOOK, ToastStyle.TASK);
    }

    public static void sendToast(Player receiver, String message, ToastStyle toastStyle) {
        sendToast(receiver, message, Material.BOOK, toastStyle);
    }

    public static void sendToast(Player receiver, String message, Material icon) {
        sendToast(receiver, message, icon, ToastStyle.TASK);
    }

    public static void sendToast(Player player, String message, Material icon, ToastStyle style) {
        (new AdvancementAccessor(message, icon.toString().toLowerCase(), style)).show(player);
    }


    // Execute console command
    public static void executeConsoleCommand(String command) {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        Bukkit.dispatchCommand(console, command);
    }

    // Check if a string is a number
    public static boolean isNumber(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    // Check if the material is an ore
    public static boolean isOre(Material material) {
        return material.name().endsWith("_ORE") || material == Material.NETHER_QUARTZ_ORE;
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

    // Check if an item is a sword
    public static boolean isSword(ItemStack item) {
        return item.getType() == Material.WOODEN_SWORD || item.getType() == Material.STONE_SWORD ||
                item.getType() == Material.IRON_SWORD || item.getType() == Material.GOLDEN_SWORD ||
                item.getType() == Material.DIAMOND_SWORD || item.getType() == Material.NETHERITE_SWORD;
    }

    // Check if an item is armor
    public static boolean isArmor(Material material) {
        return material.name().endsWith("_HELMET")
                || material.name().endsWith("_CHESTPLATE")
                || material.name().endsWith("_LEGGINGS")
                || material.name().endsWith("_BOOTS");
    }

    // Convert normal numbers to roman numbers
    public static String intToRoman(int num) {
        StringBuilder roman = new StringBuilder();
        int i = 0;
        while (num > 0) {
            int k = num / VALUES[i];
            for (int j = 0; j < k; j++) {
                roman.append(SYMBOLS[i]);
                num -= VALUES[i];
            }
            i++;
        }
        return roman.toString();
    }

    // Convert roman numbers to normal numbers
    public static int romanToInt(String roman) {
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);

        int result = 0;
        for (int i = 0; i < roman.length(); i++) {
            int current = romanMap.get(roman.charAt(i));
            if (i < roman.length() - 1 && current < romanMap.get(roman.charAt(i + 1))) {
                result -= current;
            } else {
                result += current;
            }
        }
        return result;
    }

    private static final int[] VALUES = {
            1000, 900, 500, 400,
            100, 90, 50, 40,
            10, 9, 5, 4,
            1
    };
    private static final String[] SYMBOLS = {
            "M", "CM", "D", "CD",
            "C", "XC", "L", "XL",
            "X", "IX", "V", "IV",
            "I"
    };
}
