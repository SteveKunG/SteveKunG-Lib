package com.stevekung.stevekungslib.utils.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import net.minecraftforge.fml.loading.FMLLoader;

public class ClientUtilsImpl
{
    public static boolean isClient()
    {
        return FMLLoader.getDist() == Dist.CLIENT;
    }

    public static boolean isEffectiveClient()
    {
        return EffectiveSide.get() == LogicalSide.CLIENT;
    }
}