package stevekung.mods.stevekunglib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import stevekung.mods.stevekunglib.utils.IFireBlock;

@Mixin(World.class)
public abstract class WorldMixin
{
    private final World that = (World) (Object) this;

    @Inject(method = "extinguishFire(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)Z", at = @At("HEAD"), cancellable = true)
    private void extinguishFire(EntityPlayer player, BlockPos pos, EnumFacing side, CallbackInfoReturnable info)
    {
        pos = pos.offset(side);
        Block block = this.that.getBlockState(pos).getBlock();

        if (block instanceof IFireBlock)
        {
            this.that.playEvent(player, 1009, pos, 0);
            this.that.setBlockToAir(pos);
            info.setReturnValue(true);
        }
    }
}