package com.stevekung.stevekungslib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.stevekung.stevekungslib.utils.client.EventHooksClient;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.IParticleData;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer
{
    @Redirect(method = "func_228436_a_(Lnet/minecraft/client/renderer/ActiveRenderInfo;)V", at = @At(value = "INVOKE", target = "net/minecraft/client/world/ClientWorld.addParticle(Lnet/minecraft/particles/IParticleData;DDDDDD)V"), expect = 0)
    private void replaceRainParticles(ClientWorld world, IParticleData data, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
    {
        if (!EventHooksClient.onAddRainParticle(world, x, y, z))
        {
            world.addParticle(data, x, y, z, 0.0D, 0.0D, 0.0D);
        }
    }
}