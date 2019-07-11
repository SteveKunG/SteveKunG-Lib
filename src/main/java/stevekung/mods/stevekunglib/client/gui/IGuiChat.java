package stevekung.mods.stevekunglib.client.gui;

import java.util.List;

import net.minecraft.client.gui.GuiButton;

public interface IGuiChat
{
    public void initGui(List<GuiButton> buttonList, int width, int height);
    public void drawScreen(List<GuiButton> buttonList, int mouseX, int mouseY, float partialTicks);
    public void updateScreen(List<GuiButton> buttonList, int width, int height);
    public void pageUp();
    public void pageDown();
    public void mouseClicked(int mouseX, int mouseY, int mouseButton);
    public void mouseReleased(int mouseX, int mouseY, int state);
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick);
    public void actionPerformed(GuiButton button);
    public void onGuiClosed();
    public void handleMouseInput(int width, int height);
    public void getSentHistory(int msgPos);
}