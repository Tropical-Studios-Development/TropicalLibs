package org.tropicalstudios.tropicalLibs.toasts;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.tropicalstudios.tropicalLibs.TropicalLibs;
import org.tropicalstudios.tropicalLibs.schedulers.TropicalScheduler;

import java.util.UUID;

/**
 * Helper for showing client-side advancement toasts
 *
 * Creates a temporary advancement with the desired icon, title, and frame
 * style, grants it to the target player to trigger the toast, and then quickly
 * revokes and removes the advancement again
 */
public class AdvancementAccessor {
    private final NamespacedKey key = new NamespacedKey(TropicalLibs.getINSTANCE(), UUID.randomUUID().toString());
    private final String icon;
    private final String message;
    private final ToastStyle toastStyle;

    /**
     * Create a new accessor for a one-shot toast
     *
     * @param message    the text shown as the toast title
     * @param icon       the Minecraft item id to use as the icon (without the "minecraft:" prefix),
     *                   e.g. "book"; value is lowercased internally and prefixed with "minecraft:"
     * @param toastStyle the visual frame style of the toast
     */
    public AdvancementAccessor(String message, String icon, ToastStyle toastStyle) {
        this.message = message;
        this.icon = icon;
        this.toastStyle = toastStyle;
    }

    /**
     * Display the configured toast to a player
     *
     * Loads the temporary advancement, grants it to trigger the toast, and
     * schedules a revoke and removal after a short delay (10 ticks)
     *
     * @param player the player who should see the toast
     */
    public void show(Player player) {
        this.loadAdvancement();
        this.grantAdvancement(player);
        TropicalScheduler.sync().runLater(() -> {
            this.revokeAdvancement(player);
            this.removeAdvancement();
        }, 10);
    }

    /**
     * Register the temporary advancement with Bukkit using the compiled JSON
     */
    private void loadAdvancement() {
        Bukkit.getUnsafe().loadAdvancement(this.key, this.compileJson0());
    }

    /**
     * Build the advancement JSON for a hidden one-criterion advancement that
     * always remains unannounced and only shows a toast
     *
     * @return the serialized JSON string representing the advancement
     */
    private String compileJson0() {
        JsonObject json = new JsonObject();

        JsonObject icon = new JsonObject();
        icon.addProperty("id", "minecraft:" + this.icon.toLowerCase());  // Added minecraft: prefix and lowercase

        JsonObject display = new JsonObject();
        display.add("icon", icon);
        display.addProperty("title", this.message);
        display.addProperty("description", "");
        display.addProperty("background", "minecraft:textures/gui/advancements/backgrounds/adventure.png");
        display.addProperty("frame", this.toastStyle.getKey());
        display.addProperty("announce_to_chat", false);
        display.addProperty("show_toast", true);
        display.addProperty("hidden", true);

        // Add criteria (required for advancement to work)
        JsonObject criteria = new JsonObject();
        JsonObject trigger = new JsonObject();
        trigger.addProperty("trigger", "minecraft:impossible");
        criteria.add("impossible", trigger);

        json.add("criteria", criteria);
        json.add("display", display);

        return new Gson().toJson(json);
    }

    /**
     * Grant the temporary advancement to the player if not already completed
     *
     * @param player the player to grant criteria to
     */
    private void grantAdvancement(Player player) {
        Advancement adv = this.getAdvancement();
        if (adv != null) {
            AdvancementProgress progress = player.getAdvancementProgress(adv);
            if (!progress.isDone()) {
                progress.getRemainingCriteria().forEach(progress::awardCriteria);
            }
        }
    }

    /**
     * Revoke all awarded criteria of the temporary advancement from the player
     * so it can be discarded cleanly
     *
     * @param player the player to revoke criteria from
     */
    private void revokeAdvancement(Player player) {
        Advancement adv = this.getAdvancement();
        if (adv != null) {
            AdvancementProgress prog = player.getAdvancementProgress(adv);
            if (prog.isDone()) {
                prog.getAwardedCriteria().forEach(prog::revokeCriteria);
            }
        }
    }

    /**
     * Unregister the temporary advancement from Bukkit
     */
    private void removeAdvancement() {
        Bukkit.getUnsafe().removeAdvancement(this.key);
    }

    /**
     * Resolve the Bukkit {@link Advancement} instance for this accessor's key
     *
     * @return the advancement if present, otherwise null
     */
    private Advancement getAdvancement() {
        return Bukkit.getAdvancement(this.key);
    }
}