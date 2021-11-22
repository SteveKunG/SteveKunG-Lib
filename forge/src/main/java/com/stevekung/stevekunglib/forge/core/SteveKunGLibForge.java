package com.stevekung.stevekunglib.forge.core;

import com.stevekung.stevekunglib.core.SteveKunGLib;
import com.stevekung.stevekunglib.forge.config.SteveKunGLibConfig;
import com.stevekung.stevekunglib.forge.proxy.LibClientProxyForge;
import com.stevekung.stevekunglib.forge.proxy.LibCommonProxyForge;
import com.stevekung.stevekunglib.forge.utils.ForgeCommonUtils;
import com.stevekung.stevekunglib.forge.utils.ModVersionChecker;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

@Mod(SteveKunGLib.MOD_ID)
public class SteveKunGLibForge
{
    public static LibCommonProxyForge PROXY;
    public static final ModVersionChecker CHECKER = new ModVersionChecker(SteveKunGLib.MOD_ID);

    public SteveKunGLibForge()
    {
        SteveKunGLib.init();
        EventBuses.registerModEventBus(SteveKunGLib.MOD_ID, ForgeCommonUtils.getModEventBus());
        ForgeCommonUtils.addModListener(this::phaseFour);
        ForgeCommonUtils.registerConfig(ModConfig.Type.COMMON, SteveKunGLibConfig.GENERAL_SPEC);
        ForgeCommonUtils.registerModEventBus(SteveKunGLibConfig.class);

        PROXY = DistExecutor.safeRunForDist(() -> LibClientProxyForge::new, () -> LibCommonProxyForge::new);
        PROXY.init();
    }

    private void phaseFour(FMLLoadCompleteEvent event)
    {
        if (SteveKunGLibConfig.GENERAL.enableVersionChecker.get())
        {
            SteveKunGLibForge.CHECKER.startCheck();
        }
    }
}