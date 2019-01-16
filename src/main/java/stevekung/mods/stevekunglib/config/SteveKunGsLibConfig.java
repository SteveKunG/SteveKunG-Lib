package stevekung.mods.stevekunglib.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class SteveKunGsLibConfig
{
    public static final ForgeConfigSpec.Builder GENERAL_BUILDER = new ForgeConfigSpec.Builder();
    public static final SteveKunGsLibConfig.General GENERAL = new SteveKunGsLibConfig.General(SteveKunGsLibConfig.GENERAL_BUILDER);

    public static class General
    {
        public final ForgeConfigSpec.BooleanValue enableDebugLog;
        public final ForgeConfigSpec.BooleanValue replaceGuiIngame;

        General(ForgeConfigSpec.Builder builder)
        {
            builder.comment("General settings")
                    .push("general");

            this.enableDebugLog = builder
                    .comment("Display debug log for easy bug finding!")
                    .translation("stevekungs_lib.configgui.debug_log")
                    .define("enableDebugLog", true);

            this.replaceGuiIngame = builder
                    .comment("Replace current Chat GUI and make functionality for GuiChatRegistry works.")
                    .translation("stevekungs_lib.configgui.replace_gui_chat")
                    .worldRestart() //TODO MC Restart
                    .define("replaceGuiIngame", false);

            builder.pop();
        }
    }
}