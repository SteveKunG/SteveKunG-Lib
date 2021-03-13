package com.stevekung.stevekungslib.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import me.shedaniel.architectury.event.Event;
import me.shedaniel.architectury.event.EventFactory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public interface RenderEvents
{
    Event<AddRainParticleEvent> RAIN_PARTICLE = EventFactory.createInteractionResult();
    Event<CameraTransformEvent> CAMERA_TRANSFORM = EventFactory.createLoop();
    Event<FirstPersonViewRenderEvent> FIRST_PERSON_VIEW_RENDER = EventFactory.createLoop();
    Event<RenderScreenOverlay> RENDER_SCREEN_OVERLAY = EventFactory.createLoop();
    Event<RenderEntityOverlayEvent> RENDER_ENTITY_OVERLAY = EventFactory.createLoop();

    @Environment(EnvType.CLIENT)
    interface AddRainParticleEvent
    {
        InteractionResult addRainParticle(Level level, double x, double y, double z);
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