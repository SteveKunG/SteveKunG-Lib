package stevekung.mods.stevekunglib.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.config.ForgeConfigSpec;
import stevekung.mods.stevekunglib.utils.LoggerSL;

public class SteveKunGsLibConfig
{
    public static SteveKunGsLibConfig INSTANCE = new SteveKunGsLibConfig();
    private static ForgeConfigSpec BUILDER = new ForgeConfigSpec.Builder()
            //General
            .comment("General settings")
            .push("general")
            .comment("Display debug log for easy bug finding!")
            .translation("stevekungs_lib.configgui.debug_log")
            .define("enableDebugLog", true)

            .comment("Replace current Chat GUI and make functionality for GuiChatRegistry works.")
            .translation("stevekungs_lib.configgui.replace_gui_chat")
            .worldRestart()
            .define("replaceGuiIngame", false)
            .pop()

            .build();
    public CommentedFileConfig configData;

    public static void load()
    {
        SteveKunGsLibConfig.INSTANCE.loadFrom(Paths.get("config"));
    }

    private void loadFrom(final Path configRoot)
    {
        Path configFile = configRoot.resolve("stevekungs_lib.toml");
        this.configData = CommentedFileConfig.builder(configFile).sync().autosave().writingMode(WritingMode.REPLACE).build();
        this.configData.load();

        if (!SteveKunGsLibConfig.BUILDER.isCorrect(this.configData))
        {
            LoggerSL.warning("Configuration file {} is not correct. Correcting", configRoot);
            SteveKunGsLibConfig.BUILDER.correct(this.configData, (action, path, incorrectValue, correctedValue) -> LoggerSL.warning("Incorrect key {} was corrected from {} to {}", path, incorrectValue, correctedValue));
            this.configData.save();
        }
        LoggerSL.warning("Loaded config from {}", configFile);
    }
}