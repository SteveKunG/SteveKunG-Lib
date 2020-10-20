package com.stevekung.stevekungslib.utils.client.event;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraftforge.eventbus.api.Event;

public class RenderOverlayEvent extends Event
{
    public static class FirstPersonView extends RenderOverlayEvent
    {
        private final MatrixStack stack;

        public FirstPersonView(MatrixStack stack)
        {
            this.stack = stack;
        }

        public MatrixStack getMatrixStack()
        {
            return this.stack;
        }
    }

    public static class Screen extends RenderOverlayEvent
    {
        private final float partialTicks;

        public Screen(float partialTicks)
        {
            this.partialTicks = partialTicks;
        }

        public float getPartialTicks()
        {
            return this.partialTicks;
        }
    }
}