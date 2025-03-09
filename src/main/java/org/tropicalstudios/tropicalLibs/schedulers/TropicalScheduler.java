package org.tropicalstudios.tropicalLibs.schedulers;

import org.bukkit.plugin.Plugin;
import org.tropicalstudios.tropicalLibs.schedulers.impl.AsyncScheduler;
import org.tropicalstudios.tropicalLibs.schedulers.impl.SyncScheduler;

public interface TropicalScheduler {

    // Run code async
    static TropicalScheduler async() {
        return new AsyncScheduler();
    }

    // Run code sync
    static TropicalScheduler sync() {
        return new SyncScheduler();
    }

    void run(Runnable r, Plugin plugin);

    TaskHandle runLater(Runnable r, Plugin plugin, long l);

    TaskHandle runRepeating(Runnable r, Plugin plugin, long d, long l);
}
