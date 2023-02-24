package stevekung.mods.stevekunglib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import stevekung.mods.stevekunglib.utils.client.EventHooksClient;

@Mixin(Render.class)
public class RenderMixin
{
    @Inject(method = "doRenderShadowAndFire", at = @At(value = "INVOKE", target = "net/minecraft/entity/Entity.canRenderOnFire()Z"))
    private void stevekunglib$injectPersonViewOverlayEvent(Entity entity, double x, double y, double z, float yaw, float partialTicks, CallbackInfo info)
    {
        EventHooksClient.onRenderEntityOverlay(entity, x, y, z, partialTicks);
    }
}