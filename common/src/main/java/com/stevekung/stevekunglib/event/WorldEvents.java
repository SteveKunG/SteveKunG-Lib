package com.stevekung.stevekunglib.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import net.minecraft.world.level.Level;

public interface WorldEvents
{
    Event<WeatherTickEvent> WEATHER_TICK = EventFactory.createLoop();

    interface WeatherTickEvent
    {
        void tick(Level level, int chunkPosX, int chunkPosZ);
    }
}