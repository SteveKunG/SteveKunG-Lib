package stevekung.mods.stevekunglib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import stevekung.mods.stevekunglib.utils.EventHooksCommon;

@Mixin(value = WorldServer.class, priority = 10000)
public class WorldServerMixin
{
    @Inject(method = "updateBlocks", at = @At(value = "INVOKE", target = "net/minecraft/profiler/Profiler.endStartSection(Ljava/lang/String;)V", ordinal = 2, shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void stevekunglib$injectWeatherTickEvent(CallbackInfo info, @Local Chunk chunk, @Local(index = 6, ordinal = 1) int chunkX, @Local(index = 6, ordinal = 2) int chunkZ)
    {
        EventHooksCommon.onWeatherTick(WorldServer.class.cast(this), chunk, chunkX, chunkZ);
    }
}