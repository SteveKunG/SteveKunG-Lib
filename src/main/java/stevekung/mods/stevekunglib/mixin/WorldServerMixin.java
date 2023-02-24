package stevekung.mods.stevekunglib.mixin;

import java.util.Iterator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import stevekung.mods.stevekunglib.utils.EventHooksCommon;

@Mixin(value = WorldServer.class, priority = 10000)
public abstract class WorldServerMixin
{
    @Shadow
    abstract BlockPos adjustPosToNearbyEntity(BlockPos pos);

    @Inject(method = "updateBlocks()V", at = @At(value = "INVOKE", target = "net/minecraft/profiler/Profiler.startSection(Ljava/lang/String;)V", shift = At.Shift.AFTER, ordinal = 0))
    private void stevekunglib$injectWeatherTickEvent(CallbackInfo info)
    {
        WorldServer world = WorldServer.class.cast(this);

        for (Iterator<Chunk> iterator = world.getPersistentChunkIterable(world.getPlayerChunkMap().getChunkIterator()); iterator.hasNext();)
        {
            Chunk chunk = iterator.next();
            int j = chunk.x * 16;
            int k = chunk.z * 16;
            world.updateLCG = world.updateLCG * 3 + 1013904223;
            int l = world.updateLCG >> 2;
            BlockPos strikePos = this.adjustPosToNearbyEntity(new BlockPos(j + (l & 15), 0, k + (l >> 8 & 15)));
            EventHooksCommon.onWeatherTick(world, chunk, strikePos);
        }
    }
}