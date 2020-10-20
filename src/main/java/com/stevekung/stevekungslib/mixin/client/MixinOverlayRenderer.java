package com.stevekung.stevekungslib.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.stevekung.stevekungslib.utils.client.EventHooksClient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OverlayRenderer;

@Mixin(OverlayRenderer.class)
public class MixinOverlayRenderer
{
    @SuppressWarnings("deprecation")
    @Inject(method = "renderOverlays(Lnet/minecraft/client/Minecraft;Lcom/mojang/blaze3d/matrix/MatrixStack;)V", at = @At("HEAD"))
    private static void renderFirstPersonViewOverlayEvent(Minecraft mc, MatrixStack stack, CallbackInfo info)
    {
        RenderSystem.disableAlphaTest();

        if (!mc.player.isSpectator())
        {
            EventHooksClient.onRenderOverlayFirstPersonView(stack);
        }

        RenderSystem.enableAlphaTest();
    }
}