package org.tropicalstudios.tropicalLibs.builders;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Fluent builder for creating PotionEffect instances.
 *
 * Provides convenient methods for setting duration (in ticks, seconds, minutes or infinite),
 * amplifier level (via human-friendly level), ambient flag, particle visibility, and icon
 * visibility, then building a PotionEffect.
 */
public class PotionBuilder {

    private final PotionEffectType effectType;
    private int duration;
    private int amplifier;
    private boolean ambient;
    private boolean particles;
    private boolean icon;

    /**
     * Create a builder for the specified effect type
     * Defaults: duration 600 ticks (30s), amplifier 0 (level 1), ambient false, particles true, icon true.
     *
     * @param effectType the potion effect type
     */
    public PotionBuilder(PotionEffectType effectType) {
        this.effectType = effectType;
        this.duration = 600;
        this.amplifier = 0;
        this.ambient = false;
        this.particles = true;
        this.icon = true;
    }

    /**
     * Set duration in ticks. Use -1 for infinite
     *
     * @param duration ticks (-1 for infinite)
     */
    public PotionBuilder duration(int duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Set duration in seconds. Use -1 for infinite
     *
     * @param seconds seconds (-1 for infinite)
     */
    public PotionBuilder durationSeconds(int seconds) {
        this.duration = seconds == -1 ? -1 : seconds * 20;
        return this;
    }

    /**
     * Set duration in minutes
     *
     * @param minutes minutes
     */
    public PotionBuilder durationMinutes(int minutes) {
        this.duration = minutes * 60 * 20;
        return this;
    }

    /**
     * Make the effect duration infinite (-1 ticks)
     */
    public PotionBuilder infinite() {
        this.duration = -1;
        return this;
    }

    /**
     * Set the human-friendly level (1 => amplifier 0, 2 => amplifier 1, etc.)
     *
     * @param level human level (>= 1)
     */
    public PotionBuilder level(int level) {
        this.amplifier = Math.max(0, level - 1);
        return this;
    }

    /**
     * Set whether the effect is ambient
     *
     * @param ambient true for ambient
     */
    public PotionBuilder ambient(boolean ambient) {
        this.ambient = ambient;
        return this;
    }

    /**
     * Mark this effect as ambient
     */
    public PotionBuilder ambient() {
        this.ambient = true;
        return this;
    }

    /**
     * Set whether particles are shown
     *
     * @param particles true to show particles
     */
    public PotionBuilder particles(boolean particles) {
        this.particles = particles;
        return this;
    }

    /**
     * Show particles
     */
    public PotionBuilder showParticles() {
        this.particles = true;
        return this;
    }

    /**
     * Hide particles
     */
    public PotionBuilder hideParticles() {
        this.particles = false;
        return this;
    }

    /**
     * Set whether the icon is shown
     *
     * @param icon true to show icon
     */
    public PotionBuilder icon(boolean icon) {
        this.icon = icon;
        return this;
    }

    /**
     * Show the icon
     */
    public PotionBuilder showIcon() {
        this.icon = true;
        return this;
    }

    /**
     * Hide the icon
     */
    public PotionBuilder hideIcon() {
        this.icon = false;
        return this;
    }

    /**
     * Hide both particles and icon
     */
    public PotionBuilder silent() {
        this.particles = false;
        this.icon = false;
        return this;
    }

    /**
     * Show both particles and icon
     */
    public PotionBuilder visible() {
        this.particles = true;
        this.icon = true;
        return this;
    }

    /**
     * Build the configured PotionEffect
     *
     * @return a new PotionEffect instance
     */
    public PotionEffect build() {
        return new PotionEffect(effectType, duration, amplifier, ambient, particles, icon);
    }
}
