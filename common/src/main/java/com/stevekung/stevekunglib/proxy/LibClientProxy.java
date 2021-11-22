package com.stevekung.stevekunglib.proxy;

import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;

public class LibClientProxy extends LibCommonProxy
{
    public static int ticks;
    public static int ticksPaused;
    public static float renderPartialTicks;

    public LibClientProxy()
    {
        ClientGuiEvent.SET_SCREEN.register(this::onScreenOpen);
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

    private CompoundEventResult<Screen> onScreenOpen(Screen screen)
    {
        if (screen instanceof TitleScreen)
        {
            ticks = 0;
            ticksPaused = 0;
        }
        return CompoundEventResult.pass();
    }
}