package com.stevekung.stevekungslib.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.StaticTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TagUtilsImpl
{
    public static Tag.Named<Block> createBlockTag(String modid, String name)
    {
        return StaticTags.create(new ResourceLocation(modid, "block"), TagContainer::getBlocks).bind(name);
    }

    public static Tag.Named<Item> createItemTag(String modid, String name)
    {
        return StaticTags.create(new ResourceLocation(modid, "item"), TagContainer::getItems).bind(name);
    }
}