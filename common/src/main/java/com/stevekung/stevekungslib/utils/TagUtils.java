package com.stevekung.stevekungslib.utils;

import me.shedaniel.architectury.annotations.ExpectPlatform;
import me.shedaniel.architectury.hooks.TagHooks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

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

    @ExpectPlatform
    public static Tag.Named<Enchantment> createEnchantmentTag(String modid, String name)
    {
        throw new Error();
    }

    @ExpectPlatform
    public static Tag.Named<Potion> createPotionTag(String modid, String name)
    {
        throw new Error();
    }

    @ExpectPlatform
    public static Tag.Named<BlockEntityType<?>> createTileEntityTag(String modid, String name)
    {
        throw new Error();
    }
}