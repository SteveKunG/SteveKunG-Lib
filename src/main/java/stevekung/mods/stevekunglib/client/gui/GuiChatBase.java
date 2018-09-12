package stevekung.mods.stevekunglib.client.gui;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.util.text.ITextComponent;

public class GuiChatBase extends GuiChat
{
    public GuiChatBase() {}

    public GuiChatBase(String input)
    {
        super(input);
    }

    @Override
    public void initGui()
    {
        super.initGui();
        GuiChatRegistry.getGuiChatList().stream().forEach(IGuiChat::initGui);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        GuiChatRegistry.getGuiChatList().stream().forEach(gui -> gui.drawScreen(mouseX, mouseY, partialTicks));
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
        GuiChatRegistry.getGuiChatList().stream().forEach(IGuiChat::updateScreen);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        this.tabCompleter.resetRequested();

        if (keyCode == 15)
        {
            this.tabCompleter.complete();
        }
        else
        {
            this.tabCompleter.resetDidComplete();
        }

        if (keyCode == 1)
        {
            this.mc.displayGuiScreen(null);
        }
        else if (keyCode != 28 && keyCode != 156)
        {
            if (keyCode == 200)
            {
                this.getSentHistory(-1);
            }
            else if (keyCode == 208)
            {
                this.getSentHistory(1);
            }
            else if (keyCode == 201)
            {
                this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().getLineCount() - 1);
                GuiChatRegistry.getGuiChatList().stream().forEach(IGuiChat::keyTypedScrollDown);
            }
            else if (keyCode == 209)
            {
                this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().getLineCount() + 1);
                GuiChatRegistry.getGuiChatList().stream().forEach(IGuiChat::keyTypedScrollUp);
            }
            else
            {
                this.inputField.textboxKeyTyped(typedChar, keyCode);
            }
        }
        else
        {
            String text = this.inputField.getText().trim();

            if (!text.isEmpty())
            {
                this.sendChatMessage(text);
            }
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    public void getSentHistory(int msgPos)
    {
        super.getSentHistory(msgPos);
        GuiChatRegistry.getGuiChatList().stream().forEach(gui -> gui.getSentHistory(msgPos));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        GuiChatRegistry.getGuiChatList().stream().forEach(gui -> gui.mouseClicked(mouseX, mouseY, mouseButton));
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        super.mouseReleased(mouseX, mouseY, state);
        GuiChatRegistry.getGuiChatList().stream().forEach(gui -> gui.mouseReleased(mouseX, mouseY, state));
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
        GuiChatRegistry.getGuiChatList().stream().forEach(gui -> gui.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick));
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        GuiChatRegistry.getGuiChatList().stream().forEach(gui -> gui.actionPerformed(button));
    }

    @Override
    protected void handleComponentHover(ITextComponent component, int mouseX, int mouseY)
    {
        GuiChatRegistry.getGuiChatList().stream().forEach(gui -> gui.handleComponentHover(component, mouseX, mouseY));
    }

    @Override
    public void onGuiClosed()
    {
        GuiChatRegistry.getGuiChatList().stream().forEach(IGuiChat::onGuiClosed);
    }

    @Override
    public void handleMouseInput() throws IOException
    {
        GuiChatRegistry.getGuiChatList().stream().forEach(IGuiChat::handleMouseInput);
    }
}