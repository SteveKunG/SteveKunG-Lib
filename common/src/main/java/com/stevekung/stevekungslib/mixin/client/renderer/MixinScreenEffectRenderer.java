package com.stevekung.stevekungslib.mixin.client.renderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.stevekungslib.client.event.RenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenEffectRenderer;

@Mixin(ScreenEffectRenderer.class)
public class MixinScreenEffectRenderer
{
    @Inject(method = "renderScreenEffect(Lnet/minecraft/client/Minecraft;Lcom/mojang/blaze3d/vertex/PoseStack;)V", at = @At("HEAD"))
    private static void renderFirstPersonViewOverlayEvent(Minecraft mc, PoseStack poseStack, CallbackInfo info)
    {
        if (!mc.player.isSpectator())
        {
            RenderEvents.FIRST_PERSON_VIEW_RENDER.invoker().firstPersonViewRender(poseStack);
        }
    }
}