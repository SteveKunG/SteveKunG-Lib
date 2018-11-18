package stevekung.mods.stevekunglib.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import stevekung.mods.stevekunglib.client.gui.GuiChatBase;
import stevekung.mods.stevekunglib.client.gui.GuiSleepMPBase;
import stevekung.mods.stevekunglib.config.ConfigManagerLib;

public class ClientEventHandler
{
    private final Minecraft mc;
    public static int ticks;
    public static int ticksPaused;
    public static float renderPartialTicks;

    public ClientEventHandler()
    {
        this.mc = Minecraft.getMinecraft();
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent event)
    {
        if (this.mc.currentScreen instanceof GuiMainMenu)
        {
            ClientEventHandler.ticks = 0;
            ClientEventHandler.ticksPaused = 0;
        }
        if (event.phase == Phase.START)
        {
            ClientEventHandler.ticks++;
            ClientEventHandler.renderPartialTicks = ClientEventHandler.ticks + this.mc.getRenderPartialTicks();

            if (!this.mc.isGamePaused())
            {
                ClientEventHandler.ticksPaused++;
            }
        }
        if (ConfigManagerLib.stevekung_lib_general.replaceGuiIngame)
        {
            ClientEventHandler.replaceGuiChat(this.mc, this.mc.currentScreen);
        }
    }

    @SubscribeEvent
    public void onPressKey(InputEvent.KeyInputEvent event)
    {
        if (ConfigManagerLib.stevekung_lib_general.replaceGuiIngame && this.mc.currentScreen == null && this.mc.gameSettings.keyBindCommand.isPressed())
        {
            GuiChatBase chatGuiSlash = new GuiChatBase("/");
            this.mc.displayGuiScreen(chatGuiSlash);
        }
    }

    private static void replaceGuiChat(Minecraft mc, GuiScreen currentScreen)
    {
        if (currentScreen != null)
        {
            if (currentScreen instanceof GuiChat && !(currentScreen instanceof GuiChatBase || currentScreen instanceof GuiSleepMP))
            {
                GuiChatBase chatGui = new GuiChatBase();
                mc.displayGuiScreen(chatGui);
            }
            if (currentScreen instanceof GuiSleepMP && !(currentScreen instanceof GuiSleepMPBase))
            {
                GuiSleepMPBase sleepGui = new GuiSleepMPBase();
                mc.displayGuiScreen(sleepGui);
            }
            if (currentScreen instanceof GuiSleepMPBase && !mc.player.isPlayerSleeping())
            {
                mc.displayGuiScreen(null);
            }
        }
    }
}