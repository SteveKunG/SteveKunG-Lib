package com.stevekung.stevekungslib.utils.client;

import com.stevekung.stevekungslib.utils.client.event.AddRainParticleEvent;
import com.stevekung.stevekungslib.utils.client.event.CameraTransformEvent;
import com.stevekung.stevekungslib.utils.client.event.FirstPersonViewOverlayEvent;
import com.stevekung.stevekungslib.utils.client.event.RenderEntityOverlayEvent;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class EventHooksClient
{
    public static void onCameraTransform(int rendererUpdateCount, float partialTicks)
    {
        MinecraftForge.EVENT_BUS.post(new CameraTransformEvent(rendererUpdateCount, partialTicks));
    }

    public static boolean onAddRainParticle(World world, double x, double y, double z)
    {
        return MinecraftForge.EVENT_BUS.post(new AddRainParticleEvent(world, x, y, z));
    }

    public static void onRenderFirstPersonViewOverlay()
    {
        MinecraftForge.EVENT_BUS.post(new FirstPersonViewOverlayEvent());
    }

    public static void onRenderEntityOverlay(Entity entity, double x, double y, double z, float partialTicks)
    {
        MinecraftForge.EVENT_BUS.post(new RenderEntityOverlayEvent(entity, x, y, z, partialTicks));
    }
}