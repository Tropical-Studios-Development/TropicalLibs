package org.tropicalstudios.tropicalLibs.schedulers;

import org.tropicalstudios.tropicalLibs.schedulers.impl.AsyncScheduler;
import org.tropicalstudios.tropicalLibs.schedulers.impl.SyncScheduler;

public interface TropicalScheduler {

    /**
     * Get a scheduler that runs tasks asynchronously
     *
     * @return an implementation that executes tasks off the main server thread
     */
    static TropicalScheduler async() {
        return new AsyncScheduler();
    }

    /**
     * Get a scheduler that runs tasks on the main server thread
     *
     * @return an implementation that executes tasks synchronously on the server thread
     */
    static TropicalScheduler sync() {
        return new SyncScheduler();
    }

    /**
     * Execute a task immediately using this scheduler's execution context
     *
     * @param r the runnable to execute
     */
    void run(Runnable r);

    /**
     * Schedule a one-shot task after a delay using this scheduler's context
     *
     * @param r the runnable to execute
     * @param l the delay in ticks before running
     * @return a handle for cancelling the scheduled task
     */
    TaskHandle runLater(Runnable r, long l);

    /**
     * Schedule a repeating task using this scheduler's context
     *
     * @param r the runnable to execute
     * @param d the initial delay in ticks before first run
     * @param l the period in ticks between subsequent runs
     * @return a handle for cancelling the repeating task
     */
    TaskHandle runRepeating(Runnable r, long d, long l);
}
