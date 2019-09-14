package com.stevekung.stevekungslib.utils.client.event;

import net.minecraftforge.eventbus.api.Event;

public class CameraTransformEvent extends Event
{
    private final int rendererUpdateCount;
    private final float partialTicks;

    public CameraTransformEvent(int rendererUpdateCount, float partialTicks)
    {
        this.rendererUpdateCount = rendererUpdateCount;
        this.partialTicks = partialTicks;
    }

    public int getRendererUpdateCount()
    {
        return this.rendererUpdateCount;
    }

    public float getPartialTicks()
    {
        return this.partialTicks;
    }
}