package com.stevekung.stevekungslib.utils;

import com.stevekung.stevekungslib.mixin.InvokerFireBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class BlockUtils
{
    public static void setFireBurn(Block block, int encouragement, int flammability)
    {
        ((InvokerFireBlock)Blocks.FIRE).invokeSetFlammable(block, encouragement, flammability);
    }

    public static boolean isFluid(Level level, BlockPos pos)
    {
        return level.getBlockState(pos.above()).getMaterial().isLiquid();
    }
}