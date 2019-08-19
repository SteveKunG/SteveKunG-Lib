package stevekung.mods.stevekungslib.client.gui;

import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import stevekung.mods.stevekungslib.utils.LangUtils;

@Deprecated
@OnlyIn(Dist.CLIENT)
public class SleepInMultiplayerScreenBase extends ChatScreenBase
{
    public SleepInMultiplayerScreenBase(String input)
    {
        super(input);
    }

    @Override
    public void init()
    {
        super.init();
        this.addButton(new Button(this.width / 2 - 100, this.height - 40, 200, 20, LangUtils.translate("multiplayer.stopSleeping"), button -> SleepInMultiplayerScreenBase.this.wakeFromSleep()));
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
                this.sendMessage(text);
            }
            this.inputField.setText("");
            this.minecraft.ingameGUI.getChatGUI().resetScroll();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void wakeFromSleep()
    {
        ClientPlayNetHandler connection = this.minecraft.player.connection;
        connection.sendPacket(new CEntityActionPacket(this.minecraft.player, CEntityActionPacket.Action.STOP_SLEEPING));
    }
}