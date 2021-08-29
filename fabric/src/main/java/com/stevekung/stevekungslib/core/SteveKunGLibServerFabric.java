package com.stevekung.stevekungslib.core;

import com.stevekung.stevekungslib.utils.CommonUtils;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class SteveKunGLibServerFabric implements DedicatedServerModInitializer
{
    @Override
    public void onInitializeServer()
    {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> CommonUtils.initAntisteal(SteveKunGLib.MOD_ID, SteveKunGLibServerFabric.class, server::close));
    }
}