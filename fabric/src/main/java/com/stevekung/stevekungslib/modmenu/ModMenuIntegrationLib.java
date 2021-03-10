package com.stevekung.stevekungslib.modmenu;

import java.io.IOException;

import com.stevekung.stevekungslib.config.SteveKunGsLibConfig;
import com.stevekung.stevekungslib.core.SteveKunGLibFabric;
import com.stevekung.stevekungslib.utils.LangUtils;
import com.stevekung.stevekungslib.utils.TextComponentUtils;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;

public class ModMenuIntegrationLib implements ModMenuApi
{
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory()
    {
        return this::createConfigScreen;
    }

    private Screen createConfigScreen(Screen screen)
    {
        SteveKunGsLibConfig config = SteveKunGLibFabric.CONFIG.getConfig();
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(screen).setTitle(LangUtils.translate("ui.stevekungs_lib.config.title"));
        builder.setSavingRunnable(() ->
        {
            try
            {
                SteveKunGLibFabric.CONFIG.saveConfig();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
        ConfigEntryBuilder entry = ConfigEntryBuilder.create();
        ConfigCategory generalCategory = builder.getOrCreateCategory(TextComponentUtils.component("General Settings"));
        generalCategory.addEntry(entry.startBooleanToggle(LangUtils.translate("stevekungs_lib.configgui.debug_log"), config.enableDebugLog).setSaveConsumer(value -> config.enableDebugLog = value).setDefaultValue(false).build());
        generalCategory.addEntry(entry.startBooleanToggle(LangUtils.translate("stevekungs_lib.configgui.enable_version_checker"), config.enableVersionChecker).setSaveConsumer(value -> config.enableVersionChecker = value).setDefaultValue(true).build());
        return builder.build();
    }
}