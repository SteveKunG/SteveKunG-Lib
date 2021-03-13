package com.stevekung.stevekungslib.utils.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.Event;

public class WeatherTickEvent extends Event
{
    private final Level level;
    private final BlockPos strikePos;

    public WeatherTickEvent(Level level, BlockPos strikePos)
    {
        this.level = level;
        this.strikePos = strikePos;
    }

    public Level getLevel()
    {
        return this.level;
    }

    public BlockPos getStrikePos()
    {
        return this.strikePos;
    }
}