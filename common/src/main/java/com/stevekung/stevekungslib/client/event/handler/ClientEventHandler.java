package com.stevekung.stevekungslib.client.event.handler;

import com.stevekung.stevekungslib.config.SteveKunGsLibConfig;
import com.stevekung.stevekungslib.core.SteveKunGLib;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEventHandler
{
    private final Minecraft mc;
    public static int ticks;
    public static int ticksPaused;
    public static float renderPartialTicks;

    public ClientEventHandler()
    {
        this.mc = Minecraft.getInstance();
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        if (event.phase == Phase.START)
        {
            ClientEventHandler.ticks++;
            ClientEventHandler.renderPartialTicks = ClientEventHandler.ticks + this.mc.getFrameTime();

            if (!this.mc.isPaused())
            {
                ClientEventHandler.ticksPaused++;
            }
        }
        if (this.mc.player != null && SteveKunGsLibConfig.GENERAL.enableVersionChecker.get())
        {
            if (!SteveKunGLib.CHECKER.hasChecked())
            {
                SteveKunGLib.CHECKER.checkFail();
                SteveKunGLib.CHECKER.printInfo();
                SteveKunGLib.CHECKER.setChecked(true);
            }
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event)
    {
        if (event.getGui() instanceof TitleScreen)
        {
            ClientEventHandler.ticks = 0;
            ClientEventHandler.ticksPaused = 0;
        }
    }
}