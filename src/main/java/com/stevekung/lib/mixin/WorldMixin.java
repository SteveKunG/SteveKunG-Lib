package com.stevekung.lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import com.stevekung.lib.utils.IFireBlock;

import javax.annotation.Nullable;

@Mixin(World.class)
public class WorldMixin
{
    @Inject(
            method = "extinguishFire",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/world/World.getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;"
            ),
            cancellable = true)
    private void stevekung_lib$extinguishFire(@Nullable EntityPlayer player, BlockPos pos, EnumFacing side, CallbackInfoReturnable<Boolean> info)
    {
        World world = World.class.cast(this);

        if (world.getBlockState(pos).getBlock() instanceof IFireBlock)
        {
            world.playEvent(player, 1009, pos, 0);
            world.setBlockToAir(pos);
            info.setReturnValue(true);
        }
    }
}