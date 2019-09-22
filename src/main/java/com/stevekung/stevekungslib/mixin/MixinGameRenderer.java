package com.stevekung.stevekungslib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.stevekung.stevekungslib.utils.client.EventHooksClient;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.IParticleData;

@Mixin(GameRenderer.class)
public class MixinGameRenderer
{
    @Shadow
    private int rendererUpdateCount;

    @Redirect(method = "addRainParticles()V", at = @At(value = "INVOKE", target = "net/minecraft/client/world/ClientWorld.addParticle(Lnet/minecraft/particles/IParticleData;DDDDDD)V"), expect = 0)
    private void replaceRainParticles(ClientWorld world, IParticleData data, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
    {
        if (!EventHooksClient.onAddRainParticle(world, x, y, z))
        {
            world.addParticle(data, x, y, z, 0.0D, 0.0D, 0.0D);
        }
    }

    @Inject(method = "setupCameraTransform(F)V", cancellable = true, at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/GameRenderer.applyBobbing(F)V", shift = At.Shift.AFTER))
    private void injectCameraEvent(float partialTicks, CallbackInfo info)
    {
        EventHooksClient.onCameraTransform(this.rendererUpdateCount, partialTicks);
    }
}