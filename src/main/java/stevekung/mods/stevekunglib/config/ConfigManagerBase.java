package stevekung.mods.stevekunglib.config;

import net.minecraftforge.common.ForgeConfigSpec;
import stevekung.mods.stevekunglib.utils.LoggerSL;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigManagerBase
{
    private String modid;
    private ForgeConfigSpec.Builder builder;

    public ConfigManagerBase(String modid, ForgeConfigSpec.Builder builder)
    {
        this.modid = modid;
        this.builder = builder;
    }

    public void load()
    {
        this.loadFrom(Paths.get("config"), this.modid, this.builder);
    }

    private void loadFrom(Path configRoot, String modid, ForgeConfigSpec.Builder builder)
    {
        String fileName = modid + ".toml";
        Path configFile = configRoot.resolve(fileName);
        builder.build().setConfigFile(configFile);
        LoggerSL.info("Loaded config from '{}'", fileName);
    }
}