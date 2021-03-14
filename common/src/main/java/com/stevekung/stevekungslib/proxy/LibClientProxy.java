package com.stevekung.stevekungslib.proxy;

import me.shedaniel.architectury.event.events.GuiEvent;
import me.shedaniel.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.world.InteractionResultHolder;

public class LibClientProxy extends LibCommonProxy
{
    public static int ticks;
    public static int ticksPaused;
    public static float renderPartialTicks;

    public LibClientProxy()
    {
        GuiEvent.SET_SCREEN.register(this::onScreenOpen);
        ClientTickEvent.CLIENT_PRE.register(this::onClientTick);
    }

    private void onClientTick(Minecraft mc)
    {
        ticks++;
        renderPartialTicks = ticks + mc.getFrameTime();

        if (!mc.isPaused())
        {
            ticksPaused++;
        }
    }

    private InteractionResultHolder<Screen> onScreenOpen(Screen screen)
    {
        if (screen instanceof TitleScreen)
        {
            ticks = 0;
            ticksPaused = 0;
        }
        return InteractionResultHolder.pass(screen);
    }
}