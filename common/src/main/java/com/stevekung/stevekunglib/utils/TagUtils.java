package com.stevekung.stevekunglib.utils;

import dev.architectury.hooks.tags.TagHooks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TagUtils
{
    public static Tag.Named<Block> createBlockTag(String modid, String name)
    {
        return TagHooks.optionalBlock(new ResourceLocation(modid, name));
    }

    public static Tag.Named<Item> createItemTag(String modid, String name)
    {
        return TagHooks.optionalItem(new ResourceLocation(modid, name));
    }
}