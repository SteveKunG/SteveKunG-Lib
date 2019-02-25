package stevekung.mods.stevekungslib.client.gui;

import java.util.List;

import net.minecraft.client.gui.GuiButton;

interface IGuiChat
{
    void initGui(List<GuiButton> buttonList, int width, int height);
    void render(List<GuiButton> buttonList, int mouseX, int mouseY, float partialTicks);
    void tick(List<GuiButton> buttonList, int width, int height);
    void keyTypedScrollDown();
    void keyTypedScrollUp();
    void mouseClicked(double mouseX, double mouseY, int mouseButton);
    void mouseReleased(double mouseX, double mouseY, int state);
    void mouseDragged(double mouseX, double mouseY, int mouseEvent, double dragX, double dragY);
    boolean mouseScrolled(double wheel);
    void onGuiClosed();
    void getSentHistory(int msgPos);
}