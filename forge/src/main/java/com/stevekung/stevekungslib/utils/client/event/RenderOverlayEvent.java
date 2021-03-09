package com.stevekung.stevekungslib.utils.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.eventbus.api.Event;

public class RenderOverlayEvent extends Event
{
    public static class FirstPersonView extends RenderOverlayEvent
    {
        private final PoseStack stack;

        public FirstPersonView(PoseStack stack)
        {
            this.stack = stack;
        }

        public PoseStack getMatrixStack()
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