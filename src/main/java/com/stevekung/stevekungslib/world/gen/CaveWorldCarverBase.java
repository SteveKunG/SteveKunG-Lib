package com.stevekung.stevekungslib.world.gen;

import java.util.BitSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class CaveWorldCarverBase extends WorldCarver<ProbabilityConfig>
{
    private final Set<Block> surfaceBlocks;
    private final Set<Block> subSurfaceBlocks;
    private final IFluidState lava;

    public CaveWorldCarverBase(Set<Block> terrainBlocks, Set<Fluid> terrainFluids, Set<Block> surfaceBlocks, Set<Block> subSurfaceBlocks, IFluidState lava)
    {
        super(ProbabilityConfig::deserialize, 256);
        this.carvableBlocks = terrainBlocks;
        this.carvableFluids = terrainFluids;
        this.surfaceBlocks = surfaceBlocks;
        this.subSurfaceBlocks = subSurfaceBlocks;
        this.lava = lava;
    }

    @Override
    public boolean shouldCarve(Random rand, int chunkX, int chunkZ, ProbabilityConfig config)
    {
        return rand.nextFloat() <= config.probability;
    }

    @Override
    public boolean carveRegion(IChunk chunk, Function<BlockPos, Biome> biomeGetter, Random rand, int seaLevel, int chunkX, int chunkZ, int originalX, int originalZ, BitSet carvingMask, ProbabilityConfig config)
    {
        int i = (this.func_222704_c() * 2 - 1) * 16;
        int j = rand.nextInt(rand.nextInt(rand.nextInt(15) + 1) + 1);

        for (int k = 0; k < j; ++k)
        {
            double d0 = chunkX * 16 + rand.nextInt(16);
            double d1 = rand.nextInt(rand.nextInt(120) + 8);
            double d2 = chunkZ * 16 + rand.nextInt(16);
            int l = 1;

            if (rand.nextInt(4) == 0)
            {
                float f1 = 1.0F + rand.nextFloat() * 6.0F;
                this.addRoom(chunk, biomeGetter, rand.nextLong(), seaLevel, originalX, originalZ, d0, d1, d2, f1, 0.5D, carvingMask);
                l += rand.nextInt(4);
            }

            for (int k1 = 0; k1 < l; ++k1)
            {
                float f = rand.nextFloat() * ((float)Math.PI * 2F);
                float f3 = (rand.nextFloat() - 0.5F) / 4.0F;
                float f2 = this.generateCaveRadius(rand);
                int i1 = i - rand.nextInt(i / 4);
                this.carveTunnel(chunk, biomeGetter, rand.nextLong(), seaLevel, originalX, originalZ, d0, d1, d2, f2, f, f3, 0, i1, 1.0D, carvingMask);
            }
        }
        return true;
    }

    @Override
    protected boolean func_222708_a(double p_222708_1_, double p_222708_3_, double p_222708_5_, int p_222708_7_)
    {
        return p_222708_3_ <= -0.7D || p_222708_1_ * p_222708_1_ + p_222708_3_ * p_222708_3_ + p_222708_5_ * p_222708_5_ >= 1.0D;
    }

    @Override
    protected boolean carveBlock(IChunk chunk, Function<BlockPos, Biome> biomeGetter, BitSet carvingMask, Random rand, BlockPos.Mutable mutablePos1, BlockPos.Mutable mutablePos2, BlockPos.Mutable mutablePos3, int p_222703_7_, int p_222703_8_, int p_222703_9_, int x, int z, int p_222703_12_, int y, int p_222703_14_, AtomicBoolean atomicboolean)
    {
        int i = p_222703_12_ | p_222703_14_ << 4 | y << 8;

        if (carvingMask.get(i))
        {
            return false;
        }
        else
        {
            carvingMask.set(i);
            mutablePos1.setPos(x, y, z);
            BlockState blockstate = chunk.getBlockState(mutablePos1);
            BlockState blockstate1 = chunk.getBlockState(mutablePos2.setPos(mutablePos1).move(Direction.UP));

            if (this.surfaceBlocks.stream().anyMatch(block -> blockstate.getBlock() == block.getBlock()))
            {
                atomicboolean.set(true);
            }

            if (!this.canCarveBlock(blockstate, blockstate1))
            {
                return false;
            }
            else
            {
                if (y < 11)
                {
                    chunk.setBlockState(mutablePos1, this.lava.getBlockState(), false);
                }
                else
                {
                    chunk.setBlockState(mutablePos1, CAVE_AIR, false);

                    if (atomicboolean.get())
                    {
                        mutablePos3.setPos(mutablePos1).move(Direction.DOWN);

                        if (this.subSurfaceBlocks.stream().anyMatch(block -> chunk.getBlockState(mutablePos3).getBlock() == block.getBlock()))
                        {
                            chunk.setBlockState(mutablePos3, biomeGetter.apply(mutablePos1).getSurfaceBuilderConfig().getTop(), false);
                        }
                    }
                }
                return true;
            }
        }
    }

    private float generateCaveRadius(Random rand)
    {
        float f = rand.nextFloat() * 2.0F + rand.nextFloat();

        if (rand.nextInt(10) == 0)
        {
            f *= rand.nextFloat() * rand.nextFloat() * 3.0F + 1.0F;
        }
        return f;
    }

    private void addRoom(IChunk world, Function<BlockPos, Biome> biomeGetter, long seed, int height, int originalX, int originalZ, double x, double y, double z, float p_222723_13_, double p_222723_14_, BitSet bitSet)
    {
        double d0 = 1.5D + MathHelper.sin((float)Math.PI / 2F) * p_222723_13_;
        double d1 = d0 * p_222723_14_;
        this.func_227208_a_(world, biomeGetter, seed, height, originalX, originalZ, x + 1.0D, y, z, d0, d1, bitSet);
    }

    private void carveTunnel(IChunk chunk, Function<BlockPos, Biome> biomeGetter, long seed, int height, int originalX, int originalZ, double x, double y, double z, float radius, float p_222727_14_, float p_222727_15_, int p_222727_16_, int p_222727_17_, double p_222727_18_, BitSet bitSet)
    {
        Random rand = new Random(seed);
        int i = rand.nextInt(p_222727_17_ / 2) + p_222727_17_ / 4;
        boolean flag = rand.nextInt(6) == 0;
        float f = 0.0F;
        float f1 = 0.0F;

        for (int j = 0; j < p_222727_17_; ++j)
        {
            double d0 = 1.5D + MathHelper.sin((float)Math.PI * j / p_222727_17_) * radius;
            double d1 = d0 * p_222727_18_;
            float f2 = MathHelper.cos(p_222727_15_);
            x += MathHelper.cos(p_222727_14_) * f2;
            y += MathHelper.sin(p_222727_15_);
            z += MathHelper.sin(p_222727_14_) * f2;
            p_222727_15_ = p_222727_15_ * (flag ? 0.92F : 0.7F);
            p_222727_15_ = p_222727_15_ + f1 * 0.1F;
            p_222727_14_ += f * 0.1F;
            f1 = f1 * 0.9F;
            f = f * 0.75F;
            f1 = f1 + (rand.nextFloat() - rand.nextFloat()) * rand.nextFloat() * 2.0F;
            f = f + (rand.nextFloat() - rand.nextFloat()) * rand.nextFloat() * 4.0F;

            if (j == i && radius > 1.0F)
            {
                this.carveTunnel(chunk, biomeGetter, rand.nextLong(), height, originalX, originalZ, x, y, z, rand.nextFloat() * 0.5F + 0.5F, p_222727_14_ - (float)Math.PI / 2F, p_222727_15_ / 3.0F, j, p_222727_17_, 1.0D, bitSet);
                this.carveTunnel(chunk, biomeGetter, rand.nextLong(), height, originalX, originalZ, x, y, z, rand.nextFloat() * 0.5F + 0.5F, p_222727_14_ + (float)Math.PI / 2F, p_222727_15_ / 3.0F, j, p_222727_17_, 1.0D, bitSet);
                return;
            }

            if (rand.nextInt(4) != 0)
            {
                if (!this.func_222702_a(originalX, originalZ, x, z, j, p_222727_17_, radius))
                {
                    return;
                }
                this.func_227208_a_(chunk, biomeGetter, seed, height, originalX, originalZ, x, y, z, d0, d1, bitSet);
            }
        }
    }
}