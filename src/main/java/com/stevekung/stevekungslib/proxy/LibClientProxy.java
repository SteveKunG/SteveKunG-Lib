package com.stevekung.stevekungslib.proxy;

import com.stevekung.stevekungslib.client.event.ClientEventHandler;
import com.stevekung.stevekungslib.utils.CommonUtils;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class LibClientProxy extends LibCommonProxy
{
    @Override
    public void init()
    {
        super.init();
        CommonUtils.registerEventHandler(new ClientEventHandler());
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