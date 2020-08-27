package stevekung.mods.stevekunglib.mixin;

import java.util.Iterator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import stevekung.mods.stevekunglib.utils.EventHooksCommon;

@Mixin(value = WorldServer.class, priority = 10000)
public abstract class WorldServerMixin
{
    private final WorldServer that = (WorldServer) (Object) this;

    @Shadow
    protected abstract BlockPos adjustPosToNearbyEntity(BlockPos pos);

    @Inject(method = "updateBlocks()V", at = @At(value = "INVOKE", target = "net/minecraft/world/WorldProvider.canDoLightning(Lnet/minecraft/world/chunk/Chunk;)Z", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD)
    private void injectWeatherTickEvent(CallbackInfo info, int i, boolean flag, boolean flag1, Iterator iterator, Chunk chunk, int j, int k)
    {
        this.that.updateLCG = this.that.updateLCG * 3 + 1013904223;
        int l = this.that.updateLCG >> 2;
        BlockPos strikePos = this.adjustPosToNearbyEntity(new BlockPos(j + (l & 15), 0, k + (l >> 8 & 15)));
        EventHooksCommon.onWeatherTick(this.that, chunk, strikePos);
    }
}