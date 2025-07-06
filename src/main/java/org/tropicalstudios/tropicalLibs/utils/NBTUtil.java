package org.tropicalstudios.tropicalLibs.utils;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadableNBT;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.CompletableFuture;

public class NBTUtil {

    /**
     * Set a string NBT to an item
     *
     * @param item      The item that the NBT should be applied to
     * @param tag       The string to be added
     * @param value     The value of the string to be added
     */
    public static void setString(ItemStack item, String tag, String value) {
        NBT.modify(item, nbt -> {
            nbt.setString(tag, value);
        });
    }

    /**
     * Set a string NBT to an entity
     *
     * @param entity    The entity that the NBT should be applied to
     * @param tag       The string to be added
     * @param value     The value of the string to be added
     */
    public static void setString(Entity entity, String tag, String value) {
        NBT.modify(entity, nbt -> {
            nbt.setString(tag, value);
        });
    }

    /**
     * Set a boolean NBT to an item
     *
     * @param item      The item that the NBT should be applied to
     * @param tag       The boolean to be added
     * @param value     The value of the boolean to be added (true/false)
     */
    public static void setBool(ItemStack item, String tag, boolean value) {
        NBT.modify(item, nbt -> {
            nbt.setBoolean(tag, value);
        });
    }

    /**
     * Set a boolean NBT to an entity
     *
     * @param entity    The entity that the NBT should be applied to
     * @param tag       The boolean to be added
     * @param value     The value of the boolean to be added (true/false)
     */
    public static void setBool(Entity entity, String tag, boolean value) {
        NBT.modifyPersistentData(entity, nbt -> {
            nbt.setBoolean(tag, value);
        });
    }

    /**
     * Set an integer NBT to an item
     *
     * @param item      The item that the NBT should be applied to
     * @param tag       The integer to be added
     * @param value     The value of the integer to be added
     */
    public static void setInt(ItemStack item, String tag, int value) {
        NBT.modify(item, nbt -> {
            nbt.setInteger(tag, value);
        });
    }

    /**
     * Set an integer NBT to an entity
     *
     * @param entity    The entity that the NBT should be applied to
     * @param tag       The integer to be added
     * @param value     The value of the integer to be added
     */
    public static void setInt(Entity entity, String tag, int value) {
        NBT.modifyPersistentData(entity, nbt -> {
            nbt.setInteger(tag, value);
        });
    }

    /**
     * Set a double NBT to an item
     *
     * @param item      The item that the NBT should be applied to
     * @param tag       The double to be added
     * @param value     The value of the double to be added
     */
    public static void setDouble(ItemStack item, String tag, double value) {
        NBT.modify(item, nbt -> {
            nbt.setDouble(tag, value);
        });
    }

    /**
     * Set a double NBT to an entity
     *
     * @param entity    The entity that the NBT should be applied to
     * @param tag       The double to be added
     * @param value     The value of the double to be added
     */
    public static void setDouble(Entity entity, String tag, double value) {
        NBT.modifyPersistentData(entity, nbt -> {
            nbt.setDouble(tag, value);
        });
    }

    /**
     * Get a string NBT from an item
     *
     * @param item      The item from which the NBT should be retrieved from
     * @param tag       From what tag should the value be retrieved from
     */
    public static CompletableFuture<String> getString(ItemStack item, String tag) {
        return CompletableFuture.supplyAsync(() -> NBT.get(item, nbt -> (String) nbt.getString(tag)));
    }

    /**
     * Get a string NBT from an entity
     *
     * @param entity    The entity from which the NBT should be retrieved from
     * @param tag       From what tag should the value be retrieved from
     */
    public static CompletableFuture<String> getString(Entity entity, String tag){
        return CompletableFuture.supplyAsync(() -> NBT.getPersistentData(entity, nbt -> nbt.getString(tag)));
    }

    /**
     * Get a boolean NBT from an item
     *
     * @param item      The item from which the NBT should be retrieved from
     * @param tag       From what tag should the value be retrieved from
     */
    public static CompletableFuture<Boolean> getBool(ItemStack item, String tag) {
        return CompletableFuture.supplyAsync(() -> NBT.get(item, nbt -> (boolean) nbt.getBoolean(tag)));
    }

    /**
     * Get a boolean NBT from an entity
     *
     * @param entity    The entity from which the NBT should be retrieved from
     * @param tag       From what tag should the value be retrieved from
     */
    public static CompletableFuture<Boolean> getBool(Entity entity, String tag) {
        return CompletableFuture.supplyAsync(() -> NBT.getPersistentData(entity, nbt -> nbt.getBoolean(tag)));
    }

    /**
     * Get an integer NBT from an item
     *
     * @param item      The item from which the NBT should be retrieved from
     * @param tag       From what tag should the value be retrieved from
     */
    public static CompletableFuture<Integer> getInt(ItemStack item, String tag) {
        return CompletableFuture.supplyAsync(() -> NBT.get(item, nbt -> (int) nbt.getInteger(tag)));
    }

    /**
     * Get an integer NBT from an entity
     *
     * @param entity    The entity from which the NBT should be retrieved from
     * @param tag       From what tag should the value be retrieved from
     */
    public static CompletableFuture<Integer> getInt(Entity entity, String tag) {
        return CompletableFuture.supplyAsync(() -> NBT.getPersistentData(entity, nbt -> nbt.getInteger(tag)));
    }

    /**
     * Get a double NBT from an item
     *
     * @param item      The item from which the NBT should be retrieved from
     * @param tag       From what tag should the value be retrieved from
     */
    public static CompletableFuture<Double> getDouble(ItemStack item, String tag) {
        return CompletableFuture.supplyAsync(() -> NBT.get(item, nbt -> (double) nbt.getDouble(tag)));
    }

    /**
     * Get a double NBT from an entity
     *
     * @param entity    The entity from which the NBT should be retrieved from
     * @param tag       From what tag should the value be retrieved from
     */
    public static CompletableFuture<Double> getDouble(Entity entity, String tag) {
        return CompletableFuture.supplyAsync(() -> NBT.getPersistentData(entity, nbt -> nbt.getDouble(tag)));
    }

    /**
     * Check if an item has a NBT tag
     *
     * @param item      The item that should be checked
     * @param tag       The tag that should be checked
     */
    public static CompletableFuture<Boolean> hasTag(ItemStack item, String tag) {
        return CompletableFuture.supplyAsync(() -> NBT.get(item, nbt -> (boolean) nbt.hasTag(tag)));
    }

    /**
     * Check if an entity has a NBT tag
     *
     * @param entity    The entity that should be checked
     * @param tag       The tag that should be checked
     */
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
