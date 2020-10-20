package com.stevekung.stevekungslib.utils.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stevekung.stevekungslib.utils.client.event.AddRainParticleEvent;
import com.stevekung.stevekungslib.utils.client.event.CameraTransformEvent;
import com.stevekung.stevekungslib.utils.client.event.RenderEntityOverlayEvent;
import com.stevekung.stevekungslib.utils.client.event.RenderOverlayEvent;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class EventHooksClient
{
    public static void onCameraTransform(int rendererUpdateCount, float partialTicks, MatrixStack stack)
    {
        MinecraftForge.EVENT_BUS.post(new CameraTransformEvent(rendererUpdateCount, partialTicks, stack));
    }

    public static boolean onAddRainParticle(World world, double x, double y, double z)
    {
        return MinecraftForge.EVENT_BUS.post(new AddRainParticleEvent(world, x, y, z));
    }

    public static void onRenderOverlayFirstPersonView(MatrixStack stack)
    {
        MinecraftForge.EVENT_BUS.post(new RenderOverlayEvent.FirstPersonView(stack));
    }

    public static void onRenderOverlayScreen(float partialTicks)
    {
        MinecraftForge.EVENT_BUS.post(new RenderOverlayEvent.Screen(partialTicks));
    }

    public static void onRenderEntityOverlay(Entity entity, double x, double y, double z, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer)
    {
        MinecraftForge.EVENT_BUS.post(new RenderEntityOverlayEvent(entity, x, y, z, partialTicks, stack, buffer));
    }
}