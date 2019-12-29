package com.stevekung.stevekungslib.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.stevekung.stevekungslib.utils.client.EventHooksClient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FirstPersonRenderer;

@Mixin(FirstPersonRenderer.class)
public class MixinFirstPersonRenderer
{
    @Shadow
    @Final
    @Mutable
    private Minecraft mc;

    @Inject(method = "renderOverlays(F)V", at = @At(value = "INVOKE", target = "net/minecraft/client/entity/player/ClientPlayerEntity.isSpectator()Z", shift = At.Shift.AFTER))
    private void injectPersonViewOverlayEvent(float partialTicks, CallbackInfo info)
    {
        if (!this.mc.player.isSpectator())
        {
            EventHooksClient.onRenderFirstPersonViewOverlay();
        }
    }
}