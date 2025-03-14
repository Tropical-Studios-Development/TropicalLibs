package org.tropicalstudios.tropicalLibs.utils;

import de.tr7zw.changeme.nbtapi.NBT;
import org.bukkit.inventory.ItemStack;

public class NBTUtil {

    public static void setString(ItemStack item, String tag, String value) {
        NBT.modify(item, nbt -> {
           nbt.setString(tag, value);
        });
    }

    public static void setBool(ItemStack item, String tag, boolean value) {
        NBT.modify(item, nbt -> {
           nbt.setBoolean(tag, value);
        });
    }

    public static void setInt(ItemStack item, String tag, int value) {
        NBT.modify(item, nbt -> {
           nbt.setInteger(tag, value);
        });
    }

    public static String getString(ItemStack item, String tag) {
        return NBT.get(item, nbt -> (String) nbt.getString(tag));
    }

    public static boolean getBool(ItemStack item, String tag) {
        return NBT.get(item, nbt -> (boolean) nbt.getBoolean(tag));
    }

    public static int getInt(ItemStack item, String tag) {
        return NBT.get(item, nbt -> (int) nbt.getInteger(tag));
    }

    public static boolean hasTag(ItemStack item, String tag) {
        return NBT.get(item, nbt -> (boolean) nbt.hasTag(tag));
    }

    public static String getCompound(ItemStack item) {
        return NBT.itemStackToNBT(item).toString();
    }
}
