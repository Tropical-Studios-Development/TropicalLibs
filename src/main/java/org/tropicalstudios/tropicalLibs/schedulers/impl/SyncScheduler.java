package org.tropicalstudios.tropicalLibs.schedulers.impl;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.tropicalstudios.tropicalLibs.TropicalLibs;
import org.tropicalstudios.tropicalLibs.schedulers.TaskHandle;
import org.tropicalstudios.tropicalLibs.schedulers.TropicalScheduler;

public class SyncScheduler implements TropicalScheduler {

    /**
     * Run a task on the main server thread immediately
     *
     * @param r the runnable to execute on the main thread
     */
    @Override
    public void run(Runnable r) {
        Bukkit.getScheduler().runTask(TropicalLibs.getINSTANCE(), r);
    }

    /**
     * Schedule a one-shot task on the main thread after a delay
     *
     * @param r the runnable to execute on the main thread
     * @param l the delay in ticks before running
     * @return a handle that can be used to cancel the scheduled task
     */
    @Override
    public TaskHandle runLater(Runnable r, long l) {
        BukkitTask task = Bukkit.getScheduler().runTaskLater(TropicalLibs.getINSTANCE(), r, l);
        return task::cancel;
    }

    /**
     * Schedule a repeating task on the main thread
     *
     * @param r the runnable to execute on the main thread
     * @param d the initial delay in ticks before first run
     * @param l the period in ticks between subsequent runs
     * @return a handle that can be used to cancel the repeating task
     */
    @Override
    public TaskHandle runRepeating(Runnable r, long d, long l) {
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(TropicalLibs.getINSTANCE() ,r, d, l);
        return task::cancel;
    }
}
