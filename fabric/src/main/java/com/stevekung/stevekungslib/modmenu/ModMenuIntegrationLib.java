package com.stevekung.stevekungslib.modmenu;

import com.stevekung.stevekungslib.config.SteveKunGsLibConfig;
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