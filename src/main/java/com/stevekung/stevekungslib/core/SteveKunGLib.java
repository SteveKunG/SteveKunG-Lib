package com.stevekung.stevekungslib.core;

import com.stevekung.stevekungslib.config.SteveKunGsLibConfig;
import com.stevekung.stevekungslib.proxy.LibClientProxy;
import com.stevekung.stevekungslib.proxy.LibCommonProxy;
import com.stevekung.stevekungslib.utils.CommonUtils;
import com.stevekung.stevekungslib.utils.LoggerBase;
import com.stevekung.stevekungslib.utils.ModVersionChecker;

import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

@Mod(SteveKunGLib.MOD_ID)
public class SteveKunGLib
{
    public static final String MOD_ID = "stevekungs_lib";
    public static final LoggerBase LOGGER = new LoggerBase("SteveKunG's Lib");
    public static LibCommonProxy PROXY;
    public static final ModVersionChecker CHECKER = new ModVersionChecker(MOD_ID);

    public SteveKunGLib()
    {
        CommonUtils.addModListener(this::phaseFour);
        CommonUtils.registerConfig(ModConfig.Type.COMMON, SteveKunGsLibConfig.GENERAL_BUILDER);
        CommonUtils.registerModEventBus(SteveKunGsLibConfig.class);
        //CommonUtils.registerConfigScreen(() -> (mc, parent) -> new VideoSettingsScreen(parent, mc.gameSettings)); TODO Waiting for forge
        PROXY = DistExecutor.safeRunForDist(() -> LibClientProxy::new, () -> LibCommonProxy::new);
        PROXY.init();
    }

    private void phaseFour(FMLLoadCompleteEvent event)
    {
        SteveKunGLib.CHECKER.startCheck();
    }
}