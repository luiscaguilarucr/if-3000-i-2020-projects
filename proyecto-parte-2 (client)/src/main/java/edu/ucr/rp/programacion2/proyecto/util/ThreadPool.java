package edu.ucr.rp.programacion2.proyecto.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ThreadPool {
    private static ExecutorService executorService;
    private static ScheduledExecutorService scheduledExecutorService;

    public static ExecutorService getPool() {
        if (executorService == null) {
            executorService = Executors.newCachedThreadPool();
        }
        return executorService;
    }

    public static ScheduledExecutorService getScheduledPool() {
        if (scheduledExecutorService == null) {
            scheduledExecutorService = Executors.newScheduledThreadPool(20);
        }
        return scheduledExecutorService;
    }

    public static void pause(boolean state){
        try {
            while(state) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void shutdown(){
         getPool().shutdownNow();
    }
    public static void pause() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void pause2() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
