package com.stevekung.stevekungslib.utils;

import com.stevekung.stevekungslib.core.SteveKunGLib;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.MissingMappings.Mapping;

public class BlockItemRemapper
{
    public static void remapBlock(RegistryEvent.MissingMappings<Block> event, String modid, String oldName, Block block)
    {
        for (Mapping<Block> mappings : event.getMappings())
        {
            if (mappings.key.getNamespace().equals(modid) && mappings.key.getPath().equals(oldName))
            {
                mappings.remap(block);
                SteveKunGLib.LOGGER.info("Remapping 'Block' from {} to {}", mappings.key, block.getRegistryName());
            }
        }
    }

    @SuppressWarnings("deprecation")
    public static void remapItem(RegistryEvent.MissingMappings<Item> event, String modid, String oldName, Block block)
    {
        for (Mapping<Item> mappings : event.getMappings())
        {
            if (mappings.key.getNamespace().equals(modid) && mappings.key.getPath().equals(oldName))
            {
                mappings.remap(Item.getItemFromBlock(block));
                SteveKunGLib.LOGGER.info("Remapping 'ItemBlock' from {} to {}", mappings.key, block.getRegistryName());
            }
        }
    }

    public static void remapItem(RegistryEvent.MissingMappings<Item> event, String modid, String oldName, Item item)
    {
        for (Mapping<Item> mappings : event.getMappings())
        {
            if (mappings.key.getNamespace().equals(modid) && mappings.key.getPath().equals(oldName))
            {
                mappings.remap(item);
                SteveKunGLib.LOGGER.info("Remapping 'Item' from {} to {}", mappings.key, item.getRegistryName());
            }
        }
    }
}