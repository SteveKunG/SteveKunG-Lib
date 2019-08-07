package stevekung.mods.stevekungslib.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SleepInMultiplayerScreen;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import stevekung.mods.stevekungslib.client.gui.ChatScreenBase;
import stevekung.mods.stevekungslib.client.gui.SleepInMultiplayerScreenBase;
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
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        if (this.mc.currentScreen instanceof MainMenuScreen)
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
            ClientEventHandler.replaceGuiChat(this.mc, this.mc.currentScreen);
        }
    }

    @SubscribeEvent
    public void onPressKey(InputEvent.KeyInputEvent event)
    {
        if (SteveKunGsLibConfig.GENERAL.replaceGuiIngame.get() && this.mc.currentScreen == null && this.mc.gameSettings.keyBindCommand.isPressed())
        {
            ChatScreenBase chatGuiSlash = new ChatScreenBase("/");
            this.mc.displayGuiScreen(chatGuiSlash);
        }
    }

    private static void replaceGuiChat(Minecraft mc, Screen screen)
    {
        if (screen != null)
        {
            if (screen instanceof ChatScreen && !(screen instanceof ChatScreenBase || screen instanceof SleepInMultiplayerScreen))
            {
                ChatScreenBase chatGui = new ChatScreenBase("");
                mc.displayGuiScreen(chatGui);
            }
            if (screen instanceof SleepInMultiplayerScreen && !(screen instanceof SleepInMultiplayerScreenBase))
            {
                SleepInMultiplayerScreenBase sleepGui = new SleepInMultiplayerScreenBase("");
                mc.displayGuiScreen(sleepGui);
            }
            if (screen instanceof SleepInMultiplayerScreenBase && !mc.player.isSleeping())
            {
                mc.displayGuiScreen(null);
            }
        }
    }
}