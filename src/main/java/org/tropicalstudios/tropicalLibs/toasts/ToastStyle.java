package org.tropicalstudios.tropicalLibs.toasts;

import java.util.Arrays;

/**
 * Visual frame styles for advancement toast notifications
 *
 * Matches Minecraft's advancement frames: {@code task}, {@code goal}, and {@code challenge}
 * Each style exposes a lowercase string key used by the advancement JSON
 */
public enum ToastStyle {
    TASK("task"),
    GOAL("goal"),
    CHALLENGE("challenge");

    private final String key;

    /**
     * Resolve a toast style from its string key (case-insensitive)
     *
     * @param key the style key (e.g., "task", "goal", or "challenge")
     * @return the matching {@link ToastStyle}
     * @throws IllegalArgumentException if no style matches the provided key
     */
    public static ToastStyle fromKey(String key) {
        for(ToastStyle style : values()) {
            if (style.key.equalsIgnoreCase(key)) {
                return style;
            }
        }

        throw new IllegalArgumentException("No such CompToastStyle '" + key + "'. Available: " + Arrays.asList(values()));
    }

    /**
     * Return the style key
     *
     * @return the lowercase key used in advancement JSON
     */
    public String toString() {
        return this.key;
    }

    /**
     * Create a toast style with the given key
     *
     * @param key the lowercase key used in advancement JSON
     */
    ToastStyle(final String key) {
        this.key = key;
    }

    /**
     * Get the lowercase style key used in advancement JSON
     *
     * @return the style key (e.g., "task")
     */
    public String getKey() {
        return this.key;
    }
}
