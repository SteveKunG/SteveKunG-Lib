package stevekung.mods.stevekungslib.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import stevekung.mods.stevekungslib.core.SteveKunGLib;

public class BlockItemRemapper
{
    public static void remapBlock(RegistryEvent.MissingMappings<Block> event, String modid, String oldName, Block block)
    {
        event.getMappings().forEach(mappings ->
        {
            if (mappings.key.getNamespace().equals(modid) && mappings.key.getPath().equals(oldName))
            {
                mappings.remap(block);
                SteveKunGLib.LOGGER.info("Remapping 'Block' from {} to {}", mappings.key, block.getRegistryName());
            }
        });
    }

    @SuppressWarnings("deprecation")
    public static void remapItem(RegistryEvent.MissingMappings<Item> event, String modid, String oldName, Block block)
    {
        event.getMappings().forEach(mappings ->
        {
            if (mappings.key.getNamespace().equals(modid) && mappings.key.getPath().equals(oldName))
            {
                mappings.remap(Item.getItemFromBlock(block));
                SteveKunGLib.LOGGER.info("Remapping 'ItemBlock' from {} to {}", mappings.key, block.getRegistryName());
            }
        });
    }

    public static void remapItem(RegistryEvent.MissingMappings<Item> event, String modid, String oldName, Item item)
    {
        event.getMappings().forEach(mappings ->
        {
            if (mappings.key.getNamespace().equals(modid) && mappings.key.getPath().equals(oldName))
            {
                mappings.remap(item);
                SteveKunGLib.LOGGER.info("Remapping 'Item' from {} to {}", mappings.key, item.getRegistryName());
            }
        });
    }
}