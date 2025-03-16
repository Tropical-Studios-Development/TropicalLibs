package org.tropicalstudios.tropicalLibs.utils;

import de.tr7zw.changeme.nbtapi.NBT;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.CompletableFuture;

public class NBTUtil {

    // Set a string
    public static void setString(ItemStack item, String tag, String value) {
        CompletableFuture.runAsync(() -> NBT.modify(item, nbt -> {
            nbt.setString(tag, value);
        }));
    }

    // Set a boolean
    public static void setBool(ItemStack item, String tag, boolean value) {
        CompletableFuture.runAsync(() -> NBT.modify(item, nbt -> {
            nbt.setBoolean(tag, value);
        }));
    }

    // Set an integer
    public static void setInt(ItemStack item, String tag, int value) {
        CompletableFuture.runAsync(() -> NBT.modify(item, nbt -> {
            nbt.setInteger(tag, value);
        }));
    }

    // Get a string
    public static CompletableFuture<String> getString(ItemStack item, String tag) {
        return CompletableFuture.supplyAsync(() -> NBT.get(item, nbt -> (String) nbt.getString(tag)));
    }

    // Get a boolean
    public static CompletableFuture<Boolean> getBool(ItemStack item, String tag) {
        return CompletableFuture.supplyAsync(() -> NBT.get(item, nbt -> (boolean) nbt.getBoolean(tag)));
    }

    // Get an integer
    public static CompletableFuture<Integer> getInt(ItemStack item, String tag) {
        return CompletableFuture.supplyAsync(() -> NBT.get(item, nbt -> (int) nbt.getInteger(tag)));
    }

    // Check if the item has a tag
    public static CompletableFuture<Boolean> hasTag(ItemStack item, String tag) {
        return CompletableFuture.supplyAsync(() -> NBT.get(item, nbt -> (boolean) nbt.hasTag(tag)));
    }

    // Get the full compound of the item
    public static CompletableFuture<String> getCompound(ItemStack item) {
        return CompletableFuture.supplyAsync(() -> NBT.itemStackToNBT(item).toString());
    }
}
