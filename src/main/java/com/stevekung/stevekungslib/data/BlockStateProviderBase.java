package com.stevekung.stevekungslib.data;

import net.minecraft.block.Block;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class BlockStateProviderBase extends BlockStateProvider
{
    private final String modid;

    public BlockStateProviderBase(DataGenerator generator, String modid, ExistingFileHelper helper)
    {
        super(generator, modid, helper);
        this.modid = modid;
    }

    protected void simpleCross(Block block)
    {
        this.simpleCross(block, this.toString(block));
    }

    protected void simpleCross(Block block, String name)
    {
        this.getVariantBuilder(block).partialState().setModels(new ConfiguredModel(this.models().cross(name, this.getBlockTexture(name))));
    }

    protected void simpleDoublePlant(Block block)
    {
        this.getVariantBuilder(block).partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).addModels(new ConfiguredModel(this.models().cross(this.toString(block) + "_bottom", this.getBlockTexture(this.toString(block) + "_bottom")))).partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).addModels(new ConfiguredModel(this.models().cross(this.toString(block) + "_top", this.getBlockTexture(this.toString(block) + "_top"))));
    }

    protected void simpleFluid(FlowingFluidBlock block)
    {
        this.getVariantBuilder(block).partialState().setModels(new ConfiguredModel(this.models().getBuilder(this.toString(block)).texture("particle", "minecraft:" + block.getFluid().getAttributes().getStillTexture().getPath())));
    }

    protected String toString(Block block)
    {
        return block.getRegistryName().getPath();
    }

    protected ResourceLocation getBlockTexture(String name)
    {
        return new ResourceLocation(this.modid, "block/" + name);
    }
}