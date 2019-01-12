package stevekung.mods.stevekunglib.core;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.javafmlmod.FMLModLoadingContext;
import stevekung.mods.stevekunglib.client.event.ClientEventHandler;
import stevekung.mods.stevekunglib.config.SteveKunGsLibConfig;
import stevekung.mods.stevekunglib.utils.ColorUtils;
import stevekung.mods.stevekunglib.utils.CommonUtils;
import stevekung.mods.stevekunglib.utils.client.ClientUtils;

@Mod(SteveKunGLib.MOD_ID)
public class SteveKunGLib
{
    public static final String MOD_ID = "stevekungs_lib";

    public SteveKunGLib()
    {
        FMLModLoadingContext.get().getModEventBus().addListener(this::preInit);
        FMLModLoadingContext.get().getModEventBus().addListener(this::init);
        FMLModLoadingContext.get().getModEventBus().addListener(this::postInit);

        CommonUtils.registerEventHandler(this);
        SteveKunGsLibConfig.load();
    }

    private void preInit(FMLPreInitializationEvent event)
    {
        CommonUtils.registerEventHandler(this);

        if (ClientUtils.isClient())
        {
            CommonUtils.registerEventHandler(new ClientEventHandler());
        }
    }

    private void init(FMLInitializationEvent event)
    {
        if (ClientUtils.isClient())
        {
            ColorUtils.init();
        }
    }

    private void postInit(FMLPostInitializationEvent event)
    {

    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(SteveKunGLib.MOD_ID))
        {
            //ConfigManager.sync(SteveKunGLib.MOD_ID, Config.Type.INSTANCE);
        }
    }
}