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
    public void run(Runnable r, Plugin plugin) {
        BukkitTask task = Bukkit.getScheduler().runTaskAsynchronously(plugin, r);
    }

    @Override
    public TaskHandle runLater(Runnable r, Plugin plugin, long l) {
        BukkitTask task = Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, r, l);
        return task::cancel;
    }

    @Override
    public TaskHandle runRepeating(Runnable r, Plugin plugin,long d, long l) {
        BukkitTask task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, r, d, l);
        return task::cancel;
    }
}
