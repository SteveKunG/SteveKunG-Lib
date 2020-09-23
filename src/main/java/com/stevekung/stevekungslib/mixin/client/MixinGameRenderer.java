package com.stevekung.stevekungslib.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stevekung.stevekungslib.utils.client.EventHooksClient;

import net.minecraft.client.renderer.GameRenderer;

@Mixin(GameRenderer.class)
public class MixinGameRenderer
{
    @Shadow
    private int rendererUpdateCount;

    @Inject(method = "renderWorld(FJLcom/mojang/blaze3d/matrix/MatrixStack;)V", at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/GameRenderer.hurtCameraEffect(Lcom/mojang/blaze3d/matrix/MatrixStack;F)V", shift = At.Shift.AFTER))
    private void injectCameraEvent(float partialTicks, long finishTimeNano, MatrixStack matrixStack, CallbackInfo info)
    {
        EventHooksClient.onCameraTransform(this.rendererUpdateCount, partialTicks, matrixStack);
    }
}