package stevekung.mods.stevekunglib.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.ITextComponent;

public interface IGuiChat
{
    public void initGui();
    public void drawScreen(int mouseX, int mouseY, float partialTicks);
    public void updateScreen();
    public void keyTypedScrollDown();
    public void keyTypedScrollUp();
    public void mouseClicked(int mouseX, int mouseY, int mouseButton);
    public void mouseReleased(int mouseX, int mouseY, int state);
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick);
    public void actionPerformed(GuiButton button);
    public void handleComponentHover(ITextComponent component, int mouseX, int mouseY);
    public void onGuiClosed();
    public void handleMouseInput();
    public void getSentHistory(int msgPos);
}