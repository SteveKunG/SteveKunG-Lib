package com.stevekung.stevekungslib.fabric.modmenu;

import com.stevekung.stevekungslib.fabric.config.SteveKunGsLibConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;

public class ModMenuIntegrationLib implements ModMenuApi
{
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory()
    {
        return parent -> AutoConfig.getConfigScreen(SteveKunGsLibConfig.class, parent).get();
    }
}