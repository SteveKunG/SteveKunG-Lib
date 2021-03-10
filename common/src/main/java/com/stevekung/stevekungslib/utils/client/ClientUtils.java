package com.stevekung.stevekungslib.utils.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.stevekung.stevekungslib.mixin.client.InvokerGui;
import com.stevekung.stevekungslib.utils.TextComponentUtils;
import me.shedaniel.architectury.annotations.ExpectPlatform;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ClientUtils
{
    public static final ResourceLocation UNICODE = new ResourceLocation("uniform");

    @ExpectPlatform
    public static boolean isClient()
    {
        throw new Error();
    }

    @ExpectPlatform
    public static boolean isEffectiveClient()
    {
        throw new Error();
    }

    public static boolean isKeyDown(int key)
    {
        return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), key);
    }

    public static boolean isShiftKeyDown()
    {
        return Screen.hasShiftDown();
    }

    public static boolean isControlKeyDown()
    {
        return Screen.hasControlDown();
    }

    public static void setOverlayMessage(String message)
    {
        Minecraft.getInstance().gui.setOverlayMessage(TextComponentUtils.component(message), false);
    }

    public static void setOverlayMessage(String message, int delay)
    {
        Minecraft.getInstance().gui.setOverlayMessage(TextComponentUtils.component(message), false);
        ((InvokerGui)Minecraft.getInstance().gui).setOverlayMessageTime(delay);
    }

    public static void setOverlayMessage(Component component)
    {
        Minecraft.getInstance().gui.setOverlayMessage(component, false);
    }

    public static void setOverlayMessage(Component component, int delay)
    {
        Minecraft.getInstance().gui.setOverlayMessage(component, false);
        ((InvokerGui)Minecraft.getInstance().gui).setOverlayMessageTime(delay);
    }

    public static void printClientMessage(String text)
    {
        ClientUtils.printClientMessage(TextComponentUtils.component(text));
    }

    public static void printClientMessage(String text, ChatFormatting color)
    {
        ClientUtils.printClientMessage(TextComponentUtils.formatted(text, color));
    }

    public static void printClientMessage(Component component)
    {
        if (Minecraft.getInstance().player != null)
        {
            Minecraft.getInstance().player.sendMessage(component, Util.NIL_UUID);
        }
    }
}