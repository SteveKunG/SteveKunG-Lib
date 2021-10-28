package com.stevekung.stevekungslib.proxy.forge;

import com.stevekung.stevekungslib.client.forge.event.handler.ClientEventHandler;
import com.stevekung.stevekungslib.utils.forge.ForgeCommonUtils;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class LibClientProxyForge extends LibCommonProxyForge
{
    @Override
    public void init()
    {
        super.init();
        ForgeCommonUtils.registerEventHandler(new ClientEventHandler());
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