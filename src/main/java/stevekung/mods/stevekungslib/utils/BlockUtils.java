package stevekung.mods.stevekungslib.utils;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockUtils
{
    public static void setFireBurn(Block block, int encouragement, int flammibility)
    {
        ((FireBlock)Blocks.FIRE).setFireInfo(block, encouragement, flammibility);
    }

    public static boolean isFluid(World world, BlockPos pos)
    {
        return world.getBlockState(pos.up()).getMaterial().isLiquid();
    }
}