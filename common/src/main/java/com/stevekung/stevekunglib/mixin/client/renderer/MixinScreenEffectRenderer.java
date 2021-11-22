package com.stevekung.stevekunglib.mixin.client.renderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.stevekunglib.client.event.RenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ScreenEffectRenderer;

@Mixin(ScreenEffectRenderer.class)
public class MixinScreenEffectRenderer
{
    @Inject(method = "renderScreenEffect", at = @At("HEAD"))
    private static void stevekung_lib$renderFirstPersonViewOverlayEvent(Minecraft mc, PoseStack poseStack, CallbackInfo info)
    {
        if (!mc.player.isSpectator())
        {
            RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
            RenderEvents.FIRST_PERSON_VIEW_RENDER.invoker().firstPersonViewRender(poseStack);
        }
    }
}