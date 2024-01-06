package stevekung.mods.stevekunglib.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.util.EnumParticleTypes;
import stevekung.mods.stevekunglib.utils.client.EventHooksClient;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin
{
    @Shadow
    int rendererUpdateCount;

    @Shadow
    @Final
    Minecraft mc;

    @Inject(method = "orientCamera", at = @At("HEAD"))
    private void stevekunglib$injectCameraEvent(float partialTicks, CallbackInfo info)
    {
        EventHooksClient.onCameraTransform(this.rendererUpdateCount, partialTicks);
    }

    @WrapWithCondition(method = "addRainParticles", at = @At(value = "INVOKE", target = "net/minecraft/client/multiplayer/WorldClient.spawnParticle(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V", ordinal = 0))
    private boolean stevekunglib$replaceRainParticles(WorldClient world, EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters)
    {
        return !EventHooksClient.onAddRainParticle(world, xCoord, yCoord, zCoord);
    }
}