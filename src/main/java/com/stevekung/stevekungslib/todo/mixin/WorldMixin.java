package com.stevekung.stevekungslib.todo.mixin;
//package stevekung.mods.stevekungslib.todo.mixin;
//
//import javax.annotation.Nullable;
//
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Overwrite;
//import org.spongepowered.asm.mixin.Shadow;
//
//import net.minecraft.block.Block;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.init.Blocks;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import stevekung.mods.stevekunglib.utils.IFireBlock;
//
//@Mixin(World.class)
//public abstract class WorldMixin
//{
//    @Shadow
//    public abstract IBlockState getBlockState(BlockPos pos);
//
//    @Shadow
//    public abstract void playEvent(@Nullable EntityPlayer player, int type, BlockPos pos, int data);
//
//    @Shadow
//    public abstract boolean setBlockToAir(BlockPos pos);
//
//    @Overwrite
//    public boolean extinguishFire(@Nullable EntityPlayer player, BlockPos pos, EnumFacing side)
//    {
//        pos = pos.offset(side);
//        Block block = this.getBlockState(pos).getBlock();
//
//        if (block == Blocks.FIRE || block instanceof IFireBlock)
//        {
//            this.playEvent(player, 1009, pos, 0);
//            this.setBlockToAir(pos);
//            return true;
//        }
//        else
//        {
//            return false;
//        }
//    }
//}