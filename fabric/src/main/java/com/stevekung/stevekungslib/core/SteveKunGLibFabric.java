package com.stevekung.stevekungslib.core;

import com.stevekung.stevekungslib.config.ConfigHandlerLib;
import net.fabricmc.api.ModInitializer;

public class SteveKunGLibFabric implements ModInitializer
{
    public static final ConfigHandlerLib CONFIG = new ConfigHandlerLib();

    @Override
    public void onInitialize()
    {
        SteveKunGLib.init();
        SteveKunGLib.LOGGER.setDebug(SteveKunGLibFabric.CONFIG.getConfig().enableDebugLog);
    }
}