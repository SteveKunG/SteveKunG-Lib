package com.stevekung.stevekungslib.utils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.jetbrains.annotations.NotNull;
import net.minecraft.Util;

public class CommonUtils
{
    private static final ExecutorService POOL = Executors.newFixedThreadPool(100, new ThreadFactory()
    {
        private final AtomicInteger counter = new AtomicInteger(0);

        @Override
        public Thread newThread(@NotNull Runnable runnable)
        {
            return new Thread(runnable, String.format("Thread %s", this.counter.incrementAndGet()));
        }
    });

    public static String ticksToElapsedTime(int ticks)
    {
        int seconds = ticks / 20;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return seconds < 10 ? minutes + ":0" + seconds : minutes + ":" + seconds;
    }

    public static void openLink(String url)
    {
        Util.getPlatform().openUri(url);
    }

    public static void runAsync(Runnable runnable)
    {
        CompletableFuture.runAsync(runnable, CommonUtils.POOL);
    }

    public static void schedule(Runnable runnable, long delay)
    {
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                runnable.run();
            }
        };
        new Timer().schedule(task, delay);
    }
}