package stevekung.mods.stevekunglib.core;

import java.util.Arrays;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import stevekung.mods.stevekunglib.client.event.ClientEventHandler;
import stevekung.mods.stevekunglib.utils.ColorUtils;
import stevekung.mods.stevekunglib.utils.CommonUtils;
import stevekung.mods.stevekunglib.utils.LoggerSL;
import stevekung.mods.stevekunglib.utils.client.ClientUtils;

@Mod(modid = SteveKunGLib.MOD_ID, name = SteveKunGLib.NAME, version = SteveKunGLib.VERSION, dependencies = SteveKunGLib.FORGE_VERSION, updateJSON = SteveKunGLib.JSON_URL, certificateFingerprint = SteveKunGLib.CERTIFICATE)
public class SteveKunGLib
{
    protected static final String NAME = "SteveKunG's Lib";
    public static final String MOD_ID = "stevekung's_lib";
    protected static final int MAJOR_VERSION = 1;
    protected static final int MINOR_VERSION = 1;
    protected static final int BUILD_VERSION = 4;
    public static final String VERSION = SteveKunGLib.MAJOR_VERSION + "." + SteveKunGLib.MINOR_VERSION + "." + SteveKunGLib.BUILD_VERSION;
    protected static final String FORGE_VERSION = "after:forge@[14.23.5.2838,);";
    protected static final String JSON_URL = "https://raw.githubusercontent.com/SteveKunG/VersionCheckLibrary/master/stevekung's_lib_version.json";
    protected static final String CERTIFICATE = "@FINGERPRINT@";
    private static final String URL = "https://minecraft.curseforge.com/projects/stevekungs-lib";
    public static boolean isDevelopment;

    static
    {
        try
        {
            SteveKunGLib.isDevelopment = Launch.classLoader.getClassBytes("net.minecraft.world.World") != null;
        }
        catch (Exception e) {}
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        SteveKunGLib.init(event.getModMetadata());
        CommonUtils.registerEventHandler(this);

        if (ClientUtils.isEffectiveClient())
        {
            CommonUtils.registerEventHandler(new ClientEventHandler());
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        if (ClientUtils.isEffectiveClient())
        {
            ColorUtils.init();
        }
    }

    @EventHandler
    public void onFingerprintViolation(FMLFingerprintViolationEvent event)
    {
        if (SteveKunGLib.isDevelopment)
        {
            LoggerSL.info("Development environment detected! Ignore certificate check.");
        }
        else
        {
            throw new RuntimeException("Invalid fingerprint detected! This version will NOT be supported by the author!");
        }
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(SteveKunGLib.MOD_ID))
        {
            ConfigManager.sync(SteveKunGLib.MOD_ID, Config.Type.INSTANCE);
        }
    }

    private static void init(ModMetadata info)
    {
        info.autogenerated = false;
        info.modId = SteveKunGLib.MOD_ID;
        info.name = SteveKunGLib.NAME;
        info.version = SteveKunGLib.VERSION;
        info.url = SteveKunGLib.URL;
        info.updateJSON = SteveKunGLib.JSON_URL;
        info.description = "Code and Utilities for SteveKunG's mods!";
        info.authorList = Arrays.asList("SteveKunG");
    }
}