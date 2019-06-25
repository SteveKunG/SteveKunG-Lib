package stevekung.mods.stevekungslib.world.gen;

import java.util.BitSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class CanyonWorldCarverBase extends WorldCarver<ProbabilityConfig>
{
    private final float[] field_202536_i = new float[1024];
    private Set<Block> surfaceBlocks;
    private Set<Block> subSurfaceBlocks;
    private IFluidState lava;

    public CanyonWorldCarverBase(Set<Block> terrainBlocks, Set<Fluid> terrainFluids, Set<Block> surfaceBlocks, Set<Block> subSurfaceBlocks, IFluidState lava)
    {
        super(ProbabilityConfig::deserialize, 256);
        this.field_222718_j = terrainBlocks;
        this.field_222719_k = terrainFluids;
        this.surfaceBlocks = surfaceBlocks;
        this.subSurfaceBlocks = subSurfaceBlocks;
        this.lava = lava;
    }

    @Override
    public boolean func_212868_a_(Random rand, int chunkX, int chunkZ, ProbabilityConfig config)
    {
        return rand.nextFloat() <= config.probability;
    }

    @Override
    public boolean func_212867_a_(IChunk world, Random rand, int height, int chunkX, int chunkZ, int originalX, int originalZ, BitSet bitSet, ProbabilityConfig config)
    {
        int i = (this.func_222704_c() * 2 - 1) * 16;
        double d0 = chunkX * 16 + rand.nextInt(16);
        double d1 = rand.nextInt(rand.nextInt(40) + 8) + 20;
        double d2 = chunkZ * 16 + rand.nextInt(16);
        float f = rand.nextFloat() * ((float)Math.PI * 2F);
        float f1 = (rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
        float f2 = (rand.nextFloat() * 2.0F + rand.nextFloat()) * 2.0F;
        int j = i - rand.nextInt(i / 4);
        this.calculateCaveShape(world, rand.nextLong(), height, originalX, originalZ, d0, d1, d2, f2, f, f1, 0, j, 3.0D, bitSet);
        return true;
    }

    @Override
    protected boolean func_222708_a(double p_222708_1_, double p_222708_3_, double p_222708_5_, int p_222708_7_)
    {
        return (p_222708_1_ * p_222708_1_ + p_222708_5_ * p_222708_5_) * this.field_202536_i[p_222708_7_ - 1] + p_222708_3_ * p_222708_3_ / 6.0D >= 1.0D;
    }

    @Override
    protected boolean func_222703_a(IChunk world, BitSet bitSet, Random rand, BlockPos.MutableBlockPos mutablePos1, BlockPos.MutableBlockPos mutablePos2, BlockPos.MutableBlockPos mutablePos3, int p_222703_7_, int p_222703_8_, int p_222703_9_, int x, int z, int p_222703_12_, int y, int p_222703_14_, AtomicBoolean atomicboolean)
    {
        int i = p_222703_12_ | p_222703_14_ << 4 | y << 8;

        if (bitSet.get(i))
        {
            return false;
        }
        else
        {
            bitSet.set(i);
            mutablePos1.setPos(x, y, z);
            BlockState blockstate = world.getBlockState(mutablePos1);
            BlockState blockstate1 = world.getBlockState(mutablePos2.func_189533_g(mutablePos1).move(Direction.UP));

            if (this.surfaceBlocks.stream().anyMatch(block -> blockstate.getBlock() == block.getBlock()))
            {
                atomicboolean.set(true);
            }

            if (!this.func_222707_a(blockstate, blockstate1))
            {
                return false;
            }
            else
            {
                if (y < 11)
                {
                    world.setBlockState(mutablePos1, this.lava.getBlockState(), false);
                }
                else
                {
                    world.setBlockState(mutablePos1, CAVE_AIR, false);

                    if (atomicboolean.get())
                    {
                        mutablePos3.func_189533_g(mutablePos1).move(Direction.DOWN);

                        if (this.subSurfaceBlocks.stream().anyMatch(block -> world.getBlockState(mutablePos3).getBlock() == block.getBlock()))
                        {
                            world.setBlockState(mutablePos3, world.getBiome(mutablePos1).getSurfaceBuilderConfig().getTop(), false);
                        }
                    }
                }
                return true;
            }
        }
    }

    private void calculateCaveShape(IChunk world, long seed, int height, int originalX, int originalZ, double x, double y, double z, float p_222729_13_, float p_222729_14_, float p_222729_15_, int p_222729_16_, int p_222729_17_, double p_222729_18_, BitSet bitSet)
    {
        Random rand = new Random(seed);
        float f = 1.0F;

        for (int i = 0; i < 256; ++i)
        {
            if (i == 0 || rand.nextInt(3) == 0)
            {
                f = 1.0F + rand.nextFloat() * rand.nextFloat();
            }
            this.field_202536_i[i] = f * f;
        }

        float f4 = 0.0F;
        float f1 = 0.0F;

        for (int j = p_222729_16_; j < p_222729_17_; ++j)
        {
            double d0 = 1.5D + MathHelper.sin(j * (float)Math.PI / p_222729_17_) * p_222729_13_;
            double d1 = d0 * p_222729_18_;
            d0 = d0 * (rand.nextFloat() * 0.25D + 0.75D);
            d1 = d1 * (rand.nextFloat() * 0.25D + 0.75D);
            float f2 = MathHelper.cos(p_222729_15_);
            float f3 = MathHelper.sin(p_222729_15_);
            x += MathHelper.cos(p_222729_14_) * f2;
            y += f3;
            z += MathHelper.sin(p_222729_14_) * f2;
            p_222729_15_ = p_222729_15_ * 0.7F;
            p_222729_15_ = p_222729_15_ + f1 * 0.05F;
            p_222729_14_ += f4 * 0.05F;
            f1 = f1 * 0.8F;
            f4 = f4 * 0.5F;
            f1 = f1 + (rand.nextFloat() - rand.nextFloat()) * rand.nextFloat() * 2.0F;
            f4 = f4 + (rand.nextFloat() - rand.nextFloat()) * rand.nextFloat() * 4.0F;

            if (rand.nextInt(4) != 0)
            {
                if (!this.func_222702_a(originalX, originalZ, x, z, j, p_222729_17_, p_222729_13_))
                {
                    return;
                }
                this.func_222705_a(world, seed, height, originalX, originalZ, x, y, z, d0, d1, bitSet);
            }
        }
    }
}