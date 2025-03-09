package org.tropicalstudios.tropicalLibs.schedulers.impl;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.tropicalstudios.tropicalLibs.TropicalLibs;
import org.tropicalstudios.tropicalLibs.schedulers.TaskHandle;
import org.tropicalstudios.tropicalLibs.schedulers.TropicalScheduler;

public class SyncScheduler implements TropicalScheduler {

    @Override
    public void run(Runnable r, Plugin plugin) {
        Bukkit.getScheduler().runTask(plugin, r);
    }

    @Override
    public TaskHandle runLater(Runnable r, Plugin plugin,long l) {
        BukkitTask task = Bukkit.getScheduler().runTaskLater(plugin, r, l);
        return task::cancel;
    }

    @Override
    public TaskHandle runRepeating(Runnable r, Plugin plugin, long d, long l) {
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, r, d, l);
        return task::cancel;
    }
}
