package stevekung.mods.stevekunglib.mixin;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import stevekung.mods.stevekunglib.utils.IFireBlock;

@Mixin(World.class)
public abstract class WorldMixin
{
    private final World that = (World) (Object) this;

    @Overwrite
    public boolean extinguishFire(@Nullable EntityPlayer player, BlockPos pos, EnumFacing side)
    {
        pos = pos.offset(side);
        Block block = this.that.getBlockState(pos).getBlock();

        if (block == Blocks.FIRE || block instanceof IFireBlock)
        {
            this.that.playEvent(player, 1009, pos, 0);
            this.that.setBlockToAir(pos);
            return true;
        }
        else
        {
            return false;
        }
    }
}