package stevekung.mods.stevekungslib.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import stevekung.mods.stevekungslib.utils.LangUtils;

@OnlyIn(Dist.CLIENT)
public class GuiSleepMPBase extends GuiChatBase
{
    @Override
    public void initGui()
    {
        super.initGui();
        this.addButton(new GuiButton(1, this.width / 2 - 100, this.height - 40, LangUtils.translate("multiplayer.stopSleeping"))
        {
            @Override
            public void onClick(double mouseX, double mouseY)
            {
                GuiSleepMPBase.this.wakeFromSleep();
            }
        });
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if (keyCode == 256)
        {
            this.wakeFromSleep();
        }
        else if (keyCode == 257 || keyCode == 335)
        {
            String text = this.inputField.getText().trim();

            if (!text.isEmpty())
            {
                this.sendChatMessage(text);
            }
            this.inputField.setText("");
            this.mc.ingameGUI.getChatGUI().resetScroll();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void wakeFromSleep()
    {
        NetHandlerPlayClient connection = this.mc.player.connection;
        connection.sendPacket(new CPacketEntityAction(this.mc.player, CPacketEntityAction.Action.STOP_SLEEPING));
    }
}