package org.tropicalstudios.tropicalLibs.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.tropicalstudios.tropicalLibs.Messenger;
import org.tropicalstudios.tropicalLibs.builders.MenuBuilder;
import org.tropicalstudios.tropicalLibs.utils.ChatUtil;
import org.tropicalstudios.tropicalLibs.utils.ItemUtil;
import org.tropicalstudios.tropicalLibs.utils.NBTUtil;

public abstract class MenuClick implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player player))
            return;

        MenuBuilder menu = MenuBuilder.getMenu(player);
        if (menu == null)
            return;

        if (!event.getInventory().equals(menu.getInventory()))
            return;

        event.setCancelled(true);
        ItemStack clickedItem = event.getCurrentItem();
        if (ItemUtil.isNull(clickedItem))
            return;

        String target = NBTUtil.getString(clickedItem, "target").join();
        String action = NBTUtil.getString(clickedItem, "menu-action").join();

        if (action == null || action.isEmpty())
            return;

        if (action.equalsIgnoreCase("[close]")) {
            player.closeInventory();
        }

        if (action.startsWith("[player]")) {
            String command = action.substring(9).trim();
            ChatUtil.executePlayerCommand(player, command.replace("{player}", target));
        }

        if (action.startsWith("[console]")) {
            String command = action.substring(9).trim();
            ChatUtil.executeConsoleCommand(command.replace("{player}", target));
        }

        if (action.startsWith("[broadcast]")) {
            String message = action.substring(11).trim();
            Messenger.broadcast(message);
        }

        if (action.startsWith("[message]")) {
            String message = action.substring(9).trim();
            player.sendMessage(ChatUtil.c(message));
        }

        handleAction(event, player, target, action);
    }

    protected abstract void handleAction(InventoryClickEvent event, Player player, String target, String action);
}
