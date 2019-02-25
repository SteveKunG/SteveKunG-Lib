package stevekung.mods.stevekungslib.utils.client;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import net.minecraftforge.fml.loading.FMLLoader;
import stevekung.mods.stevekungslib.utils.JsonUtils;

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
        return GLFW.glfwGetKey(Minecraft.getInstance().mainWindow.getHandle(), key) == GLFW.GLFW_PRESS;
    }

    public static boolean isMouseDown(int button)
    {
        return GLFW.glfwGetMouseButton(Minecraft.getInstance().mainWindow.getHandle(), button) == GLFW.GLFW_PRESS;
    }

    public static boolean isShiftKeyDown()
    {
        return GuiScreen.isShiftKeyDown();
    }

    public static boolean isControlKeyDown()
    {
        return GuiScreen.isCtrlKeyDown();
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