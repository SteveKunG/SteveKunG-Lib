package com.stevekung.stevekungslib.config;

import org.apache.commons.lang3.tuple.Pair;
import net.minecraftforge.common.ForgeConfigSpec;

public class SteveKunGsLibConfig
{
    public static final ForgeConfigSpec GENERAL_SPEC;
    public static final General GENERAL;

    static
    {
        Pair<General, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(General::new);
        GENERAL_SPEC = specPair.getRight();
        GENERAL = specPair.getLeft();
    }

    public static class General
    {
        public final ForgeConfigSpec.BooleanValue enableDebugLog;
        public final ForgeConfigSpec.BooleanValue enableVersionChecker;

        private General(ForgeConfigSpec.Builder builder)
        {
            builder.comment("General settings");
            builder.push("general");

            this.enableDebugLog = builder.comment("Display debug log, useful to find bugs.").translation("text.autoconfig.stevekungs_lib.option.general.enableDebugLog").define("enableDebugLog", false);
            this.enableVersionChecker = builder.translation("text.autoconfig.stevekungs_lib.option.general.enableVersionChecker").define("enableVersionChecker", true);

            builder.pop();
        }
    }
}