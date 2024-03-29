package stevekung.mods.stevekunglib.world.gen;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;

public class MapGenRavineBase extends MapGenBase
{
    protected static final IBlockState FLOWING_LAVA = Blocks.FLOWING_LAVA.getDefaultState();
    private IBlockState top = Blocks.GRASS.getDefaultState();
    private final IBlockState lava;
    private Set<Block> topBlock = new HashSet<>();
    private final Set<Block> digBlock;
    private final Set<Block> fluidBlock;
    private final float[] rs = new float[1024];

    public MapGenRavineBase(Set<Block> topBlock, IBlockState lava, Set<Block> digBlock, Set<Block> fluidBlock)
    {
        this.topBlock = topBlock;
        this.lava = lava;
        this.digBlock = digBlock;
        this.fluidBlock = fluidBlock;
    }

    public MapGenRavineBase(IBlockState top, IBlockState lava, Set<Block> digBlock)
    {
        this(top, lava, digBlock, new HashSet<>());
    }

    public MapGenRavineBase(IBlockState top, IBlockState lava, Set<Block> digBlock, Set<Block> fluidBlock)
    {
        this.top = top;
        this.lava = lava;
        this.digBlock = digBlock;
        this.fluidBlock = fluidBlock;
    }

    @Override
    protected void recursiveGenerate(World world, int chunkX, int chunkZ, int originalX, int originalZ, ChunkPrimer primer)
    {
        if (this.rand.nextInt(50) == 0)
        {
            double d0 = chunkX * 16 + this.rand.nextInt(16);
            double d1 = this.rand.nextInt(this.rand.nextInt(40) + 8) + 20;
            double d2 = chunkZ * 16 + this.rand.nextInt(16);

            for (int j = 0; j < 1; ++j)
            {
                float f = this.rand.nextFloat() * ((float)Math.PI * 2F);
                float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
                float f2 = (this.rand.nextFloat() * 2.0F + this.rand.nextFloat()) * 2.0F;
                this.addTunnel(this.rand.nextLong(), originalX, originalZ, primer, d0, d1, d2, f2, f, f1, 0, 0, 3.0D);
            }
        }
    }

