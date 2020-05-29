package com.stevekung.stevekungslib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.stevekung.stevekungslib.utils.IFireBlock;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

@Mixin(World.class)
public abstract class MixinWorld
{
    private final World that = (World) (Object) this;

    @Inject(method = "extinguishFire(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/Direction;)Z", cancellable = true, at = @At("HEAD"))
    private void extinguishCustomFire(PlayerEntity player, BlockPos pos, Direction side, CallbackInfoReturnable<Boolean> info)
    {
        pos = pos.offset(side);

        if (this.that.getBlockState(pos).getBlock() instanceof IFireBlock)
        {
            this.that.playEvent(player, Constants.WorldEvents.FIRE_EXTINGUISH_SOUND, pos, 0);
            this.that.removeBlock(pos, false);
            info.setReturnValue(true);
        }
    }
}