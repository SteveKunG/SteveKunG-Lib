package com.stevekung.stevekungslib.utils;

import net.minecraftforge.event.RegistryEvent;

public class AbstractRegistryInitializer
{
    public void init(RegistryEvent.Register<?> event, LoggerBase logger)
    {
        logger.info("Registering objects to {}", event.getRegistry().getRegistrySuperType().getSimpleName());
    }
}