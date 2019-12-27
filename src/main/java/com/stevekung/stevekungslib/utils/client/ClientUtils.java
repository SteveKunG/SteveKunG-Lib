package com.stevekung.stevekungslib.utils.client;

import org.lwjgl.glfw.GLFW;

import com.stevekung.stevekungslib.utils.JsonUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import net.minecraftforge.fml.loading.FMLLoader;

public class ClientUtils
{
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
        return GLFW.glfwGetKey(Minecraft.getInstance().func_228018_at_().getHandle(), key) == GLFW.GLFW_PRESS;
    }

    public static boolean isMouseDown(int button)
    {
        return GLFW.glfwGetMouseButton(Minecraft.getInstance().func_228018_at_().getHandle(), button) == GLFW.GLFW_PRESS;
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
        Minecraft.getInstance().ingameGUI.setOverlayMessage(message, false);
    }

    public static void setOverlayMessage(String message, int delay)
    {
        Minecraft.getInstance().ingameGUI.setOverlayMessage(message, false);
        Minecraft.getInstance().ingameGUI.overlayMessageTime = delay;
    }

    public static void setOverlayMessage(ITextComponent component)
    {
        Minecraft.getInstance().ingameGUI.setOverlayMessage(component.getFormattedText(), false);
    }

    public static void setOverlayMessage(ITextComponent component, int delay)
    {
        Minecraft.getInstance().ingameGUI.setOverlayMessage(component.getFormattedText(), false);
        Minecraft.getInstance().ingameGUI.overlayMessageTime = delay;
    }

    public static void printClientMessage(String text)
    {
        ClientUtils.printClientMessage(JsonUtils.create(text));
    }

    public static void printClientMessage(String text, Style color)
    {
        ClientUtils.printClientMessage(JsonUtils.create(text).setStyle(color));
    }

    public static void printClientMessage(ITextComponent component)
    {
        if (Minecraft.getInstance().player != null)
        {
            Minecraft.getInstance().player.sendMessage(component);
        }
    }
}