package stevekung.mods.stevekunglib.core;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import stevekung.mods.stevekunglib.client.event.ClientEventHandler;
import stevekung.mods.stevekunglib.config.ConfigManagerBase;
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
        CommonUtils.addModListener(this::setup);
        CommonUtils.addModListener(this::postInit);
        CommonUtils.registerEventHandler(this);
        new ConfigManagerBase(SteveKunGLib.MOD_ID, SteveKunGsLibConfig.GENERAL_BUILDER).load();
    }

    private void setup(FMLClientSetupEvent event)
    {
        CommonUtils.registerEventHandler(this);

        if (ClientUtils.isClient())
        {
            CommonUtils.registerEventHandler(new ClientEventHandler());
        }
    }

    private void postInit(InterModProcessEvent event)
    {
        if (ClientUtils.isClient())
        {
            ColorUtils.init();
        }
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(SteveKunGLib.MOD_ID))
        {
            //ConfigManager.sync(SteveKunGLib.MOD_ID, Config.Type.INSTANCE);TODO
        }
    }
}