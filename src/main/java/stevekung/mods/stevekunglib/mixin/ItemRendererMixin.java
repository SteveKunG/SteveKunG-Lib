package stevekung.mods.stevekunglib.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import stevekung.mods.stevekunglib.utils.client.EventHooksClient;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin
{
    @Shadow
    @Final
    private Minecraft mc;

    @Inject(method = "renderOverlays(F)V", cancellable = true, at = @At(value = "INVOKE", target = "net/minecraft/client/entity/EntityPlayerSP.isSpectator()Z", shift = At.Shift.AFTER))
    private void injectPersonViewOverlayEvent(float partialTicks, CallbackInfo info)
    {
        if (!this.mc.player.isSpectator())
        {
            EventHooksClient.onRenderFirstPersonViewOverlay();
        }
    }
}