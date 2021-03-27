package com.stevekung.stevekungslib.mixin.client.renderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import com.stevekung.stevekungslib.client.event.RenderEvents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.InteractionResult;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer
{
    @Redirect(method = "tickRain(Lnet/minecraft/client/Camera;)V", at = @At(value = "INVOKE", target = "net/minecraft/client/multiplayer/ClientLevel.addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
    private void replaceRainParticles(ClientLevel level, ParticleOptions options, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
    {
        InteractionResult event = RenderEvents.RAIN_PARTICLE.invoker().addRainParticle(level, x, y, z);

        if (event != InteractionResult.PASS)
        {
            level.addParticle(options, x, y, z, 0.0D, 0.0D, 0.0D);
        }
    }
}