package com.stevekung.stevekunglib.mixin.client.renderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import com.stevekung.stevekunglib.client.event.RenderEvents;
import dev.architectury.event.EventResult;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.particles.ParticleOptions;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer
{
    @Redirect(method = "tickRain(Lnet/minecraft/client/Camera;)V", at = @At(value = "INVOKE", target = "net/minecraft/client/multiplayer/ClientLevel.addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
    private void replaceRainParticles(ClientLevel level, ParticleOptions options, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
    {
        var event = RenderEvents.RAIN_PARTICLE.invoker().addRainParticle(level, x, y, z);

        if (event == EventResult.interruptTrue())
        {
            level.addParticle(options, x, y, z, 0.0D, 0.0D, 0.0D);
        }
    }
}