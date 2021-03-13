package com.stevekung.stevekungslib.utils.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.eventbus.api.Event;

public class CameraTransformEvent extends Event
{
    private final int rendererUpdateCount;
    private final float partialTicks;
    private final PoseStack poseStack;

    public CameraTransformEvent(int rendererUpdateCount, float partialTicks, PoseStack poseStack)
    {
        this.rendererUpdateCount = rendererUpdateCount;
        this.partialTicks = partialTicks;
        this.poseStack = poseStack;
    }

    public int getRendererUpdateCount()
    {
        return this.rendererUpdateCount;
    }

    public float getPartialTicks()
    {
        return this.partialTicks;
    }

    public PoseStack getPoseStack()
    {
        return this.poseStack;
    }
}