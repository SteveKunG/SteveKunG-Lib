package stevekung.mods.stevekungslib.world.gen;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

import java.util.BitSet;
import java.util.Random;
import java.util.Set;

public class CanyonWorldCarverBase extends WorldCarver<ProbabilityConfig>
{
    private final float[] field_202536_i = new float[1024];
    private Set<Block> surfaceBlocks;
    private Set<Block> subSurfaceBlocks;
    private IBlockState lava;

    public CanyonWorldCarverBase(Set<Block> terrainBlocks, Set<Fluid> terrainFluids, Set<Block> surfaceBlocks, Set<Block> subSurfaceBlocks, IBlockState lava)
    {
        this.terrainBlocks = terrainBlocks;
        this.terrainFluids = terrainFluids;
        this.surfaceBlocks = surfaceBlocks;
        this.subSurfaceBlocks = subSurfaceBlocks;
        this.lava = lava;
    }

    @Override
    public boolean func_212246_a(IBlockReader reader, Random rand, int chunkX, int chunkZ, ProbabilityConfig config)
    {
        return rand.nextFloat() <= config.probability;
    }

    @Override
    public boolean carve(IWorld world, Random rand, int chunkX, int chunkZ, int originalX, int originalZ, BitSet bitSet, ProbabilityConfig config)
    {
        int var9 = (this.func_202520_b() * 2 - 1) * 16;
        double var10 = (double)(chunkX * 16 + rand.nextInt(16));
        double var12 = (double)(rand.nextInt(rand.nextInt(40) + 8) + 20);
        double var14 = (double)(chunkZ * 16 + rand.nextInt(16));
        float var16 = rand.nextFloat() * 6.2831855F;
        float var17 = (rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
        float var20 = (rand.nextFloat() * 2.0F + rand.nextFloat()) * 2.0F;
        int var21 = var9 - rand.nextInt(var9 / 4);
        this.func_202535_a(world, rand.nextLong(), originalX, originalZ, var10, var12, var14, var20, var16, var17, var21, bitSet);
        return true;
    }

    @Override
    protected boolean carveAtTarget(IWorld world, long seed, int chunkX, int chunkZ, double var6, double var8, double var10, double var12, double var14, BitSet bitSet)
    {
        double var17 = (double)(chunkX * 16 + 8);
        double var19 = (double)(chunkZ * 16 + 8);

        if (var6 >= var17 - 16.0D - var12 * 2.0D && var10 >= var19 - 16.0D - var12 * 2.0D && var6 <= var17 + 16.0D + var12 * 2.0D && var10 <= var19 + 16.0D + var12 * 2.0D)
        {
            int var21 = Math.max(MathHelper.floor(var6 - var12) - chunkX * 16 - 1, 0);
            int var22 = Math.min(MathHelper.floor(var6 + var12) - chunkX * 16 + 1, 16);
            int var23 = Math.max(MathHelper.floor(var8 - var14) - 1, 1);
            int var24 = Math.min(MathHelper.floor(var8 + var14) + 1, 248);
            int var25 = Math.max(MathHelper.floor(var10 - var12) - chunkZ * 16 - 1, 0);
            int var26 = Math.min(MathHelper.floor(var10 + var12) - chunkZ * 16 + 1, 16);

            if (this.doesAreaHaveFluids(world, chunkX, chunkZ, var21, var22, var23, var24, var25, var26))
            {
                return false;
            }
            else if (var21 <= var22 && var23 <= var24 && var25 <= var26)
            {
                boolean var27 = false;
                MutableBlockPos var28 = new MutableBlockPos();
                MutableBlockPos var29 = new MutableBlockPos();
                MutableBlockPos var30 = new MutableBlockPos();

                for (int var31 = var21; var31 < var22; ++var31)
                {
                    int var32 = var31 + chunkX * 16;
                    double var33 = ((double)var32 + 0.5D - var6) / var12;

                    for (int var35 = var25; var35 < var26; ++var35)
                    {
                        int var36 = var35 + chunkZ * 16;
                        double var37 = ((double)var36 + 0.5D - var10) / var12;

                        if (var33 * var33 + var37 * var37 < 1.0D)
                        {
                            boolean var39 = false;

                            for (int var40 = var24; var40 > var23; --var40)
                            {
                                double var41 = ((double)(var40 - 1) + 0.5D - var8) / var14;

                                if ((var33 * var33 + var37 * var37) * (double)this.field_202536_i[var40 - 1] + var41 * var41 / 6.0D < 1.0D)
                                {
                                    int var43 = var31 | var35 << 4 | var40 << 8;

                                    if (!bitSet.get(var43))
                                    {
                                        bitSet.set(var43);
                                        var28.setPos(var32, var40, var36);
                                        IBlockState surfaceBlock = world.getBlockState(var28);
                                        var29.setPos(var28).move(EnumFacing.UP);
                                        var30.setPos(var28).move(EnumFacing.DOWN);
                                        IBlockState var45 = world.getBlockState(var29);

                                        if (this.surfaceBlocks.stream().anyMatch(block -> surfaceBlock.getBlock() == block.getBlock()))
                                        {
                                            var39 = true;
                                        }

                                        if (this.isTargetSafeFromFalling(surfaceBlock, var45))
                                        {
                                            if (var40 - 1 < 10)
                                            {
                                                world.setBlockState(var28, this.lava, 2);
                                            }
                                            else
                                            {
                                                world.setBlockState(var28, DEFAULT_CAVE_AIR, 2);

                                                if (var39 && this.subSurfaceBlocks.stream().anyMatch(block -> world.getBlockState(var30).getBlock() == block.getBlock()))
                                                {
                                                    world.setBlockState(var30, world.getBiome(var28).getSurfaceBuilderConfig().getTop(), 2);
                                                }
                                            }
                                            var27 = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return var27;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    private void func_202535_a(IWorld world, long seed, int originalX, int originalZ, double var6, double var8, double var10, float var12, float var13, float var14, int var16, BitSet bitSet)
    {
        Random rand = new Random(seed);
        float var21 = 1.0F;

        for (int var22 = 0; var22 < 256; ++var22)
        {
            if (var22 == 0 || rand.nextInt(3) == 0)
            {
                var21 = 1.0F + rand.nextFloat() * rand.nextFloat();
            }
            this.field_202536_i[var22] = var21 * var21;
        }

        float var31 = 0.0F;
        float var23 = 0.0F;

        for (int var24 = 0; var24 < var16; ++var24)
        {
            double var25 = 1.5D + (double)(MathHelper.sin((float)var24 * 3.1415927F / (float)var16) * var12);
            double var27 = var25 * 3.0D;
            var25 *= (double)rand.nextFloat() * 0.25D + 0.75D;
            var27 *= (double)rand.nextFloat() * 0.25D + 0.75D;
            float var29 = MathHelper.cos(var14);
            float var30 = MathHelper.sin(var14);
            var6 += (double)(MathHelper.cos(var13) * var29);
            var8 += (double)var30;
            var10 += (double)(MathHelper.sin(var13) * var29);
            var14 *= 0.7F;
            var14 += var23 * 0.05F;
            var13 += var31 * 0.05F;
            var23 *= 0.8F;
            var31 *= 0.5F;
            var23 += (rand.nextFloat() - rand.nextFloat()) * rand.nextFloat() * 2.0F;
            var31 += (rand.nextFloat() - rand.nextFloat()) * rand.nextFloat() * 4.0F;

            if (rand.nextInt(4) != 0)
            {
                if (!this.isWithinGenerationDepth(originalX, originalZ, var6, var10, var24, var16, var12))
                {
                    return;
                }
                this.carveAtTarget(world, seed, originalX, originalZ, var6, var8, var10, var25, var27, bitSet);
            }
        }
    }
}