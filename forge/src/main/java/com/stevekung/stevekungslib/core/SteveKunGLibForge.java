package com.stevekung.stevekungslib.core;

import com.stevekung.stevekungslib.config.SteveKunGsLibConfig;
import com.stevekung.stevekungslib.proxy.LibClientProxyForge;
import com.stevekung.stevekungslib.proxy.LibCommonProxyForge;
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
    public static LibCommonProxyForge PROXY;
    public static final ModVersionChecker CHECKER = new ModVersionChecker(SteveKunGLib.MOD_ID);

    public SteveKunGLibForge()
    {
        SteveKunGLib.init();
        EventBuses.registerModEventBus(SteveKunGLib.MOD_ID, ForgeCommonUtils.getModEventBus());
        ForgeCommonUtils.addModListener(this::phaseFour);
        ForgeCommonUtils.registerConfig(ModConfig.Type.COMMON, SteveKunGsLibConfig.GENERAL_SPEC);
        ForgeCommonUtils.registerModEventBus(SteveKunGsLibConfig.class);
        ForgeCommonUtils.registerConfigScreen(() -> (mc, parent) -> ForgeCommonUtils.openConfigFile(parent, SteveKunGLib.MOD_ID, ModConfig.Type.COMMON));

        PROXY = DistExecutor.safeRunForDist(() -> LibClientProxyForge::new, () -> LibCommonProxyForge::new);
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