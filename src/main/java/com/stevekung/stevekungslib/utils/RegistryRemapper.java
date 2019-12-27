package com.stevekung.stevekungslib.utils;

import com.stevekung.stevekungslib.core.SteveKunGLib;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegistryRemapper<T extends IForgeRegistryEntry<T>>
{
    private final RegistryEvent.MissingMappings<T> event;

    public RegistryRemapper(RegistryEvent.MissingMappings<T> event)
    {
        this.event = event;
    }

    public void remapRegistry(String modId, String oldName, T remapObj)
    {
        for (RegistryEvent.MissingMappings.Mapping<T> mappings : this.event.getMappings())
        {
            if (mappings.key.getNamespace().equals(modId) && mappings.key.getPath().equals(oldName))
            {
                mappings.remap(remapObj);
                SteveKunGLib.LOGGER.info("Remapping '" + remapObj.getClass().getSimpleName() + "' from {} to {}", mappings.key, remapObj.getRegistryName());
            }
        }
    }
}