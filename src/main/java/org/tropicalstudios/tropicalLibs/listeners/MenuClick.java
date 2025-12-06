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

/**
 * Base inventory click listener for menu interactions
 *
 * Handles clicks only for the active {@link org.tropicalstudios.tropicalLibs.builders.MenuBuilder}
 * instance opened by the player. Reads item NBT keys {@code target} and {@code menu-action}
 * to perform built‑in actions and then delegates to {@link #handleAction(InventoryClickEvent, Player, String, String)}
 * for plugin‑specific behavior
 *
 * Supported built‑in actions (case‑insensitive):
 * - {@code [close]} — closes the player's inventory
 * - {@code [player] <command>} — runs a command as the player (replaces {@code {player}} with target)
 * - {@code [console] <command>} — runs a command as console (replaces {@code {player}} with target)
 * - {@code [broadcast] <message>} — broadcasts a message (replaces {@code {player}} with target)
 * - {@code [message] <message>} — sends a private message to the player (replaces {@code {player}} with target)
 */
public abstract class MenuClick implements Listener {

    /**
     * Handle inventory clicks within the player's open menu
     *
     * Safely ignores clicks from non‑players, clicks outside the active menu inventory,
     * and null/air items. Parses the clicked item's NBT to resolve {@code target} and
     * {@code menu-action}, executes any built‑in action, and finally calls
     * {@link #handleAction(InventoryClickEvent, Player, String, String)}.
     *
     * @param event the Bukkit inventory click event
     */
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
            Messenger.broadcast(message.replace("{player}", target));
        }

        if (action.startsWith("[message]")) {
            String message = action.substring(9).trim();
            player.sendMessage(ChatUtil.c(message.replace("{player}", target)));
        }

        handleAction(event, player, target, action);
    }

    /**
     * Implement to handle custom actions not covered by the built‑in set
     *
     * This method is invoked after any recognized built‑in action has been processed.
     * The {@code action} string is the raw value of the {@code menu-action} NBT tag
     * from the clicked item, and {@code target} is the value of the {@code target}
     * NBT tag (may be an empty string)
     *
     * @param event  the original inventory click event (already cancelled)
     * @param player the player who clicked
     * @param target the resolved target string from item NBT
     * @param action the raw action string from item NBT
     */
    protected abstract void handleAction(InventoryClickEvent event, Player player, String target, String action);
}
