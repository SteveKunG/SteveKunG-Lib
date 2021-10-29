package com.stevekung.stevekungslib.forge.client.event.handler;

import com.stevekung.stevekungslib.forge.config.SteveKunGsLibConfig;
import com.stevekung.stevekungslib.forge.core.SteveKunGLibForge;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEventHandler
{
    private final Minecraft mc;

    public ClientEventHandler()
    {
        this.mc = Minecraft.getInstance();
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        if (this.mc.player != null && SteveKunGsLibConfig.GENERAL.enableVersionChecker.get())
        {
            if (!SteveKunGLibForge.CHECKER.hasChecked())
            {
                SteveKunGLibForge.CHECKER.checkFail();
                SteveKunGLibForge.CHECKER.printInfo();
                SteveKunGLibForge.CHECKER.setChecked(true);
            }
        }
    }
}