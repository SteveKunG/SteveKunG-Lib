package com.stevekung.stevekungslib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;

@Mixin(FireBlock.class)
public interface InvokerFireBlock
{
    @Invoker
    void invokeSetFlammable(Block block, int i, int j);
}