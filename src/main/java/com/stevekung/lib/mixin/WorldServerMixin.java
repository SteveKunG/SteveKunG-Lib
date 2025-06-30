package com.stevekung.lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import com.stevekung.lib.utils.EventHooksCommon;

@Mixin(WorldServer.class)
public class WorldServerMixin
{
    @Inject(
            method = "updateBlocks",
            at = @At(
                    value = "CONSTANT",
                    args = "stringValue=thunder"))
    private void stevekungs_lib$injectWeatherTickEvent(CallbackInfo info, @Local Chunk chunk, @Local(index = 6, ordinal = 1) int chunkX, @Local(index = 6, ordinal = 2) int chunkZ)
    {
        EventHooksCommon.onWeatherTick(WorldServer.class.cast(this), chunk, chunkX, chunkZ);
    }
}