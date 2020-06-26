package com.stevekung.stevekungslib.utils.client;

import org.lwjgl.glfw.GLFW;

import com.stevekung.stevekungslib.utils.JsonUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import net.minecraftforge.fml.loading.FMLLoader;

public class ClientUtils
{
    public static FontRenderer unicodeFontRenderer;

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
        return GLFW.glfwGetKey(Minecraft.getInstance().getMainWindow().getHandle(), key) == GLFW.GLFW_PRESS;
    }

    public static boolean isMouseDown(int button)
    {
        return GLFW.glfwGetMouseButton(Minecraft.getInstance().getMainWindow().getHandle(), button) == GLFW.GLFW_PRESS;
    }

    public static boolean isShiftKeyDown()
    {
        return false;
        //        return Screen.hasShiftDown();TODO
    }

    public static boolean isControlKeyDown()
    {
        return false;
        //        return Screen.hasControlDown();TODO
    }

    public static void setOverlayMessage(String message)
    {
        Minecraft.getInstance().ingameGUI.setOverlayMessage(JsonUtils.create(message), false);
    }

    public static void setOverlayMessage(String message, int delay)
    {
        Minecraft.getInstance().ingameGUI.setOverlayMessage(JsonUtils.create(message), false);
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
        ClientUtils.printClientMessage(JsonUtils.create(text));
    }

    public static void printClientMessage(String text, TextFormatting color)
    {
        ClientUtils.printClientMessage(JsonUtils.create(text).func_240699_a_(color));
    }

    public static void printClientMessage(ITextComponent component)
    {
        if (Minecraft.getInstance().player != null)
        {
            Minecraft.getInstance().player.sendMessage(component, Util.field_240973_b_);
        }
    }
}