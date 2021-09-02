package com.stevekung.stevekungslib.proxy;

import com.stevekung.stevekungslib.client.event.handler.ClientEventHandler;
import com.stevekung.stevekungslib.core.SteveKunGLib;
import com.stevekung.stevekungslib.utils.CommonUtils;
import com.stevekung.stevekungslib.utils.ForgeCommonUtils;
import net.minecraft.client.Minecraft;
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
        CommonUtils.initAntisteal(SteveKunGLib.MOD_ID, LibClientProxyForge.class, () -> Minecraft.getInstance().close());
    }
}