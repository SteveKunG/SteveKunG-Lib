package com.stevekung.stevekunglib.fabric.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "stevekung_lib")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public final class SteveKunGLibConfig implements ConfigData
{
    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.TransitiveObject
    public GeneralCategory general;

    public SteveKunGLibConfig()
    {
        this.general = new GeneralCategory();
    }

    public static class GeneralCategory
    {
        @Comment("Display debug log, useful to find bugs.\n" + "(default value: false)")
        public boolean enableDebugLog = false;
        @ConfigEntry.Gui.RequiresRestart
        public boolean enableVersionChecker = true;
    }
}