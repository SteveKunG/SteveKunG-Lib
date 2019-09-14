package stevekung.mods.stevekungslib.core;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import stevekung.mods.stevekungslib.client.event.ClientEventHandler;
import stevekung.mods.stevekungslib.config.SteveKunGsLibConfig;
import stevekung.mods.stevekungslib.utils.ColorUtils;
import stevekung.mods.stevekungslib.utils.CommonUtils;
import stevekung.mods.stevekungslib.utils.LoggerBase;
import stevekung.mods.stevekungslib.utils.client.ClientUtils;

@Mod(SteveKunGLib.MOD_ID)
public class SteveKunGLib
{
    public static final String MOD_ID = "stevekungs_lib";
    public static LoggerBase LOGGER;

    public SteveKunGLib()
    {
        CommonUtils.registerConfig(ModConfig.Type.CLIENT, SteveKunGsLibConfig.GENERAL_BUILDER);
        CommonUtils.registerModEventBus(SteveKunGsLibConfig.class);

        CommonUtils.addModListener(this::phaseOne);
        CommonUtils.registerEventHandler(this);
    }

    private void phaseOne(FMLCommonSetupEvent event)
    {
        SteveKunGLib.LOGGER = new LoggerBase("SteveKunG's Lib", SteveKunGsLibConfig.GENERAL.enableDebugLog.get());

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