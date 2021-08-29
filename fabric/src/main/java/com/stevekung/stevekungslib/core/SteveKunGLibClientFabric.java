package com.stevekung.stevekungslib.core;

import com.stevekung.stevekungslib.utils.CommonUtils;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;

public class SteveKunGLibClientFabric implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        CommonUtils.initAntisteal(SteveKunGLib.MOD_ID, SteveKunGLibClientFabric.class, () -> Minecraft.getInstance().close());
    }
}