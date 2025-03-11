package org.tropicalstudios.tropicalLibs.schedulers.impl;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.tropicalstudios.tropicalLibs.TropicalLibs;
import org.tropicalstudios.tropicalLibs.schedulers.TaskHandle;
import org.tropicalstudios.tropicalLibs.schedulers.TropicalScheduler;

public class SyncScheduler implements TropicalScheduler {

    @Override
    public void run(Runnable r) {
        Bukkit.getScheduler().runTask(TropicalLibs.getINSTANCE(), r);
    }

    @Override
    public TaskHandle runLater(Runnable r, long l) {
        BukkitTask task = Bukkit.getScheduler().runTaskLater(TropicalLibs.getINSTANCE(), r, l);
        return task::cancel;
    }

    @Override
    public TaskHandle runRepeating(Runnable r, long d, long l) {
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(TropicalLibs.getINSTANCE() ,r, d, l);
        return task::cancel;
    }
}
