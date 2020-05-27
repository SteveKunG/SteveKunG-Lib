package stevekung.mods.stevekunglib.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.util.EnumParticleTypes;
import stevekung.mods.stevekunglib.utils.client.EventHooksClient;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin
{
    @Shadow
    private int rendererUpdateCount;

    @Shadow
    @Final
    @Mutable
    private Minecraft mc;

    @Inject(method = "setupCameraTransform(FI)V", cancellable = true, at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/EntityRenderer.orientCamera(F)V", shift = At.Shift.BEFORE))
    private void injectCameraEvent(float partialTicks, int pass, CallbackInfo info)
    {
        EventHooksClient.onCameraTransform(this.rendererUpdateCount, partialTicks);
    }

    @Redirect(method = "addRainParticles()V", at = @At(value = "INVOKE", target = "net/minecraft/client/multiplayer/WorldClient.spawnParticle(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V"))
    private void replaceRainParticles(WorldClient world, EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters)
    {
        if (!EventHooksClient.onAddRainParticle(world, xCoord, yCoord, zCoord))
        {
            world.spawnParticle(particleType, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
        }
    }
}