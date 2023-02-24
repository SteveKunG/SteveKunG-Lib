package stevekung.mods.stevekunglib.client.gui;

import java.util.List;

import net.minecraft.client.gui.GuiButton;

public interface IGuiChat
{
    void initGui(List<GuiButton> buttonList, int width, int height);
    void drawScreen(List<GuiButton> buttonList, int mouseX, int mouseY, float partialTicks);
    void updateScreen(List<GuiButton> buttonList, int width, int height);
    void pageUp();
    void pageDown();
    void mouseClicked(int mouseX, int mouseY, int mouseButton);
    void mouseReleased(int mouseX, int mouseY, int state);
    void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick);
    void actionPerformed(GuiButton button);
    void onGuiClosed();
    void handleMouseInput(int width, int height);
    void getSentHistory(int msgPos);
}