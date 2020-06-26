package com.stevekung.stevekungslib.utils;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockUtils
{
    public static void setFireBurn(Block block, int encouragement, int flammibility)
    {
        //((FireBlock)Blocks.FIRE).setFireInfo(block, encouragement, flammibility);TODO
    }

    public static boolean isFluid(World world, BlockPos pos)
    {
        return world.getBlockState(pos.up()).getMaterial().isLiquid();
    }
}