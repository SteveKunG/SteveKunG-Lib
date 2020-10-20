package com.stevekung.stevekungslib.utils;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.tags.ITag;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeTagHandler;
import net.minecraftforge.registries.ForgeRegistries;

public class TagUtils
{
    public static ITag.INamedTag<Block> createBlockTag(String modid, String name)
    {
        return ForgeTagHandler.makeWrapperTag(ForgeRegistries.BLOCKS, new ResourceLocation(modid, name));
    }

    public static ITag.INamedTag<Item> createItemTag(String modid, String name)
    {
        return ForgeTagHandler.makeWrapperTag(ForgeRegistries.ITEMS, new ResourceLocation(modid, name));
    }

    public static ITag.INamedTag<Enchantment> createEnchantmentTag(String modid, String name)
    {
        return ForgeTagHandler.makeWrapperTag(ForgeRegistries.ENCHANTMENTS, new ResourceLocation(modid, name));
    }

    public static ITag.INamedTag<Potion> createPotionTag(String modid, String name)
    {
        return ForgeTagHandler.makeWrapperTag(ForgeRegistries.POTION_TYPES, new ResourceLocation(modid, name));
    }

    public static ITag.INamedTag<TileEntityType<?>> createTileEntityTag(String modid, String name)
    {
        return ForgeTagHandler.makeWrapperTag(ForgeRegistries.TILE_ENTITIES, new ResourceLocation(modid, name));
    }
}