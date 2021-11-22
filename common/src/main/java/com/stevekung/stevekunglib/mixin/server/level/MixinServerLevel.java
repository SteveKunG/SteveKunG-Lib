package com.stevekung.stevekunglib.mixin.server.level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import com.stevekung.stevekunglib.event.WorldEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;

@Mixin(ServerLevel.class)
public class MixinServerLevel
{
    @Inject(method = "tickChunk(Lnet/minecraft/world/level/chunk/LevelChunk;I)V", at = @At(value = "INVOKE", target = "net/minecraft/server/level/ServerLevel.getProfiler()Lnet/minecraft/util/profiling/ProfilerFiller;", shift = Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD)
    private void injectWeatherTickEvent(LevelChunk chunk, int randomTickSpeed, CallbackInfo info, ChunkPos chunkpos, boolean flag, int i, int j)
    {
        WorldEvents.WEATHER_TICK.invoker().tick((ServerLevel) (Object) this, i, j);
    }
}