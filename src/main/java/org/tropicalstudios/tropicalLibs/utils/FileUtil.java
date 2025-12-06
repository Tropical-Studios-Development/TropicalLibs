package org.tropicalstudios.tropicalLibs.utils;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileUtil {

    /**
     * Returns a file inside the plugin's data folder
     */
    public static File getFile(Plugin plugin, String name) {
        return new File(plugin.getDataFolder(), name);
    }

    /**
     * Creates the file if missing, along with parent directories
     *
     * @param file  The file that should be created
     */
    private static void createIfMissing(File file) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        if (!file.exists())
            file.createNewFile();
    }

    /**
     * Appends multiple lines to a file
     *
     * @param file  To what file should the lines pe appended to
     * @param lines The text that should pe appended
     */
    public static void appendLines(File file, List<String> lines) throws IOException {
        createIfMissing(file);
        try (FileWriter fw = new FileWriter(file, true)) {
            for (String line : lines) {
                fw.write(line);
                fw.write(System.lineSeparator());
            }
        }
    }

    /**
     * Overwrites a file completely
     *
     * @param file  The file where the lines should be written
     * @param lines What should be written in the file
     */
    public static void writeLines(File file, List<String> lines) throws IOException {
        createIfMissing(file);
        try (FileWriter fw = new FileWriter(file, false)) {
            for (String line : lines) {
                fw.write(line);
                fw.write(System.lineSeparator());
            }
        }
    }
}
