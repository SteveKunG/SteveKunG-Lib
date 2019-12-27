package com.stevekung.stevekungslib.utils.client;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class RenderUtils
{
    public static void bindTexture(String resource)
    {
        RenderUtils.bindTexture(new ResourceLocation(resource));
    }

    public static void bindTexture(ResourceLocation resource)
    {
        Minecraft.getInstance().getTextureManager().bindTexture(resource);
    }
}