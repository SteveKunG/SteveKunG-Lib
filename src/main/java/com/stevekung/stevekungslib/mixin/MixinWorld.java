package com.stevekung.stevekungslib.mixin;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.stevekung.stevekungslib.utils.IFireBlock;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

@Mixin(World.class)
public abstract class MixinWorld
{
    private final World that = (World) (Object) this;

    /**
     * @reason Currently vanilla doesn't support custom fire when extinguish
     * @author SteveKunG
     */
    @Overwrite
    public boolean extinguishFire(@Nullable PlayerEntity player, BlockPos pos, Direction side)
    {
        pos = pos.offset(side);
        Block block = this.that.getBlockState(pos).getBlock();

        if (block == Blocks.FIRE || block instanceof IFireBlock)
        {
            this.that.playEvent(player, Constants.WorldEvents.FIRE_EXTINGUISH_SOUND, pos, 0);
            this.that.removeBlock(pos, false);
            return true;
        }
        else
        {
            return false;
        }
    }
}