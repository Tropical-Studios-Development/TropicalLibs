package org.tropicalstudios.tropicalLibs.utils;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadableNBT;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.CompletableFuture;

public class NBTUtil {

    // Set a string nbt to an item
    public static CompletableFuture<Void> setString(ItemStack item, String tag, String value) {
        return CompletableFuture.runAsync(() -> {
            NBT.modify(item, nbt -> {
                nbt.setString(tag, value);
            });
        });
    }

    // Set a string nbt to an entity
    public static CompletableFuture<Void> setString(Entity entity, String tag, String value) {
        return CompletableFuture.runAsync(() -> {
            NBT.modify(entity, nbt -> {
                nbt.setString(tag, value);
            });
        });
    }

    // Set a boolean nbt to an item
    public static CompletableFuture<Void> setBool(ItemStack item, String tag, boolean value) {
        return CompletableFuture.runAsync(() -> {
            NBT.modify(item, nbt -> {
                nbt.setBoolean(tag, value);
            });
        });
    }

    // Set a boolean nbt to an entity
    public static CompletableFuture<Void> setBool(Entity entity, String tag, boolean value) {
        return CompletableFuture.runAsync(() -> {
            NBT.modifyPersistentData(entity, nbt -> {
                nbt.setBoolean(tag, value);
            });
        });
    }

    // Set an integer nbt to an item
    public static CompletableFuture<Void> setInt(ItemStack item, String tag, int value) {
        return CompletableFuture.runAsync(() -> {
            NBT.modify(item, nbt -> {
                nbt.setInteger(tag, value);
            });
        });
    }

    // Set an integer nbt to an entity
    public static CompletableFuture<Void> setInt(Entity entity, String tag, int value) {
        return CompletableFuture.runAsync(() -> {
            NBT.modifyPersistentData(entity, nbt -> {
                nbt.setInteger(tag, value);
            });
        });
    }

    // Get a string nbt from an item
    public static CompletableFuture<String> getString(ItemStack item, String tag) {
        return CompletableFuture.supplyAsync(() -> NBT.get(item, nbt -> (String) nbt.getString(tag)));
    }

    // Get a string nbt from an entity
    public static CompletableFuture<String> getString(Entity entity, String tag){
        return CompletableFuture.supplyAsync(() -> NBT.getPersistentData(entity, nbt -> nbt.getString(tag)));
    }

    // Get a boolean nbt from an item
    public static CompletableFuture<Boolean> getBool(ItemStack item, String tag) {
        return CompletableFuture.supplyAsync(() -> NBT.get(item, nbt -> (boolean) nbt.getBoolean(tag)));
    }

    // Get a boolean nbt from an entity
    public static CompletableFuture<Boolean> getBool(Entity entity, String tag) {
        return CompletableFuture.supplyAsync(() -> NBT.getPersistentData(entity, nbt -> nbt.getBoolean(tag)));
    }

    // Get an integer nbt from an item
    public static CompletableFuture<Integer> getInt(ItemStack item, String tag) {
        return CompletableFuture.supplyAsync(() -> NBT.get(item, nbt -> (int) nbt.getInteger(tag)));
    }

    // Get an integer nbt from an entity
    public static CompletableFuture<Integer> getInt(Entity entity, String tag) {
        return CompletableFuture.supplyAsync(() -> NBT.getPersistentData(entity, nbt -> nbt.getInteger(tag)));
    }

    // Check if an item has a tag
    public static CompletableFuture<Boolean> hasTag(ItemStack item, String tag) {
        return CompletableFuture.supplyAsync(() -> NBT.get(item, nbt -> (boolean) nbt.hasTag(tag)));
    }

    // Check if an entity has a tag
    public static CompletableFuture<Boolean> hasTag(Entity entity, String tag) {
        return CompletableFuture.supplyAsync(() -> NBT.getPersistentData(entity, nbt -> nbt.hasTag(tag)));
    }

    // Get the full compound of an item
    public static CompletableFuture<String> getCompound(ItemStack item) {
        return CompletableFuture.supplyAsync(() -> NBT.itemStackToNBT(item).toString());
    }

    // Get the full compound of an entity
    public static CompletableFuture<String> getCompound(Entity entity) {
        return CompletableFuture.supplyAsync(() -> NBT.getPersistentData(entity, ReadableNBT::toString));
    }
}