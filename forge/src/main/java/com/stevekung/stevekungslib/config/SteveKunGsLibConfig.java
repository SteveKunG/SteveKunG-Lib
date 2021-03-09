package com.stevekung.stevekungslib.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class SteveKunGsLibConfig
{
    public static final ForgeConfigSpec.Builder GENERAL_BUILDER = new ForgeConfigSpec.Builder();
    public static final General GENERAL = new General(SteveKunGsLibConfig.GENERAL_BUILDER);

    public static class General
    {
        public final ForgeConfigSpec.BooleanValue enableDebugLog;
        public final ForgeConfigSpec.BooleanValue enableVersionChecker;

        private General(ForgeConfigSpec.Builder builder)
        {
            builder.comment("General settings")
            .push("general");

            this.enableDebugLog = builder
                    .comment("Display debug log, useful to find bugs.")
                    .translation("stevekungs_lib.configgui.debug_log")
                    .define("enableDebugLog", true);
            this.enableVersionChecker = builder
                    .translation("stevekungs_lib.configgui.enable_version_checker")
                    .define("enableVersionChecker", true);
            builder.pop();
        }
    }
}