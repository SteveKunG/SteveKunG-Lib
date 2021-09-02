package com.stevekung.stevekungslib.proxy;

import com.stevekung.stevekungslib.config.SteveKunGsLibConfig;
import com.stevekung.stevekungslib.core.SteveKunGLib;
import com.stevekung.stevekungslib.utils.CommonUtils;
import com.stevekung.stevekungslib.utils.ForgeCommonUtils;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

public class LibCommonProxyForge
{
    public void init()
    {
        ForgeCommonUtils.registerModEventBus(this);
        ForgeCommonUtils.addModListener(this::commonSetup);
        ForgeCommonUtils.addModListener(this::clientSetup);
        ForgeCommonUtils.addListener(this::serverStarting);
    }

    public void serverStarting(FMLServerStartingEvent event)
    {
        CommonUtils.initAntisteal(SteveKunGLib.MOD_ID, LibCommonProxyForge.class, event.getServer()::close);
    }

    public void commonSetup(FMLCommonSetupEvent event)
    {
        SteveKunGLib.LOGGER.setDebug(SteveKunGsLibConfig.GENERAL.enableDebugLog.get());
    }

    public void clientSetup(FMLClientSetupEvent event)
    {
    }
}