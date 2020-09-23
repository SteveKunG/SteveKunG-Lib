package com.stevekung.stevekungslib.proxy;

import com.stevekung.stevekungslib.utils.CommonUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientProxy
{
    private final Minecraft mc;
    public static int ticks;
    public static int ticksPaused;
    public static float renderPartialTicks;

    public ClientProxy()
    {
        this.mc = Minecraft.getInstance();
        CommonUtils.registerEventHandler(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        if (event.phase == Phase.START)
        {
            ClientProxy.ticks++;
            ClientProxy.renderPartialTicks = ClientProxy.ticks + this.mc.getRenderPartialTicks();

            if (!this.mc.isGamePaused())
            {
                ClientProxy.ticksPaused++;
            }
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event)
    {
        if (event.getGui() instanceof MainMenuScreen)
        {
            ClientProxy.ticks = 0;
            ClientProxy.ticksPaused = 0;
        }
    }
}