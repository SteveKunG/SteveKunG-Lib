package com.stevekung.stevekungslib.utils;

import com.stevekung.stevekungslib.utils.event.WeatherTickEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;

public class CommonEventHooks
{
    public static void onWeatherTick(Level world, BlockPos strikePos)
    {
        MinecraftForge.EVENT_BUS.post(new WeatherTickEvent(world, strikePos));
    }
}