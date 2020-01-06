package com.stevekung.stevekungslib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stevekung.stevekungslib.utils.client.EventHooksClient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OverlayRenderer;

@Mixin(OverlayRenderer.class)
public class MixinOverlayRenderer
{
    @Inject(method = "func_228734_a_(Lnet/minecraft/client/Minecraft;Lcom/mojang/blaze3d/matrix/MatrixStack;)V", at = @At(value = "INVOKE", target = "net/minecraft/client/entity/player/ClientPlayerEntity.isSpectator()Z", shift = At.Shift.AFTER))
    private static void renderFirstPersonViewOverlayEvent(Minecraft mc, MatrixStack stack, CallbackInfo info)
    {
        if (!mc.player.isSpectator())
        {
            EventHooksClient.onRenderFirstPersonViewOverlay();
        }
    }
}