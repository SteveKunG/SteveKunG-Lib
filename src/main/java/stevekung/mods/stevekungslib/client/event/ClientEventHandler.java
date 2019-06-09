package stevekung.mods.stevekungslib.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SleepInMultiplayerScreen;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import stevekung.mods.stevekungslib.client.gui.GuiChatBase;
import stevekung.mods.stevekungslib.client.gui.GuiSleepMPBase;
import stevekung.mods.stevekungslib.config.SteveKunGsLibConfig;

public class ClientEventHandler
{
    private final Minecraft mc;
    public static int ticks;
    public static int ticksPaused;
    public static float renderPartialTicks;

    public ClientEventHandler()
    {
        this.mc = Minecraft.getInstance();
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent event)
    {
        if (this.mc.field_71462_r instanceof MainMenuScreen)
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
        if (SteveKunGsLibConfig.GENERAL.replaceGuiIngame.get())
        {
            ClientEventHandler.replaceGuiChat(this.mc, this.mc.field_71462_r);
        }
    }

    @SubscribeEvent
    public void onPressKey(InputEvent.KeyInputEvent event)
    {
        if (SteveKunGsLibConfig.GENERAL.replaceGuiIngame.get() && this.mc.field_71462_r == null && this.mc.gameSettings.keyBindCommand.isPressed())
        {
            GuiChatBase chatGuiSlash = new GuiChatBase("/");
            this.mc.displayGuiScreen(chatGuiSlash);
        }
    }

    private static void replaceGuiChat(Minecraft mc, Screen field_71462_r)
    {
        if (field_71462_r != null)
        {
            if (field_71462_r instanceof ChatScreen && !(field_71462_r instanceof GuiChatBase || field_71462_r instanceof SleepInMultiplayerScreen))
            {
                GuiChatBase chatGui = new GuiChatBase("");
                mc.displayGuiScreen(chatGui);
            }
            if (field_71462_r instanceof SleepInMultiplayerScreen && !(field_71462_r instanceof GuiSleepMPBase))
            {
                GuiSleepMPBase sleepGui = new GuiSleepMPBase("");
                mc.displayGuiScreen(sleepGui);
            }
            if (field_71462_r instanceof GuiSleepMPBase && !mc.player.isPlayerSleeping())
            {
                mc.displayGuiScreen(null);
            }
        }
    }
}