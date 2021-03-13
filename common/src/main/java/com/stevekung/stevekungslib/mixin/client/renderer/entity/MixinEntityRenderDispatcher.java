package com.stevekung.stevekungslib.mixin.client.renderer.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.stevekungslib.client.event.RenderEvents;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;

@Mixin(EntityRenderDispatcher.class)
public class MixinEntityRenderDispatcher
{
    @Inject(method = "render(Lnet/minecraft/world/entity/Entity;DDDFFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "net/minecraft/world/entity/Entity.displayFireAnimation()Z", shift = At.Shift.BEFORE))
    private <E extends Entity> void renderEntityOverlayEvent(E entity, double x, double y, double z, float yaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int color, CallbackInfo info)
    {
        if (!entity.isSpectator())
        {
            RenderEvents.RENDER_ENTITY_OVERLAY.invoker().renderEntityOverlay(entity, x, y, z, partialTicks, stack, buffer);
        }
    }
}