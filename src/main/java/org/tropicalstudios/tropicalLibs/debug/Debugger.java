package org.tropicalstudios.tropicalLibs.debug;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.tropicalstudios.tropicalLibs.Messenger;
import org.tropicalstudios.tropicalLibs.TropicalLibs;
import org.tropicalstudios.tropicalLibs.utils.PluginUtil;
import org.tropicalstudios.tropicalLibs.utils.FileUtil;
import org.tropicalstudios.tropicalLibs.utils.TimeUtil;

import java.io.File;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Debugger {

    /**
     * Time how long it takes for code to execute
     *
     * @param runnable      The code that should be timed
     */
    public static long time(Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();
        return System.currentTimeMillis() - start;
    }

    /**
     * Save a formatted error report to the plugin's error.log and warn in console
     *
     * The report includes a timestamp, calling plugin name and version, server/Java info,
     * a list of installed plugins, any provided context messages, and a trimmed stack trace
     * including nested causes. The file is stored as "error.log" in the caller plugin's
     * data folder. If saving fails, a secondary warning is logged
     *
     * @param throwable the exception or error to record (may be null)
     * @param messages  optional additional context lines to append to the report
     */
    public static void saveError(Throwable throwable, String... messages) {
        final String systemInfo = "Server Running: " + Bukkit.getName() + " " + Bukkit.getBukkitVersion() + " Java " + System.getProperty("java.version");

        try {
            final List<String> lines = new ArrayList<>();
            final String header = PluginUtil.getPluginName() + " " + PluginUtil.getVersion() + " has encountered " + throwable.getClass().getSimpleName();

            // Write out header and server info
            fill(lines,
                    "------------------------------------[ " + TimeUtil.getFormattedDate(ZoneId.systemDefault().toString()) + " ]-----------------------------------",
                    header,
                    systemInfo,
                    "Plugins: " + Arrays.stream(Bukkit.getPluginManager().getPlugins())
                            .map(Plugin::getName)
                            .collect(Collectors.joining(", ", "", ","))
,
                    "--------------------------------------------------------------------------------------------------");

            // Write additional data
            if (messages != null && !String.join("", messages).isEmpty()) {
                fill(lines, "\nMore Information: ");
                fill(lines, messages);
            }

            { // Write the stack trace

                do {
                    // Write the error header
                    fill(lines, throwable == null ? "Unknown error" : throwable.getClass().getSimpleName());

                    int count = 0;
                    for (final StackTraceElement el : throwable.getStackTrace()) {
                        count++;

                        final String trace = el.toString();
                        if (trace.contains("sun.reflect"))
                            continue;

                        if (count > 6 && trace.startsWith("net.minecraft.server"))
                            break;

                        fill(lines, "\t at " + el);
                    }
                } while ((throwable = throwable.getCause()) != null);
            }

            fill(lines, "--------------------------------------------------------------------------------------------------", System.lineSeparator());

            // Log to the console
            Messenger.log(Messenger.LogLevel.WARN, "Please check your error.log and report this issue with the information in that file");

            // Finally, save the error file
            Plugin caller = TropicalLibs.getINSTANCE();
            File file = FileUtil.getFile(caller, "error.log");
            FileUtil.appendLines(file, lines);

        } catch (final Throwable secondError) {
            Messenger.log(Messenger.LogLevel.WARN, "Got error when saving another error!");
        }
    }

    private static void fill(List<String> list, String... messages) {
        list.addAll(Arrays.asList(messages));
    }
}
