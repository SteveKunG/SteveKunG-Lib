package com.stevekung.stevekungslib.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;

public class BlockUtils
{
    public static void setFireBurn(Block block, int encouragement, int flammability)
    {
        ((FireBlock) Blocks.FIRE).setFlammable(block, encouragement, flammability);
    }

    public static boolean isFluid(Level level, BlockPos pos)
    {
        return level.getBlockState(pos.above()).getMaterial().isLiquid();
    }
}