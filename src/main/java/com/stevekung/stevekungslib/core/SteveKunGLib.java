package com.stevekung.stevekungslib.core;

import com.stevekung.stevekungslib.config.SteveKunGsLibConfig;
import com.stevekung.stevekungslib.proxy.ClientProxy;
import com.stevekung.stevekungslib.proxy.CommonProxy;
import com.stevekung.stevekungslib.utils.CommonUtils;
import com.stevekung.stevekungslib.utils.LoggerBase;

import net.minecraftforge.fml.DistExecutor;
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
        DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

        CommonUtils.addModListener(this::phaseOne);
    }

    private void phaseOne(FMLCommonSetupEvent event)
    {
        SteveKunGLib.LOGGER.setDebug(SteveKunGsLibConfig.GENERAL.enableDebugLog.get());
    }
}