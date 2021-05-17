package com.stevekung.stevekungslib.mixin.server.level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import com.stevekung.stevekungslib.event.WorldEvents;
import com.stevekung.stevekungslib.mixin.InvokerServerLevel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;

@Mixin(ServerLevel.class)
public class MixinServerLevel
{
    private final ServerLevel that = (ServerLevel) (Object) this;

    @Inject(method = "tickChunk(Lnet/minecraft/world/level/chunk/LevelChunk;I)V", at = @At(value = "INVOKE", target = "net/minecraft/server/level/ServerLevel.getProfiler()Lnet/minecraft/util/profiling/ProfilerFiller;", shift = Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD)
    private void injectWeatherTickEvent(LevelChunk chunk, int randomTickSpeed, CallbackInfo info, ChunkPos chunkpos, boolean flag, int i, int j)
    {
        WorldEvents.WEATHER_TICK.invoker().tick(this.that, ((InvokerServerLevel) this.that).invokeFindLightingTargetAround(this.that.getBlockRandomPos(i, 0, j, 15)));
    }
}