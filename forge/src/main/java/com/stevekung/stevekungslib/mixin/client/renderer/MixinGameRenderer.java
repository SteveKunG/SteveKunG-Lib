package com.stevekung.stevekungslib.mixin.client.renderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.stevekungslib.utils.client.EventHooksClient;
import net.minecraft.client.renderer.GameRenderer;

@Mixin(GameRenderer.class)
public class MixinGameRenderer
{
    @Shadow
    private int tick;

    @Inject(method = "renderLevel(FJLcom/mojang/blaze3d/vertex/PoseStack;)V", at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/GameRenderer.bobHurt(Lcom/mojang/blaze3d/vertex/PoseStack;F)V", shift = At.Shift.AFTER))
    private void injectCameraEvent(float partialTicks, long finishTimeNano, PoseStack poseStack, CallbackInfo info)
    {
        EventHooksClient.onCameraTransform(this.tick, partialTicks, poseStack);
    }

    @Inject(method = "render(FJZ)V", at = @At(value = "INVOKE", target = "net/minecraft/util/Mth.lerp(FFF)F", shift = At.Shift.AFTER))
    private void render(float partialTicks, long nanoTime, boolean renderWorld, CallbackInfo info)
    {
        EventHooksClient.onRenderOverlayScreen(partialTicks);
    }
}