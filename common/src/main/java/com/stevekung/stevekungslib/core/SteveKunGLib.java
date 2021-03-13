package com.stevekung.stevekungslib.core;

import com.stevekung.stevekungslib.client.event.handler.ClientEventHandler;
import com.stevekung.stevekungslib.utils.LoggerBase;
import me.shedaniel.architectury.event.events.GuiEvent;
import me.shedaniel.architectury.event.events.client.ClientTickEvent;

public class SteveKunGLib
{
    public static final String MOD_ID = "stevekungs_lib";
    public static final LoggerBase LOGGER = new LoggerBase("SteveKunG's Lib");

    public static void init()
    {
        GuiEvent.SET_SCREEN.register(ClientEventHandler::onScreenOpen);
        ClientTickEvent.CLIENT_PRE.register(ClientEventHandler::onClientTick);
    }
}