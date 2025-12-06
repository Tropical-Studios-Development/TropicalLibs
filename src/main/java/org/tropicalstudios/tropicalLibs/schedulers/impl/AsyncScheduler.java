package org.tropicalstudios.tropicalLibs.schedulers.impl;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.tropicalstudios.tropicalLibs.TropicalLibs;
import org.tropicalstudios.tropicalLibs.schedulers.TaskHandle;
import org.tropicalstudios.tropicalLibs.schedulers.TropicalScheduler;

public class AsyncScheduler implements TropicalScheduler {

    /**
     * Run a task asynchronously on the server thread pool
     *
     * @param r the runnable to execute off the main thread
     */
    @Override
    public void run(Runnable r) {
        BukkitTask task = Bukkit.getScheduler().runTaskAsynchronously(TropicalLibs.getINSTANCE(), r);
    }

    /**
     * Schedule a one-shot asynchronous task to run after a delay
     *
     * @param r the runnable to execute off the main thread
     * @param l the delay in ticks before running
     * @return a handle that can be used to cancel the scheduled task
     */
    @Override
    public TaskHandle runLater(Runnable r, long l) {
        BukkitTask task = Bukkit.getScheduler().runTaskLaterAsynchronously(TropicalLibs.getINSTANCE(), r, l);
        return task::cancel;
    }

    /**
     * Schedule a repeating asynchronous task
     *
     * @param r the runnable to execute off the main thread
     * @param d the initial delay in ticks before first run
     * @param l the period in ticks between subsequent runs
     * @return a handle that can be used to cancel the repeating task
     */
    @Override
    public TaskHandle runRepeating(Runnable r, long d, long l) {
        BukkitTask task = Bukkit.getScheduler().runTaskTimerAsynchronously(TropicalLibs.getINSTANCE(), r, d, l);
        return task::cancel;
    }
}
