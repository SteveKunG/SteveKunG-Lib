package stevekung.mods.stevekungslib.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import stevekung.mods.stevekungslib.utils.enums.HarvestLevel;

public class BlockUtils
{
    public static void setFireBurn(Block block, int encouragement, int flammibility)
    {
        ((BlockFire)Blocks.FIRE).setFireInfo(block, encouragement, flammibility);
    }

    public static void setBlockHarvestLevel(Block block, HarvestLevel harvestLevel, int level)
    {
        //        block.setHarvestLevel(harvestLevel.toString(), level);
    }

    public static void setToolHarvestLevel(Item item, HarvestLevel harvestLevel, int level)
    {
        //        item.setHarvestLevel(harvestLevel.toString(), level);TODO
    }

    public static boolean isFluid(World world, BlockPos pos)
    {
        return world.getBlockState(pos.up()).getMaterial().isLiquid();//TODO
    }
}