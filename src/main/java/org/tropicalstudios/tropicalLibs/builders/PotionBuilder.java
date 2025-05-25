package org.tropicalstudios.tropicalLibs.builders;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionBuilder {

    private final PotionEffectType effectType;
    private int duration;
    private int amplifier;
    private boolean ambient;
    private boolean particles;
    private boolean icon;

    public PotionBuilder(PotionEffectType effectType) {
        this.effectType = effectType;
        this.duration = 600;
        this.amplifier = 0;
        this.ambient = false;
        this.particles = true;
        this.icon = true;
    }

    public PotionBuilder duration(int duration) {
        this.duration = duration;
        return this;
    }

    public PotionBuilder durationSeconds(int seconds) {
        this.duration = seconds == -1 ? -1 : seconds * 20;
        return this;
    }

    public PotionBuilder durationMinutes(int minutes) {
        this.duration = minutes * 60 * 20;
        return this;
    }

    public PotionBuilder infinite() {
        this.duration = -1;
        return this;
    }

    public PotionBuilder level(int level) {
        this.amplifier = Math.max(0, level - 1);
        return this;
    }

    public PotionBuilder ambient(boolean ambient) {
        this.ambient = ambient;
        return this;
    }

    public PotionBuilder ambient() {
        this.ambient = true;
        return this;
    }

    public PotionBuilder particles(boolean particles) {
        this.particles = particles;
        return this;
    }

    public PotionBuilder showParticles() {
        this.particles = true;
        return this;
    }

    public PotionBuilder hideParticles() {
        this.particles = false;
        return this;
    }

    public PotionBuilder icon(boolean icon) {
        this.icon = icon;
        return this;
    }

    public PotionBuilder showIcon() {
        this.icon = true;
        return this;
    }

    public PotionBuilder hideIcon() {
        this.icon = false;
        return this;
    }

    public PotionBuilder silent() {
        this.particles = false;
        this.icon = false;
        return this;
    }

    public PotionBuilder visible() {
        this.particles = true;
        this.icon = true;
        return this;
    }

    public PotionEffect build() {
        return new PotionEffect(effectType, duration, amplifier, ambient, particles, icon);
    }
}
