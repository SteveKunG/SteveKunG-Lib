package com.stevekung.stevekunglib.fabric.modmenu;

import com.stevekung.stevekunglib.fabric.config.SteveKunGLibConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;

public class ModMenuIntegrationLib implements ModMenuApi
{
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory()
    {
        return parent -> AutoConfig.getConfigScreen(SteveKunGLibConfig.class, parent).get();
    }
}