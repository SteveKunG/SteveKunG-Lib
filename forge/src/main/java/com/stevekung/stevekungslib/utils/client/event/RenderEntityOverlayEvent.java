package com.stevekung.stevekungslib.utils.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.eventbus.api.Event;

public class RenderEntityOverlayEvent extends Event
{
    private final Entity entity;
    private final double x;
    private final double y;
    private final double z;
    private final float partialTicks;
    private final PoseStack stack;
    private final MultiBufferSource buffer;

    public RenderEntityOverlayEvent(Entity entity, double x, double y, double z, float partialTicks, PoseStack stack, MultiBufferSource buffer)
    {
        this.entity = entity;
        this.x = x;
        this.y = y;
        this.z = z;
        this.partialTicks = partialTicks;
        this.stack = stack;
        this.buffer = buffer;
    }

    public Entity getEntity()
    {
        return this.entity;
    }

    public double getX()
    {
        return this.x;
    }

    public double getY()
    {
        return this.y;
    }

    public double getZ()
    {
        return this.z;
    }

    public float getPartialTicks()
    {
        return this.partialTicks;
    }

    public PoseStack getMatrixStack()
    {
        return this.stack;
    }

    public MultiBufferSource getRenderTypeBuffer()
    {
        return this.buffer;
    }
}