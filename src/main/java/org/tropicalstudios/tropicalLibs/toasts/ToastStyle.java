package org.tropicalstudios.tropicalLibs.toasts;

import java.util.Arrays;

public enum ToastStyle {
    TASK("task"),
    GOAL("goal"),
    CHALLENGE("challenge");

    private final String key;

    public static ToastStyle fromKey(String key) {
        for(ToastStyle style : values()) {
            if (style.key.equalsIgnoreCase(key)) {
                return style;
            }
        }

        throw new IllegalArgumentException("No such CompToastStyle '" + key + "'. Available: " + Arrays.asList(values()));
    }

    public String toString() {
        return this.key;
    }

    ToastStyle(final String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
