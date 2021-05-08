package com.stevekung.stevekungslib.event;

import me.shedaniel.architectury.event.Event;
import me.shedaniel.architectury.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface WorldEvents
{
    Event<WeatherTickEvent> WEATHER_TICK = EventFactory.createLoop();

    interface WeatherTickEvent
    {
        void tick(Level level, BlockPos strikePos);
    }
}