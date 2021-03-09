package com.stevekung.stevekungslib.world.gen;

import java.util.BitSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class CanyonWorldCarverBase extends WorldCarver<ProbabilityFeatureConfiguration>
{
    private final float[] field_202536_i = new float[1024];
    private final Set<Block> surfaceBlocks;
    private final Set<Block> subSurfaceBlocks;
    private final FluidState lava;

    public CanyonWorldCarverBase(Set<Block> terrainBlocks, Set<Fluid> terrainFluids, Set<Block> surfaceBlocks, Set<Block> subSurfaceBlocks, FluidState lava)
    {
        super(ProbabilityFeatureConfiguration.CODEC, 256);
        this.replaceableBlocks = terrainBlocks;
        this.liquids = terrainFluids;
        this.surfaceBlocks = surfaceBlocks;
        this.subSurfaceBlocks = subSurfaceBlocks;
        this.lava = lava;
    }

    @Override
    public boolean isStartChunk(Random rand, int chunkX, int chunkZ, ProbabilityFeatureConfiguration config)
    {
        return rand.nextFloat() <= config.probability;
    }

    @Override
    public boolean carve(ChunkAccess chunk, Function<BlockPos, Biome> biomeGetter, Random rand, int seaLevel, int chunkX, int chunkZ, int originalX, int originalZ, BitSet carvingMask, ProbabilityFeatureConfiguration config)
    {
        int i = (this.getRange() * 2 - 1) * 16;
        double d0 = chunkX * 16 + rand.nextInt(16);
        double d1 = rand.nextInt(rand.nextInt(40) + 8) + 20;
        double d2 = chunkZ * 16 + rand.nextInt(16);
        float f = rand.nextFloat() * ((float)Math.PI * 2F);
        float f1 = (rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
        float f2 = (rand.nextFloat() * 2.0F + rand.nextFloat()) * 2.0F;
        int j = i - rand.nextInt(i / 4);
        this.carveTunnel(chunk, biomeGetter, rand.nextLong(), seaLevel, originalX, originalZ, d0, d1, d2, f2, f, f1, j, carvingMask);
        return true;
    }

    @Override
    protected boolean skip(double p_222708_1_, double p_222708_3_, double p_222708_5_, int p_222708_7_)
    {
        return (p_222708_1_ * p_222708_1_ + p_222708_5_ * p_222708_5_) * this.field_202536_i[p_222708_7_ - 1] + p_222708_3_ * p_222708_3_ / 6.0D >= 1.0D;
    }

    @Override
    protected boolean carveBlock(ChunkAccess chunk, Function<BlockPos, Biome> biomeGetter, BitSet carvingMask, Random rand, BlockPos.MutableBlockPos mutablePos1, BlockPos.MutableBlockPos mutablePos2, BlockPos.MutableBlockPos mutablePos3, int p_222703_7_, int p_222703_8_, int p_222703_9_, int x, int z, int p_222703_12_, int y, int p_222703_14_, MutableBoolean atomicboolean)
    {
        int i = p_222703_12_ | p_222703_14_ << 4 | y << 8;

        if (carvingMask.get(i))
        {
            return false;
        }
        else
        {
            carvingMask.set(i);
            mutablePos1.set(x, y, z);
            BlockState blockstate = chunk.getBlockState(mutablePos1);
            BlockState blockstate1 = chunk.getBlockState(mutablePos2.set(mutablePos1).move(Direction.UP));

            if (this.surfaceBlocks.stream().anyMatch(block -> blockstate.getBlock() == block))
            {
                atomicboolean.setTrue();
            }

            if (!this.canReplaceBlock(blockstate, blockstate1))
            {
                return false;
            }
            else
            {
                if (y < 11)
                {
                    chunk.setBlockState(mutablePos1, this.lava.createLegacyBlock(), false);
                }
                else
                {
                    chunk.setBlockState(mutablePos1, CAVE_AIR, false);

                    if (atomicboolean.isTrue())
                    {
                        mutablePos3.set(mutablePos1).move(Direction.DOWN);

                        if (this.subSurfaceBlocks.stream().anyMatch(block -> chunk.getBlockState(mutablePos3).getBlock() == block))
                        {
                            chunk.setBlockState(mutablePos3, biomeGetter.apply(mutablePos1).getGenerationSettings().getSurfaceBuilderConfig().getTopMaterial(), false);
                        }
                    }
                }
                return true;
            }
        }
    }

    private void carveTunnel(ChunkAccess chunk, Function<BlockPos, Biome> biomeGetter, long seed, int seaLevel, int originalX, int originalZ, double x, double y, double z, float radius, float p_222729_14_, float p_222729_15_, int maxY, BitSet bitSet)
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

        for (int j = 0; j < maxY; ++j)
        {
            double d0 = 1.5D + Mth.sin(j * (float)Math.PI / maxY) * radius;
            double d1 = d0 * 3.0D;
            d0 = d0 * (rand.nextFloat() * 0.25D + 0.75D);
            d1 = d1 * (rand.nextFloat() * 0.25D + 0.75D);
            float f2 = Mth.cos(p_222729_15_);
            float f3 = Mth.sin(p_222729_15_);
            x += Mth.cos(p_222729_14_) * f2;
            y += f3;
            z += Mth.sin(p_222729_14_) * f2;
            p_222729_15_ = p_222729_15_ * 0.7F;
            p_222729_15_ = p_222729_15_ + f1 * 0.05F;
            p_222729_14_ += f4 * 0.05F;
            f1 = f1 * 0.8F;
            f4 = f4 * 0.5F;
            f1 = f1 + (rand.nextFloat() - rand.nextFloat()) * rand.nextFloat() * 2.0F;
            f4 = f4 + (rand.nextFloat() - rand.nextFloat()) * rand.nextFloat() * 4.0F;

            if (rand.nextInt(4) != 0)
            {
                if (!this.canReach(originalX, originalZ, x, z, j, maxY, radius))
                {
                    return;
                }
                this.carveSphere(chunk, biomeGetter, seed, seaLevel, originalX, originalZ, x, y, z, d0, d1, bitSet);
            }
        }
    }
}