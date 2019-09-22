package com.stevekung.stevekungslib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.stevekung.stevekungslib.utils.client.EventHooksClient;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.entity.Entity;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer
{
    @Inject(method = "doRenderShadowAndFire(Lnet/minecraft/entity/Entity;DDDFF)V", cancellable = true, at = @At(value = "INVOKE", target = "net/minecraft/entity/Entity.canRenderOnFire()Z", shift = At.Shift.BEFORE))
    private void injectPersonViewOverlayEvent(Entity entity, double x, double y, double z, float yaw, float partialTicks, CallbackInfo info)
    {
        EventHooksClient.onRenderEntityOverlay(entity, x, y, z, partialTicks);
    }
}