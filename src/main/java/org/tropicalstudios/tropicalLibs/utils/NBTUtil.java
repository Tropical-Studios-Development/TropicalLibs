package org.tropicalstudios.tropicalLibs.utils;

import de.tr7zw.changeme.nbtapi.NBT;
import org.bukkit.inventory.ItemStack;

public class NBTUtil {

    // Set a string
    public static void setString(ItemStack item, String tag, String value) {
        NBT.modify(item, nbt -> {
           nbt.setString(tag, value);
        });
    }

    // Set a boolean
    public static void setBool(ItemStack item, String tag, boolean value) {
        NBT.modify(item, nbt -> {
           nbt.setBoolean(tag, value);
        });
    }

    // Set an integer
    public static void setInt(ItemStack item, String tag, int value) {
        NBT.modify(item, nbt -> {
           nbt.setInteger(tag, value);
        });
    }

    // Get a string
    public static String getString(ItemStack item, String tag) {
        return NBT.get(item, nbt -> (String) nbt.getString(tag));
    }

    // Get a boolean
    public static boolean getBool(ItemStack item, String tag) {
        return NBT.get(item, nbt -> (boolean) nbt.getBoolean(tag));
    }

    // Get an integer
    public static int getInt(ItemStack item, String tag) {
        return NBT.get(item, nbt -> (int) nbt.getInteger(tag));
    }

    // Check if the item has a tag
    public static boolean hasTag(ItemStack item, String tag) {
        return NBT.get(item, nbt -> (boolean) nbt.hasTag(tag));
    }

    // Get the full compound of the item
    public static String getCompound(ItemStack item) {
        return NBT.itemStackToNBT(item).toString();
    }
}
