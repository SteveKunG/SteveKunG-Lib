package com.stevekung.stevekungslib.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class SteveKunGsLibConfig
{
    public static final ForgeConfigSpec.Builder GENERAL_BUILDER = new ForgeConfigSpec.Builder();
    public static final SteveKunGsLibConfig.General GENERAL = new SteveKunGsLibConfig.General(SteveKunGsLibConfig.GENERAL_BUILDER);

    public static class General
    {
        public final ForgeConfigSpec.BooleanValue enableDebugLog;

        private General(ForgeConfigSpec.Builder builder)
        {
            builder.comment("General settings")
            .push("general");

            this.enableDebugLog = builder
                    .comment("Display debug log for easy bug finding!")
                    .translation("stevekungs_lib.configgui.debug_log")
                    .define("enableDebugLog", true);
            builder.pop();
        }
    }
}