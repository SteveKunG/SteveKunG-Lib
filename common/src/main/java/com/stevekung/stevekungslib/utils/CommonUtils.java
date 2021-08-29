package com.stevekung.stevekungslib.utils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.jetbrains.annotations.NotNull;
import com.google.common.collect.Maps;
import com.stevekung.stevekungslib.core.SteveKunGLib;
import dev.architectury.platform.Platform;
import i.am.cal.antisteal.Antisteal;
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
        var seconds = ticks / 20;
        var minutes = seconds / 60;
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
        var task = new TimerTask()
        {
            @Override
            public void run()
            {
                runnable.run();
            }
        };
        new Timer().schedule(task, delay);
    }

    public static void initAntisteal(String name, Class<?> modClass, Runnable close)
    {
        var mod = Platform.getMod(name);
        var path = mod.getFilePath();
        var domainMaps = Maps.<String, String>newHashMap();
        SteveKunGLib.LOGGER.debug(path.toUri().toString());
        domainMaps.put("Curseforge", "curseforge.com");
        domainMaps.put("Modrinth", "modrinth.com");
        domainMaps.put("GitHub", "github.com");
        Antisteal.check(path, () ->
        {
            SteveKunGLib.LOGGER.error("Get official mod download here: {}", mod.getHomepage().get());
            close.run();
        }, domainMaps, modClass);
    }
}