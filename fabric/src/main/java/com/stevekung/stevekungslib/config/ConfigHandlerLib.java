package com.stevekung.stevekungslib.config;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;

import com.stevekung.stevekungslib.core.SteveKunGLib;
import com.stevekung.stevekungslib.utils.ConfigHandlerBase;
import com.stevekung.stevekungslib.utils.TextComponentUtils;

public class ConfigHandlerLib extends ConfigHandlerBase
{
    private SteveKunGsLibConfig config;

    public ConfigHandlerLib()
    {
        super(SteveKunGLib.MOD_ID);
    }

    public SteveKunGsLibConfig getConfig()
    {
        if (this.config == null)
        {
            try
            {
                this.loadConfig();
            }
            catch (IOException e)
            {
                SteveKunGLib.LOGGER.error("Failed to load config, using default.", e);
                return new SteveKunGsLibConfig();
            }
        }
        return this.config;
    }

    @Override
    public void loadConfig() throws IOException
    {
        this.configFile.getParentFile().mkdirs();

        if (!this.configFile.exists())
        {
            SteveKunGLib.LOGGER.error("Unable to find config file, creating new one.");
            this.config = new SteveKunGsLibConfig();
            this.saveConfig();
        }
        else
        {
            this.config = GSON.fromJson(ConfigHandlerBase.readFile(this.configFile.toPath().toString(), Charset.defaultCharset()), SteveKunGsLibConfig.class);
        }
    }

    @Override
    public void saveConfig() throws IOException
    {
        this.configFile.getParentFile().mkdirs();
        FileWriter writer = new FileWriter(this.configFile);
        TextComponentUtils.toJson(this.config, writer);
        writer.close();
    }
}