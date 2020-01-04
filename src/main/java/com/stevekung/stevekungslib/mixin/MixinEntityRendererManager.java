package com.stevekung.stevekungslib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.stevekung.stevekungslib.utils.client.EventHooksClient;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;

@Mixin(EntityRendererManager.class)
public class MixinEntityRendererManager
{
    @Inject(method = "renderEntity(Lnet/minecraft/entity/Entity;DDDFFZ)V", at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/entity/EntityRenderer.doRenderShadowAndFire(Lnet/minecraft/entity/Entity;DDDFF)V", shift = At.Shift.AFTER))
    private void renderEntityOverlayEvent(Entity entity, double x, double y, double z, float yaw, float partialTicks, boolean p_188391_10_, CallbackInfo info)
    {
        if (!entity.isSpectator())
        {
            EventHooksClient.onRenderEntityOverlay(entity, x, y, z, partialTicks);
        }
    }
}