package com.stevekung.stevekungslib.utils.event;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.Event;

public class WeatherTickEvent extends Event
{
    private final World world;
    private final BlockPos strikePos;

    public WeatherTickEvent(World world, BlockPos strikePos)
    {
        this.world = world;
        this.strikePos = strikePos;
    }

    public World getWorld()
    {
        return this.world;
    }

    public BlockPos getStrikePos()
    {
        return this.strikePos;
    }
}