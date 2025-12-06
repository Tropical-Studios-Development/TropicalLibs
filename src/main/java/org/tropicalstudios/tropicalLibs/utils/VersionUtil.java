package org.tropicalstudios.tropicalLibs.utils;

public class VersionUtil {

    private static String serverVersion;
    private static V current;
    private static int subversion;

    // Auto-detect version on class load
    static {
        try {
            detect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Auto-detect Bukkit version and parse it
    private static void detect() throws Exception {
        // Example: "1.20.4-R0.1-SNAPSHOT"
        String bukkitVersion = org.bukkit.Bukkit.getBukkitVersion();

        String clean = bukkitVersion.split("-")[0].trim(); // → "1.20.4"
        serverVersion = clean;

        String[] parts = clean.split("\\.");

        if (parts.length < 2)
            throw new IllegalArgumentException("Invalid version: " + clean);

        int minor = Integer.parseInt(parts[1]);
        subversion = parts.length >= 3 ? Integer.parseInt(parts[2]) : 0;

        current = V.parse(minor);
    }

    // ---------------------------------------------------------------------
    // Getters
    // ---------------------------------------------------------------------

    public static V getVersion() {
        return current;
    }

    public static String getFullVersion() {
        return serverVersion;
    }

    // ---------------------------------------------------------------------
    // Comparison Helper Methods
    // ---------------------------------------------------------------------

    /**
     * Check if the current Minecraft version equals the given version
     *
     * @param version The version to compare to
     */
    public static boolean equals(V version) {
        return current == version;
    }

    /**
     * Check if the current Minecraft version is older than the given version
     *
     * @param other The version to compare to
     */
    public static boolean olderThan(V other) {
        return current.getMinor() < other.getMinor();
    }

    /**
     * Check if the current Minecraft version is newer than the given version
     *
     * @param other The version to compare to
     */
    public static boolean newerThan(V other) {
        return current.getMinor() > other.getMinor();
    }

    /**
     * Check if the current Minecraft version equals or is newer than the given version
     *
     * @param other The version to compare to
     */
    public static boolean atLeast(V other) {
        return current.getMinor() >= other.getMinor();
    }

    // ---------------------------------------------------------------------
    // Feature Support Helper
    // ---------------------------------------------------------------------

    /**
     * Check if the current server version supports a specific feature
     *
     * @param feature The feature to test
     */
    public static boolean supportsFeature(Feature feature) {
        return atLeast(feature.getSince());
    }

    // ---------------------------------------------------------------------
    // Version Enum
    // ---------------------------------------------------------------------

    public enum V {
        v1_22(22),
        v1_21(21),
        v1_20(20),
        v1_19(19),
        v1_18(18),
        v1_17(17),
        v1_16(16),
        v1_15(15),
        v1_14(14),
        v1_13(13),
        v1_12(12),
        v1_11(11),
        v1_10(10),
        v1_9(9),
        v1_8(8),
        v1_7(7);

        private final int minor;

        V(int minor) {
            this.minor = minor;
        }

        public int getMinor() {
            return minor;
        }

        public static V parse(int number) throws Exception {
            for (V v : values()) {
                if (v.minor == number)
                    return v;
            }
            throw new Exception("Unsupported MC version: 1." + number);
        }

        @Override
        public String toString() {
            return "1." + minor;
        }
    }

    // ---------------------------------------------------------------------
    // Feature Enum
    // ---------------------------------------------------------------------

    public enum Feature {
        ACTION_BAR(V.v1_8),
        TITLE(V.v1_8),
        SUBTITLE(V.v1_8),
        BOSSBAR_API(V.v1_9),
        CLICKABLE_MESSAGES(V.v1_7),
        HEX(V.v1_16);

        private final V since;

        Feature(V since) {
            this.since = since;
        }

        public V getSince() {
            return since;
        }
    }
}
