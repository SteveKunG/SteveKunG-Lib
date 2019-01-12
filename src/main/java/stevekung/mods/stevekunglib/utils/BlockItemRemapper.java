package stevekung.mods.stevekunglib.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

public class BlockItemRemapper
{
    public static void remapBlock(RegistryEvent.MissingMappings<Block> event, String modid, String oldName, Block block)
    {
        event.getMappings().forEach(mappings ->
        {
            if (mappings.key.getNamespace().equals(modid) && mappings.key.getPath().equals(oldName))
            {
                mappings.remap(block);
                LoggerSL.info("Remapping 'Block' from {} to {}", mappings.key, block.getRegistryName());
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
                LoggerSL.info("Remapping 'ItemBlock' from {} to {}", mappings.key, block.getRegistryName());
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
                LoggerSL.info("Remapping 'Item' from {} to {}", mappings.key, item.getRegistryName());
            }
        });
    }
}