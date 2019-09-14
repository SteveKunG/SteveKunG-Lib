package com.stevekung.stevekungslib.client.gui;

import java.util.ArrayList;
import java.util.List;

public class ChatScreenRegistry
{
    private static final List<IChatScreen> CHAT_SCREENS = new ArrayList<>();

    public static void register(IChatScreen chat)
    {
        ChatScreenRegistry.CHAT_SCREENS.add(chat);
    }

    public static List<IChatScreen> getChatScreen()
    {
        return ChatScreenRegistry.CHAT_SCREENS;
    }
}