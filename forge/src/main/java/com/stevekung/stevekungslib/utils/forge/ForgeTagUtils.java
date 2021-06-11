package com.stevekung.stevekungslib.utils.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.ForgeTagHandler;
import net.minecraftforge.registries.ForgeRegistries;

public class ForgeTagUtils
{
    public static Tag.Named<Enchantment> createEnchantmentTag(String modid, String name)
    {
        return ForgeTagHandler.makeWrapperTag(ForgeRegistries.ENCHANTMENTS, new ResourceLocation(modid, name));
    }

    public static Tag.Named<Potion> createPotionTag(String modid, String name)
    {
        return ForgeTagHandler.makeWrapperTag(ForgeRegistries.POTION_TYPES, new ResourceLocation(modid, name));
    }

    public static Tag.Named<BlockEntityType<?>> createTileEntityTag(String modid, String name)
    {
        return ForgeTagHandler.makeWrapperTag(ForgeRegistries.TILE_ENTITIES, new ResourceLocation(modid, name));
    }
}