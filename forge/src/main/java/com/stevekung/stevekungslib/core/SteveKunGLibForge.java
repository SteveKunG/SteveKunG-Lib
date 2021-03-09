package com.stevekung.stevekungslib.core;

import com.stevekung.stevekungslib.config.SteveKunGsLibConfig;
import com.stevekung.stevekungslib.proxy.LibClientProxy;
import com.stevekung.stevekungslib.proxy.LibCommonProxy;
import com.stevekung.stevekungslib.utils.ForgeCommonUtils;
import com.stevekung.stevekungslib.utils.ModVersionChecker;
import me.shedaniel.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

@Mod(SteveKunGLib.MOD_ID)
public class SteveKunGLibForge
{
    public static LibCommonProxy PROXY;
    public static final ModVersionChecker CHECKER = new ModVersionChecker(SteveKunGLib.MOD_ID);

    public SteveKunGLibForge()
    {
        EventBuses.registerModEventBus(SteveKunGLib.MOD_ID, ForgeCommonUtils.getModEventBus());
        ForgeCommonUtils.addModListener(this::phaseFour);
        ForgeCommonUtils.registerConfig(ModConfig.Type.COMMON, SteveKunGsLibConfig.GENERAL_BUILDER);
        ForgeCommonUtils.registerModEventBus(SteveKunGsLibConfig.class);
        //CommonUtils.registerConfigScreen(() -> (mc, parent) -> new VideoSettingsScreen(parent, mc.gameSettings)); TODO Waiting for forge
        PROXY = DistExecutor.safeRunForDist(() -> LibClientProxy::new, () -> LibCommonProxy::new);
        PROXY.init();
    }

    private void phaseFour(FMLLoadCompleteEvent event)
    {
        if (SteveKunGsLibConfig.GENERAL.enableVersionChecker.get())
        {
            SteveKunGLibForge.CHECKER.startCheck();
        }
    }
}