package com.stevekung.stevekungslib.forge.proxy;

import com.stevekung.stevekungslib.core.SteveKunGLib;
import com.stevekung.stevekungslib.forge.client.event.handler.ClientEventHandler;
import com.stevekung.stevekungslib.forge.utils.ForgeCommonUtils;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class LibClientProxyForge extends LibCommonProxyForge
{
    @Override
    public void init()
    {
        super.init();
        ForgeCommonUtils.registerEventHandler(new ClientEventHandler());
        ForgeCommonUtils.registerConfigScreen((mc, parent) -> ForgeCommonUtils.openConfigFile(parent, SteveKunGLib.MOD_ID, ModConfig.Type.COMMON));
    }

    @Override
    public void commonSetup(FMLCommonSetupEvent event)
    {
        super.commonSetup(event);
    }

    @Override
    public void clientSetup(FMLClientSetupEvent event)
    {
        super.clientSetup(event);
    }
}