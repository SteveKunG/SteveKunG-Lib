package com.stevekung.stevekungslib.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class ItemModelProviderBase extends ItemModelProvider
{
    public ItemModelProviderBase(DataGenerator generator, String modid, ExistingFileHelper helper)
    {
        super(generator, modid, helper);
    }

    protected ItemModelBuilder parentedBlock(Block block)
    {
        return this.getBuilder(block.getRegistryName().getPath()).parent(new ModelFile.UncheckedModelFile(this.modLoc("block/" + block.getRegistryName().getPath())));
    }

    protected ItemModelBuilder parentedBlock(Block block, String model)
    {
        return this.getBuilder(block.getRegistryName().getPath()).parent(new ModelFile.UncheckedModelFile(this.modLoc(model)));
    }

    protected ItemModelBuilder parentedBlock(Block block, ResourceLocation resource)
    {
        return this.getBuilder(block.getRegistryName().getPath()).parent(new ModelFile.UncheckedModelFile(resource));
    }

    protected ItemModelBuilder parentedItem(Item item, String model)
    {
        return this.getBuilder(item.getRegistryName().getPath()).parent(new ModelFile.UncheckedModelFile(this.modLoc(model)));
    }

    protected void itemGenerated(Block block)
    {
        this.itemGenerated(block, this.toString(block));
    }

    protected void itemGenerated(Block block, String texture)
    {
        this.getBuilder(block.getRegistryName().getPath()).parent(this.getExistingFile(this.mcLoc("item/generated"))).texture("layer0", "block/" + texture);
    }

    protected void itemGenerated(Item item)
    {
        this.itemGenerated(item, this.toString(item));
    }

    protected void itemGenerated(Item item, String texture)
    {
        this.getBuilder(item.getRegistryName().getPath()).parent(this.getExistingFile(this.mcLoc("item/generated"))).texture("layer0", "item/" + texture);
    }

    protected ItemModelBuilder itemGenerated(Item item, ResourceLocation model)
    {
        return this.getBuilder(item.getRegistryName().getPath()).parent(this.getExistingFile(model));
    }

    protected void spawnEgg(Item item)
    {
        this.getBuilder(item.getRegistryName().getPath()).parent(this.getExistingFile(this.mcLoc("item/template_spawn_egg")));
    }

    protected void itemHandheld(Item item, String texture)
    {
        this.getBuilder(item.getRegistryName().getPath()).parent(this.getExistingFile(this.mcLoc("item/handheld"))).texture("layer0", "item/" + texture);
    }

    protected String toString(ItemLike base)
    {
        return base.asItem().getRegistryName().getPath();
    }
}