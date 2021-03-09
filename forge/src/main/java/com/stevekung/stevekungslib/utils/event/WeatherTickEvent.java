package com.stevekung.stevekungslib.utils.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.Event;

public class WeatherTickEvent extends Event
{
    private final Level world;
    private final BlockPos strikePos;

    public WeatherTickEvent(Level world, BlockPos strikePos)
    {
        this.world = world;
        this.strikePos = strikePos;
    }

    public Level getWorld()
    {
        return this.world;
    }

    public BlockPos getStrikePos()
    {
        return this.strikePos;
    }
}