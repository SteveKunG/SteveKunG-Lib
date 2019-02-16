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

public class WorldCarverBase extends WorldCarver<ProbabilityConfig>
{
    private Set<Block> surfaceBlocks;
    private Set<Block> subSurfaceBlocks;
    private IBlockState lava;

    public WorldCarverBase(Set<Block> terrainBlocks, Set<Fluid> terrainFluids, Set<Block> surfaceBlocks, Set<Block> subSurfaceBlocks, IBlockState lava)
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
    public boolean carve(IWorld world, Random rand, int chunkX, int chunkZ, int var5, int var6, BitSet bitSet, ProbabilityConfig config)
    {
        int var9 = (this.func_202520_b() * 2 - 1) * 16;
        int var10 = rand.nextInt(rand.nextInt(rand.nextInt(15) + 1) + 1);

        for (int var11 = 0; var11 < var10; ++var11)
        {
            double var12 = (double)(chunkX * 16 + rand.nextInt(16));
            double var14 = (double)rand.nextInt(rand.nextInt(120) + 8);
            double var16 = (double)(chunkZ * 16 + rand.nextInt(16));
            int var18 = 1;
            float var21;

            if (rand.nextInt(4) == 0)
            {
                var21 = 1.0F + rand.nextFloat() * 6.0F;
                this.addRoom(world, rand.nextLong(), var5, var6, var12, var14, var16, var21, bitSet);
                var18 += rand.nextInt(4);
            }

            for (int var27 = 0; var27 < var18; ++var27)
            {
                float var20 = rand.nextFloat() * 6.2831855F;
                var21 = (rand.nextFloat() - 0.5F) / 4.0F;
                float var24 = rand.nextFloat() * 2.0F + rand.nextFloat();

                if (rand.nextInt(10) == 0)
                {
                    var24 *= rand.nextFloat() * rand.nextFloat() * 3.0F + 1.0F;
                }

                int var25 = var9 - rand.nextInt(var9 / 4);
                this.addTunnel(world, rand.nextLong(), var5, var6, var12, var14, var16, var24, var20, var21, 0, var25, bitSet);
            }
        }
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
            else
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
                                double var41 = ((double)var40 - 0.5D - var8) / var14;

                                if (var41 > -0.7D && var33 * var33 + var41 * var41 + var37 * var37 < 1.0D)
                                {
                                    int var43 = var31 | var35 << 4 | var40 << 8;

                                    if (!bitSet.get(var43))
                                    {
                                        bitSet.set(var43);
                                        var28.setPos(var32, var40, var36);
                                        IBlockState surfaceBlock = world.getBlockState(var28);
                                        IBlockState var45 = world.getBlockState(var29.setPos(var28).move(EnumFacing.UP));

                                        if (this.surfaceBlocks.stream().anyMatch(block -> surfaceBlock.getBlock() == block.getBlock()))
                                        {
                                            var39 = true;
                                        }

                                        if (this.isTargetSafeFromFalling(surfaceBlock, var45))
                                        {
                                            if (var40 < 11)
                                            {
                                                world.setBlockState(var28, this.lava, 2);
                                            }
                                            else
                                            {
                                                world.setBlockState(var28, DEFAULT_CAVE_AIR, 2);

                                                if (var39)
                                                {
                                                    var30.setPos(var28).move(EnumFacing.DOWN);

                                                    if (this.subSurfaceBlocks.stream().anyMatch(block -> world.getBlockState(var30).getBlock().getBlock() == block.getBlock()))
                                                    {
                                                        IBlockState var46 = world.getBiome(var28).getSurfaceBuilderConfig().getTop();
                                                        world.setBlockState(var30, var46, 2);
                                                    }
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
        }
        else
        {
            return false;
        }
    }

    private void addRoom(IWorld world, long seed, int var4, int var5, double var6, double var8, double var10, float var12, BitSet bitSet)
    {
        double var16 = 1.5D + (double)(MathHelper.sin(1.5707964F) * var12);
        double var18 = var16 * 0.5D;
        this.carveAtTarget(world, seed, var4, var5, var6 + 1.0D, var8, var10, var16, var18, bitSet);
    }

    private void addTunnel(IWorld world, long seed, int var4, int var5, double var6, double var8, double var10, float var12, float var13, float var14, int var15, int var16, BitSet bitSet)
    {
        Random var20 = new Random(seed);
        int var21 = var20.nextInt(var16 / 2) + var16 / 4;
        boolean var22 = var20.nextInt(6) == 0;
        float var23 = 0.0F;
        float var24 = 0.0F;

        for (int var25 = var15; var25 < var16; ++var25)
        {
            double var26 = 1.5D + (double)(MathHelper.sin(3.1415927F * (float)var25 / (float)var16) * var12);
            double var28 = var26 * 1.0D;
            float var30 = MathHelper.cos(var14);
            var6 += (double)(MathHelper.cos(var13) * var30);
            var8 += (double)MathHelper.sin(var14);
            var10 += (double)(MathHelper.sin(var13) * var30);
            var14 *= var22 ? 0.92F : 0.7F;
            var14 += var24 * 0.1F;
            var13 += var23 * 0.1F;
            var24 *= 0.9F;
            var23 *= 0.75F;
            var24 += (var20.nextFloat() - var20.nextFloat()) * var20.nextFloat() * 2.0F;
            var23 += (var20.nextFloat() - var20.nextFloat()) * var20.nextFloat() * 4.0F;

            if (var25 == var21 && var12 > 1.0F)
            {
                this.addTunnel(world, var20.nextLong(), var4, var5, var6, var8, var10, var20.nextFloat() * 0.5F + 0.5F, var13 - 1.5707964F, var14 / 3.0F, var25, var16, bitSet);
                this.addTunnel(world, var20.nextLong(), var4, var5, var6, var8, var10, var20.nextFloat() * 0.5F + 0.5F, var13 + 1.5707964F, var14 / 3.0F, var25, var16, bitSet);
                return;
            }

            if (var20.nextInt(4) != 0)
            {
                if (!this.isWithinGenerationDepth(var4, var5, var6, var10, var25, var16, var12))
                {
                    return;
                }
                this.carveAtTarget(world, seed, var4, var5, var6, var8, var10, var26, var28, bitSet);
            }
        }
    }
}