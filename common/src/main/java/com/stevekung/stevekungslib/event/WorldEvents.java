package com.stevekung.stevekungslib.event;

import me.shedaniel.architectury.event.Event;
import me.shedaniel.architectury.event.EventFactory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface WorldEvents
{
    Event<WeatherTickEvent> WEATHER_TICK = EventFactory.createLoop();

    @Environment(EnvType.CLIENT)
    interface WeatherTickEvent
    {
        void tick(Level level, BlockPos strikePos);
    }
}