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

public class AdvancementAccessor {
    private final NamespacedKey key = new NamespacedKey(TropicalLibs.getPlugin(), UUID.randomUUID().toString());
    private final String icon;
    private final String message;
    private final ToastStyle toastStyle;

    public AdvancementAccessor(String message, String icon, ToastStyle toastStyle) {
        this.message = message;
        this.icon = icon;
        this.toastStyle = toastStyle;
    }

    public void show(Player player) {
        this.loadAdvancement();
        this.grantAdvancement(player);
        TropicalScheduler.sync().runLater(() -> {
            this.revokeAdvancement(player);
            this.removeAdvancement();
        }, 10);
    }

    private void loadAdvancement() {
        Bukkit.getUnsafe().loadAdvancement(this.key, this.compileJson0());
    }

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

    private void grantAdvancement(Player player) {
        Advancement adv = this.getAdvancement();
        if (adv != null) {
            AdvancementProgress progress = player.getAdvancementProgress(adv);
            if (!progress.isDone()) {
                progress.getRemainingCriteria().forEach(progress::awardCriteria);
            }
        }
    }

    private void revokeAdvancement(Player player) {
        Advancement adv = this.getAdvancement();
        if (adv != null) {
            AdvancementProgress prog = player.getAdvancementProgress(adv);
            if (prog.isDone()) {
                prog.getAwardedCriteria().forEach(prog::revokeCriteria);
            }
        }
    }

    private void removeAdvancement() {
        Bukkit.getUnsafe().removeAdvancement(this.key);
    }

    private Advancement getAdvancement() {
        return Bukkit.getAdvancement(this.key);
    }
}