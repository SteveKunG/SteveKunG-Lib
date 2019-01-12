package stevekung.mods.stevekunglib.client.gui;

import java.io.IOException;
import java.util.List;

import net.minecraft.client.gui.GuiButton;

interface IGuiChat
{
    void initGui(List<GuiButton> buttonList, int width, int height);
    void drawScreen(List<GuiButton> buttonList, int mouseX, int mouseY, float partialTicks);
    void updateScreen(List<GuiButton> buttonList, int width, int height);
    void keyTypedScrollDown();
    void keyTypedScrollUp();
    void mouseClicked(double mouseX, double mouseY, int mouseButton);
    void mouseReleased(double mouseX, double mouseY, int state);
    void mouseClickMove(double mouseX, double mouseY, int clickedMouseButton, long timeSinceLastClick);
    void actionPerformed(GuiButton button);
    void onGuiClosed();
    void handleMouseInput(int width, int height) throws IOException;
    void getSentHistory(int msgPos);
}