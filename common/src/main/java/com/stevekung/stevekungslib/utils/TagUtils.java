package com.stevekung.stevekungslib.utils;

import me.shedaniel.architectury.hooks.TagHooks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TagUtils
{
    public static Tag.Named<Block> createBlockTag(String modid, String name)
    {
        return TagHooks.getBlockOptional(new ResourceLocation(modid, name));
    }

    public static Tag.Named<Item> createItemTag(String modid, String name)
    {
        return TagHooks.getItemOptional(new ResourceLocation(modid, name));
    }
}