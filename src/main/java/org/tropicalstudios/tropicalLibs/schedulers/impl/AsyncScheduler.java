package org.tropicalstudios.tropicalLibs.schedulers.impl;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.tropicalstudios.tropicalLibs.TropicalLibs;
import org.tropicalstudios.tropicalLibs.schedulers.TaskHandle;
import org.tropicalstudios.tropicalLibs.schedulers.TropicalScheduler;

import java.util.concurrent.CompletableFuture;

public class AsyncScheduler implements TropicalScheduler {

    @Override
    public void run(Runnable r) {
        BukkitTask task = Bukkit.getScheduler().runTaskAsynchronously(TropicalLibs.getPlugin(), r);
    }

    @Override
    public TaskHandle runLater(Runnable r, long l) {
        BukkitTask task = Bukkit.getScheduler().runTaskLaterAsynchronously(TropicalLibs.getPlugin(), r, l);
        return task::cancel;
    }

    @Override
    public TaskHandle runRepeating(Runnable r, long d, long l) {
        BukkitTask task = Bukkit.getScheduler().runTaskTimerAsynchronously(TropicalLibs.getPlugin(), r, d, l);
        return task::cancel;
    }
}
