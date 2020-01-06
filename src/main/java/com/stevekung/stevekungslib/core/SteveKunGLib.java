package com.stevekung.stevekungslib.core;

import com.stevekung.stevekungslib.client.event.ClientEventHandler;
import com.stevekung.stevekungslib.config.SteveKunGsLibConfig;
import com.stevekung.stevekungslib.utils.ColorUtils;
import com.stevekung.stevekungslib.utils.CommonUtils;
import com.stevekung.stevekungslib.utils.LoggerBase;
import com.stevekung.stevekungslib.utils.client.ClientUtils;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(SteveKunGLib.MOD_ID)
public class SteveKunGLib
{
    public static final String MOD_ID = "stevekungs_lib";
    public static final LoggerBase LOGGER = new LoggerBase("SteveKunG's Lib");

    public SteveKunGLib()
    {
        CommonUtils.registerConfig(ModConfig.Type.COMMON, SteveKunGsLibConfig.GENERAL_BUILDER);
        CommonUtils.registerModEventBus(SteveKunGsLibConfig.class);
        //CommonUtils.registerConfigScreen(() -> (mc, parent) -> new VideoSettingsScreen(parent, mc.gameSettings)); TODO Waiting for forge

        CommonUtils.addModListener(this::phaseOne);
        CommonUtils.registerEventHandler(this);
    }

    private void phaseOne(FMLCommonSetupEvent event)
    {
        SteveKunGLib.LOGGER.setDebug(SteveKunGsLibConfig.GENERAL.enableDebugLog.get());

        if (ClientUtils.isEffectiveClient())
        {
            CommonUtils.registerEventHandler(new ClientEventHandler());
        }
        if (ClientUtils.isClient())
        {
            ColorUtils.init();
        }
    }
}