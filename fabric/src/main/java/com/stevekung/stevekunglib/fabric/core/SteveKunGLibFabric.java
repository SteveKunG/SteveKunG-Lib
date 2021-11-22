package com.stevekung.stevekunglib.fabric.core;

import com.stevekung.stevekunglib.core.SteveKunGLib;
import com.stevekung.stevekunglib.fabric.config.SteveKunGLibConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class SteveKunGLibFabric implements ModInitializer
{
    public static SteveKunGLibConfig CONFIG;

    @Override
    public void onInitialize()
    {
        SteveKunGLib.init();
        AutoConfig.register(SteveKunGLibConfig.class, GsonConfigSerializer::new);
        SteveKunGLibFabric.CONFIG = AutoConfig.getConfigHolder(SteveKunGLibConfig.class).getConfig();
        SteveKunGLib.LOGGER.setDebug(SteveKunGLibFabric.CONFIG.general.enableDebugLog);
    }
}