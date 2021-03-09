package com.stevekung.stevekungslib.utils.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.eventbus.api.Event;

public class CameraTransformEvent extends Event
{
    private final int rendererUpdateCount;
    private final float partialTicks;
    private final PoseStack stack;

    public CameraTransformEvent(int rendererUpdateCount, float partialTicks, PoseStack stack)
    {
        this.rendererUpdateCount = rendererUpdateCount;
        this.partialTicks = partialTicks;
        this.stack = stack;
    }

    public int getRendererUpdateCount()
    {
        return this.rendererUpdateCount;
    }

    public float getPartialTicks()
    {
        return this.partialTicks;
    }

    public PoseStack getMatrixStack()
    {
        return this.stack;
    }
}