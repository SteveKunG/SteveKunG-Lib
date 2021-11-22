package com.stevekung.stevekunglib.forge.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class BlockStateProviderBase extends BlockStateProvider
{
    public BlockStateProviderBase(DataGenerator generator, String modid, ExistingFileHelper helper)
    {
        super(generator, modid, helper);
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

    protected void simpleFluid(LiquidBlock block)
    {
        this.getVariantBuilder(block).partialState().setModels(new ConfiguredModel(this.models().getBuilder(this.toString(block)).texture("particle", "minecraft:" + block.getFluid().getAttributes().getStillTexture().getPath())));
    }

    protected void simpleTorch(Block block, Block wallBlock)
    {
        this.simpleBlock(block, this.models().torch(this.toString(block), this.modLoc("block/" + this.toString(block))));
        this.horizontalBlock(wallBlock, this.models().torchWall(this.toString(wallBlock), this.modLoc("block/" + this.toString(block))), 90);
    }

    protected void simpleCake(Block block)
    {
        this.getVariantBuilder(block).forAllStates(state ->
        {
            var slice = state.getValue(BlockStateProperties.BITES);
            var model = slice > 0 ? this.toString(block) + "_slice" + slice : this.toString(block);
            return ConfiguredModel.builder().modelFile(this.models().getExistingFile(this.modLoc("block/" + model))).build();
        });
    }

    protected String toString(Block block)
    {
        return block.getRegistryName().getPath();
    }

    protected ResourceLocation getBlockTexture(String name)
    {
        return this.modLoc("block/" + name);
    }

    protected ResourceLocation getGeneratedModel(Block block)
    {
        return this.models().generatedModels.get(this.modLoc("block/" + this.toString(block))).getLocation();
    }
}