package com.stevekung.stevekungslib.utils.client.event;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraftforge.eventbus.api.Event;

public class CameraTransformEvent extends Event
{
    private final int rendererUpdateCount;
    private final float partialTicks;
    private final MatrixStack stack;

    public CameraTransformEvent(int rendererUpdateCount, float partialTicks, MatrixStack stack)
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

    public MatrixStack getMatrixStack()
    {
        return this.stack;
    }
}