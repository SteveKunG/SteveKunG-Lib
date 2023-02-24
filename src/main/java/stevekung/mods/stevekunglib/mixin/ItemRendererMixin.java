package stevekung.mods.stevekunglib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.renderer.ItemRenderer;
import stevekung.mods.stevekunglib.utils.client.EventHooksClient;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin
{
    @Inject(method = "renderOverlays", at = @At(value = "INVOKE", target = "net/minecraft/client/entity/EntityPlayerSP.isInsideOfMaterial(Lnet/minecraft/block/material/Material;)Z"))
    private void stevekunglib$injectFirstPersonViewOverlayEvent(float partialTicks, CallbackInfo info)
    {
        EventHooksClient.onRenderFirstPersonViewOverlay();
    }
}