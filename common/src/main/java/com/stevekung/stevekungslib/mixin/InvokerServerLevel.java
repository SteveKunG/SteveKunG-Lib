package com.stevekung.stevekungslib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

@Mixin(ServerLevel.class)
public interface InvokerServerLevel
{
    @Invoker
    BlockPos invokeFindLightingTargetAround(BlockPos blockPos);
}