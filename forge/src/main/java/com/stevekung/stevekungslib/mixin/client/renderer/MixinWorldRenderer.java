package com.stevekung.stevekungslib.mixin.client.renderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.stevekung.stevekungslib.utils.client.EventHooksClient;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.particles.ParticleOptions;

@Mixin(LevelRenderer.class)
public class MixinWorldRenderer
{
    @Redirect(method = "tickRain(Lnet/minecraft/client/Camera;)V", at = @At(value = "INVOKE", target = "net/minecraft/client/multiplayer/ClientLevel.addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
    private void replaceRainParticles(ClientLevel world, ParticleOptions data, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
    {
        if (!EventHooksClient.onAddRainParticle(world, x, y, z))
        {
            world.addParticle(data, x, y, z, 0.0D, 0.0D, 0.0D);
        }
    }
}