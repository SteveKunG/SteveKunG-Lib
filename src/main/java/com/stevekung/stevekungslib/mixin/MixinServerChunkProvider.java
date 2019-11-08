package com.stevekung.stevekungslib.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.stevekung.stevekungslib.utils.CommonEventHooks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ChunkHolder;
import net.minecraft.world.server.ServerChunkProvider;

@Mixin(ServerChunkProvider.class)
public abstract class MixinServerChunkProvider
{
    private final ServerChunkProvider that = (ServerChunkProvider) (Object) this;

    @Shadow
    private long lastGameTime;

    @Inject(method = "tickChunks()V", cancellable = true, at = @At(value = "INVOKE", target = "net/minecraft/profiler/IProfiler.endSection()V", shift = At.Shift.AFTER, ordinal = 0))
    private void injectWeatherTickEvent(CallbackInfo info)
    {
        long i = this.that.world.getGameTime();
        long j = i - this.lastGameTime;
        this.lastGameTime = i;

        this.that.chunkManager.getLoadedChunksIterable().forEach(holder ->
        {
            Optional<Chunk> optional = holder.func_219297_b().getNow(ChunkHolder.UNLOADED_CHUNK).left();

            if (optional.isPresent())
            {
                Chunk chunk = optional.get();
                holder.sendChanges(chunk);
                ChunkPos chunkpos = holder.getPosition();

                if (!this.that.chunkManager.isOutsideSpawningRadius(chunkpos))
                {
                    chunk.setInhabitedTime(chunk.getInhabitedTime() + j);

                    ChunkPos chunkpos2 = chunk.getPos();
                    int chunkX = chunkpos2.getXStart();
                    int chunkZ = chunkpos2.getZStart();
                    BlockPos strikePos = this.that.world.adjustPosToNearbyEntity(this.that.world.func_217383_a(chunkX, 0, chunkZ, 15));
                    CommonEventHooks.onWeatherTick(this.that.world, chunkX, chunkZ, strikePos);
                }
            }
        });
    }
}