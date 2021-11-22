package com.stevekung.stevekunglib.forge.utils;

import com.stevekung.stevekunglib.core.SteveKunGLib;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

public record RegistryRemapper<T extends IForgeRegistryEntry<T>>(RegistryEvent.MissingMappings<T> event)
{
    public void remapRegistry(String modId, String oldName, T remapObj)
    {
        for (var mappings : this.event.getMappings(modId))
        {
            if (mappings.key.getNamespace().equals(modId) && mappings.key.getPath().equals(oldName))
            {
                mappings.remap(remapObj);
                SteveKunGLib.LOGGER.info("Remapping '" + remapObj.getClass().getSimpleName() + "' from {} to {}", mappings.key, remapObj.getRegistryName());
            }
        }
    }
}