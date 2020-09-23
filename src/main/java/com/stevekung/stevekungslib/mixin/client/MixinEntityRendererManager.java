package com.stevekung.stevekungslib.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stevekung.stevekungslib.utils.client.EventHooksClient;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;

@Mixin(EntityRendererManager.class)
public class MixinEntityRendererManager
{
    @Inject(method = "renderEntityStatic(Lnet/minecraft/entity/Entity;DDDFFLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;I)V", at = @At(value = "INVOKE", target = "net/minecraft/entity/Entity.canRenderOnFire()Z", shift = At.Shift.BEFORE))
    private <E extends Entity> void renderEntityOverlayEvent(E entity, double x, double y, double z, float yaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int color, CallbackInfo info)
    {
        if (!entity.isSpectator())
        {
            EventHooksClient.onRenderEntityOverlay(entity, x, y, z, partialTicks, stack, buffer);
        }
    }
}