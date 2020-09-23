package com.stevekung.stevekungslib.utils;

import com.stevekung.stevekungslib.utils.event.WeatherTickEvent;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class CommonEventHooks
{
    public static void onWeatherTick(World world, BlockPos strikePos)
    {
        MinecraftForge.EVENT_BUS.post(new WeatherTickEvent(world, strikePos));
    }
}