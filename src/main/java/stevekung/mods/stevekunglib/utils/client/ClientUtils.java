package stevekung.mods.stevekunglib.utils.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import stevekung.mods.stevekunglib.utils.JsonUtils;

public class ClientUtils
{
    public static boolean isClient()
    {
        return FMLCommonHandler.instance().getSide() == Side.CLIENT;
    }

    public static boolean isEffectiveClient()
    {
        return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;
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
        Minecraft.getMinecraft().ingameGUI.setOverlayMessage(message, false);
    }

    public static void setOverlayMessage(String message, int delay)
    {
        Minecraft.getMinecraft().ingameGUI.setOverlayMessage(message, false);
        Minecraft.getMinecraft().ingameGUI.overlayMessageTime = delay;
    }

    public static void setOverlayMessage(ITextComponent component)
    {
        Minecraft.getMinecraft().ingameGUI.setOverlayMessage(component.getFormattedText(), false);
    }

    public static void setOverlayMessage(ITextComponent component, int delay)
    {
        Minecraft.getMinecraft().ingameGUI.setOverlayMessage(component.getFormattedText(), false);
        Minecraft.getMinecraft().ingameGUI.overlayMessageTime = delay;
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
        if (Minecraft.getMinecraft().player != null)
        {
            Minecraft.getMinecraft().player.sendMessage(component);
        }
    }

    public static void registerCommand(ClientCommandBase command)
    {
        ClientCommandHandler.instance.registerCommand(command);
    }
}