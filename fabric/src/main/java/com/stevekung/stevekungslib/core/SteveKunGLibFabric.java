package com.stevekung.stevekungslib.core;

import com.stevekung.stevekungslib.config.SteveKunGsLibConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class SteveKunGLibFabric implements ModInitializer
{
    public static SteveKunGsLibConfig CONFIG;

    @Override
    public void onInitialize()
    {
        SteveKunGLib.init();
        AutoConfig.register(SteveKunGsLibConfig.class, GsonConfigSerializer::new);
        SteveKunGLibFabric.CONFIG = AutoConfig.getConfigHolder(SteveKunGsLibConfig.class).getConfig();
        SteveKunGLib.LOGGER.setDebug(SteveKunGLibFabric.CONFIG.general.enableDebugLog);
    }
}