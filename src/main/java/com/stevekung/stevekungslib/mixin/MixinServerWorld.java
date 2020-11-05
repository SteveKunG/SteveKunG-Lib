package com.stevekung.stevekungslib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.stevekung.stevekungslib.utils.CommonEventHooks;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerWorld;

@Mixin(ServerWorld.class)
public class MixinServerWorld
{
    private final ServerWorld that = (ServerWorld) (Object) this;

    @Inject(method = "tickEnvironment(Lnet/minecraft/world/chunk/Chunk;I)V", at = @At(value = "INVOKE", target = "net/minecraft/world/server/ServerWorld.getProfiler()Lnet/minecraft/profiler/IProfiler;", shift = Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD)
    private void injectWeatherTickEvent(Chunk chunk, int randomTickSpeed, CallbackInfo info, ChunkPos chunkpos, boolean flag, int i, int j)
    {
        CommonEventHooks.onWeatherTick(this.that, this.that.adjustPosToNearbyEntity(this.that.getBlockRandomPos(i, 0, j, 15)));
    }
}