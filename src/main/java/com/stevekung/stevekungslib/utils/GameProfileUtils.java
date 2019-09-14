package com.stevekung.stevekungslib.utils;

import java.util.UUID;

import net.minecraft.client.Minecraft;

public class GameProfileUtils
{
    public static String getUsername()
    {
        return Minecraft.getInstance().getSession().getProfile().getName();
    }

    public static UUID getUUID()
    {
        return Minecraft.getInstance().getSession().getProfile().getId();
    }

    public static boolean isSteveKunG()
    {
        return GameProfileUtils.getUsername().equals("SteveKunG") && GameProfileUtils.getUUID().equals(UUID.fromString("eef3a603-1c1b-4c98-8264-d2f04b231ef4"));
    }
}