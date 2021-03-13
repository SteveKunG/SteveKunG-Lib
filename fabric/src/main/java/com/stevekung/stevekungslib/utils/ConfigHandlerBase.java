package com.stevekung.stevekungslib.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stevekung.stevekungslib.core.SteveKunGLib;
import net.fabricmc.loader.api.FabricLoader;

public abstract class ConfigHandlerBase
{
    protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    protected File configFile;

    public ConfigHandlerBase(String name)
    {
        this.configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), name + ".json");

        try
        {
            this.loadConfig();
        }
        catch (IOException e)
        {
            SteveKunGLib.LOGGER.error("Failed to load config, using default.", e);
        }
    }

    public File getConfigFile()
    {
        return this.configFile;
    }

    public static String readFile(String path, Charset encoding) throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public abstract void loadConfig() throws IOException;

    public abstract void saveConfig() throws IOException;
}