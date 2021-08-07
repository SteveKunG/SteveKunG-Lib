package com.stevekung.stevekungslib.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public interface RenderEvents
{
    Event<AddRainParticleEvent> RAIN_PARTICLE = EventFactory.createEventResult();
    Event<CameraTransformEvent> CAMERA_TRANSFORM = EventFactory.createLoop();
    Event<FirstPersonViewRenderEvent> FIRST_PERSON_VIEW_RENDER = EventFactory.createLoop();
    Event<RenderScreenOverlay> RENDER_SCREEN_OVERLAY = EventFactory.createLoop();
    Event<RenderEntityOverlayEvent> RENDER_ENTITY_OVERLAY = EventFactory.createLoop();

    @Environment(EnvType.CLIENT)
    interface AddRainParticleEvent
    {
        EventResult addRainParticle(Level level, double x, double y, double z);
    }

    @Environment(EnvType.CLIENT)
    interface CameraTransformEvent
    {
        void cameraTransform(int rendererUpdateCount, float partialTicks, PoseStack poseStack);
    }

    @Environment(EnvType.CLIENT)
    interface FirstPersonViewRenderEvent
    {
        void firstPersonViewRender(PoseStack poseStack);
    }

    @Environment(EnvType.CLIENT)
    interface RenderScreenOverlay
    {
        void renderScreenOverlay(float partialTicks);
    }

    @Environment(EnvType.CLIENT)
    interface RenderEntityOverlayEvent
    {
        void renderEntityOverlay(Entity entity, double x, double y, double z, float partialTicks, PoseStack poseStack, MultiBufferSource buffer);
    }
}