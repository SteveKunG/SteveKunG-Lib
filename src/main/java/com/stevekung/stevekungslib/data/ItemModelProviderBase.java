package com.stevekung.stevekungslib.data;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class ItemModelProviderBase extends ItemModelProvider
{
    public ItemModelProviderBase(DataGenerator generator, String modid, ExistingFileHelper helper)
    {
        super(generator, modid, helper);
    }

    protected void parentedBlock(Block block, String model)
    {
        this.getBuilder(block.getRegistryName().getPath()).parent(new ModelFile.UncheckedModelFile(this.modLoc(model)));
    }

    protected void parentedItem(Item item, String model)
    {
        this.getBuilder(item.getRegistryName().getPath()).parent(new ModelFile.UncheckedModelFile(this.modLoc(model)));
    }

    protected void itemGenerated(Block block)
    {
        this.itemGenerated(block, this.itemToString(block));
    }

    protected void itemGenerated(Block block, String texture)
    {
        this.getBuilder(block.getRegistryName().getPath()).parent(this.getExistingFile(this.mcLoc("item/generated"))).texture("layer0", "block/" + texture);
    }

    protected void itemGenerated(Item item)
    {
        this.itemGenerated(item, this.itemToString(item));
    }

    protected void itemGenerated(Item item, String texture)
    {
        this.getBuilder(item.getRegistryName().getPath()).parent(this.getExistingFile(this.mcLoc("item/generated"))).texture("layer0", "item/" + texture);
    }

    protected void spawnEgg(Item item)
    {
        this.getBuilder(item.getRegistryName().getPath()).parent(this.getExistingFile(this.mcLoc("item/template_spawn_egg")));
    }

    protected void itemHandheld(Item item, String texture)
    {
        this.getBuilder(item.getRegistryName().getPath()).parent(this.getExistingFile(this.mcLoc("item/handheld"))).texture("layer0", "item/" + texture);
    }

    protected String itemToString(IItemProvider base)
    {
        return base.asItem().getRegistryName().getPath();
    }
}