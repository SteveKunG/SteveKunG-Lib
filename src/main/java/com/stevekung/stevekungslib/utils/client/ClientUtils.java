package com.stevekung.stevekungslib.utils.client;

import com.stevekung.stevekungslib.utils.TextComponentUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import net.minecraftforge.fml.loading.FMLLoader;

public class ClientUtils
{
    public static final ResourceLocation UNICODE = new ResourceLocation("uniform");

    public static boolean isClient()
    {
        return FMLLoader.getDist() == Dist.CLIENT;
    }

    public static boolean isEffectiveClient()
    {
        return EffectiveSide.get() == LogicalSide.CLIENT;
    }

    public static boolean isKeyDown(int key)
    {
        return InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), key);
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
        Minecraft.getInstance().ingameGUI.setOverlayMessage(TextComponentUtils.component(message), false);
    }

    public static void setOverlayMessage(String message, int delay)
    {
        Minecraft.getInstance().ingameGUI.setOverlayMessage(TextComponentUtils.component(message), false);
        Minecraft.getInstance().ingameGUI.overlayMessageTime = delay;
    }

    public static void setOverlayMessage(ITextComponent component)
    {
        Minecraft.getInstance().ingameGUI.setOverlayMessage(component, false);
    }

    public static void setOverlayMessage(ITextComponent component, int delay)
    {
        Minecraft.getInstance().ingameGUI.setOverlayMessage(component, false);
        Minecraft.getInstance().ingameGUI.overlayMessageTime = delay;
    }

    public static void printClientMessage(String text)
    {
        ClientUtils.printClientMessage(TextComponentUtils.component(text));
    }

    public static void printClientMessage(String text, TextFormatting color)
    {
        ClientUtils.printClientMessage(TextComponentUtils.formatted(text, color));
    }

    public static void printClientMessage(ITextComponent component)
    {
        if (Minecraft.getInstance().player != null)
        {
            Minecraft.getInstance().player.sendMessage(component, Util.DUMMY_UUID);
        }
    }
}