package com.stevekung.stevekungslib.client.event.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.world.InteractionResultHolder;

public class ClientEventHandler
{
    public static int ticks;
    public static int ticksPaused;
    public static float renderPartialTicks;

    public static void onClientTick(Minecraft mc)
    {
        ClientEventHandler.ticks++;
        ClientEventHandler.renderPartialTicks = ClientEventHandler.ticks + mc.getFrameTime();

        if (!mc.isPaused())
        {
            ClientEventHandler.ticksPaused++;
        }
    }

    public static InteractionResultHolder<Screen> onScreenOpen(Screen screen)
    {
        if (screen instanceof TitleScreen)
        {
            ClientEventHandler.ticks = 0;
            ClientEventHandler.ticksPaused = 0;
        }
        return InteractionResultHolder.pass(screen);
    }
}