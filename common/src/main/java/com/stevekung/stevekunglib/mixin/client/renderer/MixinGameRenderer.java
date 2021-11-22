package com.stevekung.stevekunglib.mixin.client.renderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.stevekunglib.client.event.RenderEvents;
import net.minecraft.client.renderer.GameRenderer;

@Mixin(GameRenderer.class)
public class MixinGameRenderer
{
    @Shadow
    int tick;

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/GameRenderer.bobHurt(Lcom/mojang/blaze3d/vertex/PoseStack;F)V", shift = At.Shift.AFTER))
    private void stevekung_lib$addCameraEvent(float partialTicks, long finishTimeNano, PoseStack poseStack, CallbackInfo info)
    {
        RenderEvents.CAMERA_TRANSFORM.invoker().cameraTransform(this.tick, partialTicks, poseStack);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "net/minecraft/util/Mth.lerp(FFF)F", shift = At.Shift.AFTER))
    private void stevekung_lib$renderScreenOverlayEvent(float partialTicks, long nanoTime, boolean renderWorld, CallbackInfo info)
    {
        RenderEvents.RENDER_SCREEN_OVERLAY.invoker().renderScreenOverlay(partialTicks);
    }
}