    protected void addTunnel(long seed, int chunkX, int chunkZ, ChunkPrimer primer, double p_180707_6_, double p_180707_8_, double p_180707_10_, float p_180707_12_, float p_180707_13_, float p_180707_14_, int p_180707_15_, int p_180707_16_, double p_180707_17_)
    {
        Random random = new Random(seed);
        double d0 = chunkX * 16 + 8;
        double d1 = chunkZ * 16 + 8;
        float f = 0.0F;
        float f1 = 0.0F;

        if (p_180707_16_ <= 0)
        {
            int i = this.range * 16 - 16;
            p_180707_16_ = i - random.nextInt(i / 4);
        }

        boolean flag1 = false;

        if (p_180707_15_ == -1)
        {
            p_180707_15_ = p_180707_16_ / 2;
            flag1 = true;
        }

        float f2 = 1.0F;

        for (int j = 0; j < 256; ++j)
        {
            if (j == 0 || random.nextInt(3) == 0)
            {
                f2 = 1.0F + random.nextFloat() * random.nextFloat();
            }
            this.rs[j] = f2 * f2;
        }

        for (; p_180707_15_ < p_180707_16_; ++p_180707_15_)
        {
            double d9 = 1.5D + MathHelper.sin(p_180707_15_ * (float)Math.PI / p_180707_16_) * p_180707_12_;
            double d2 = d9 * p_180707_17_;
            d9 = d9 * (random.nextFloat() * 0.25D + 0.75D);
            d2 = d2 * (random.nextFloat() * 0.25D + 0.75D);
            float f3 = MathHelper.cos(p_180707_14_);
            float f4 = MathHelper.sin(p_180707_14_);
            p_180707_6_ += MathHelper.cos(p_180707_13_) * f3;
            p_180707_8_ += f4;
            p_180707_10_ += MathHelper.sin(p_180707_13_) * f3;
            p_180707_14_ = p_180707_14_ * 0.7F;
            p_180707_14_ = p_180707_14_ + f1 * 0.05F;
            p_180707_13_ += f * 0.05F;
            f1 = f1 * 0.8F;
            f = f * 0.5F;
            f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            f = f + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;

            if (flag1 || random.nextInt(4) != 0)
            {
                double d3 = p_180707_6_ - d0;
                double d4 = p_180707_10_ - d1;
                double d5 = p_180707_16_ - p_180707_15_;
                double d6 = p_180707_12_ + 2.0F + 16.0F;

                if (d3 * d3 + d4 * d4 - d5 * d5 > d6 * d6)
                {
                    return;
                }

                if (p_180707_6_ >= d0 - 16.0D - d9 * 2.0D && p_180707_10_ >= d1 - 16.0D - d9 * 2.0D && p_180707_6_ <= d0 + 16.0D + d9 * 2.0D && p_180707_10_ <= d1 + 16.0D + d9 * 2.0D)
                {
                    int k2 = MathHelper.floor(p_180707_6_ - d9) - chunkX * 16 - 1;
                    int k = MathHelper.floor(p_180707_6_ + d9) - chunkX * 16 + 1;
                    int l2 = MathHelper.floor(p_180707_8_ - d2) - 1;
                    int l = MathHelper.floor(p_180707_8_ + d2) + 1;
                    int i3 = MathHelper.floor(p_180707_10_ - d9) - chunkZ * 16 - 1;
                    int i1 = MathHelper.floor(p_180707_10_ + d9) - chunkZ * 16 + 1;

                    if (k2 < 0)
                    {
                        k2 = 0;
                    }
                    if (k > 16)
                    {
                        k = 16;
                    }
                    if (l2 < 1)
                    {
                        l2 = 1;
                    }
                    if (l > 248)
                    {
                        l = 248;
                    }
                    if (i3 < 0)
                    {
                        i3 = 0;
                    }
                    if (i1 > 16)
                    {
                        i1 = 16;
                    }

                    boolean flag2 = false;

                    for (int j1 = k2; !flag2 && j1 < k; ++j1)
                    {
                        for (int k1 = i3; !flag2 && k1 < i1; ++k1)
                        {
                            for (int l1 = l + 1; !flag2 && l1 >= l2 - 1; --l1)
                            {
                                if (l1 >= 0 && l1 < 256)
                                {
                                    if (this.isOceanBlock(primer, j1, l1, k1, chunkX, chunkZ))
                                    {
                                        flag2 = true;
                                    }
                                    if (l1 != l2 - 1 && j1 != k2 && j1 != k - 1 && k1 != i3 && k1 != i1 - 1)
                                    {
                                        l1 = l2;
                                    }
                                }
                            }
                        }
                    }

                    if (!flag2)
                    {
                        for (int j3 = k2; j3 < k; ++j3)
                        {
                            double d10 = (j3 + chunkX * 16 + 0.5D - p_180707_6_) / d9;

                            for (int i2 = i3; i2 < i1; ++i2)
                            {
                                double d7 = (i2 + chunkZ * 16 + 0.5D - p_180707_10_) / d9;
                                boolean flag = false;

                                if (d10 * d10 + d7 * d7 < 1.0D)
                                {
                                    for (int j2 = l; j2 > l2; --j2)
                                    {
                                        double d8 = (j2 - 1 + 0.5D - p_180707_8_) / d2;

                                        if ((d10 * d10 + d7 * d7) * this.rs[j2 - 1] + d8 * d8 / 6.0D < 1.0D)
                                        {
                                            if (this.isTopBlock(primer, j3, j2, i2, chunkX, chunkZ))
                                            {
                                                flag = true;
                                            }
                                            this.digBlock(primer, j3, j2, i2, chunkX, chunkZ, flag);
                                        }
                                    }
                                }
                            }
                        }
                        if (flag1)
                        {
                            break;
                        }
                    }
                }
            }
        }
    }

    private boolean isOceanBlock(ChunkPrimer primer, int x, int y, int z, int chunkX, int chunkZ)
    {
        Block block = primer.getBlockState(x, y, z).getBlock();

        if (this.fluidBlock.isEmpty())
        {
            return false;
        }
        else
        {
            for (Block fluidBlock : this.fluidBlock)
            {
                if (fluidBlock.equals(block))
                {
                    return true;
                }
            }
        }
        return block == Blocks.FLOWING_WATER || block == Blocks.WATER;
    }

    private boolean isTopBlock(ChunkPrimer primer, int x, int y, int z, int chunkX, int chunkZ)
    {
        IBlockState state = primer.getBlockState(x, y, z);

        if (this.topBlock.isEmpty())
        {
            return false;
        }
        else
        {
            for (Block topBlock : this.topBlock)
            {
                if (topBlock.equals(state.getBlock()))
                {
                    return true;
                }
            }
        }
        return state.getBlock() == this.top.getBlock();
    }

    private void digBlock(ChunkPrimer primer, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop)
    {
        IBlockState state = primer.getBlockState(x, y, z);
        Biome biome = this.world.getBiome(new BlockPos(x + chunkX * 16, 0, z + chunkZ * 16));
        IBlockState top = biome.topBlock;
        IBlockState filler = biome.fillerBlock;

        if (this.canReplaceBlock(state) || state.getBlock() == top.getBlock() || state.getBlock() == filler.getBlock())
        {
            if (y - 1 < 10)
            {
                primer.setBlockState(x, y, z, this.lava);
            }
            else
            {
                primer.setBlockState(x, y, z, Blocks.AIR.getDefaultState());

                if (foundTop && primer.getBlockState(x, y - 1, z).getBlock() == filler.getBlock())
                {
                    primer.setBlockState(x, y - 1, z, top.getBlock().getDefaultState());
                }
            }
        }
    }

    private boolean canReplaceBlock(IBlockState state)
    {
        for (Block block : this.digBlock)
        {
            if (state.getBlock().equals(block))
            {
                return true;
            }
        }
        return state.getBlock() == this.top.getBlock();
    }
}