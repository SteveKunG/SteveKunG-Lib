package com.stevekung.lib.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import com.stevekung.lib.utils.client.EventHooksClient;

@Mixin(Render.class)
public class RenderMixin
{
    @Inject(method = "doRenderShadowAndFire", at = @At(value = "INVOKE", target = "net/minecraft/entity/Entity.canRenderOnFire()Z"))
    private void stevekung_lib$injectPersonViewOverlayEvent(Entity entity, double x, double y, double z, float yaw, float partialTicks, CallbackInfo info)
    {
        EventHooksClient.onRenderEntityOverlay(entity, x, y, z, partialTicks);
    }
}