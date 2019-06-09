package stevekung.mods.stevekungslib.client.gui;

import java.util.List;

import net.minecraft.client.gui.widget.Widget;

public interface IGuiChat
{
    default void initGui(List<Widget> buttonList, int width, int height) {}
    default void render(List<Widget> buttonList, int mouseX, int mouseY, float partialTicks) {}
    default void tick(List<Widget> buttonList, int width, int height) {}
    default void keyTypedScrollDown() {}
    default void keyTypedScrollUp() {}
    default void mouseClicked(double mouseX, double mouseY, int mouseButton) {}
    default void mouseReleased(double mouseX, double mouseY, int state) {}
    default void mouseDragged(double mouseX, double mouseY, int mouseEvent, double dragX, double dragY) {}

    default boolean mouseScrolled(double wheel)
    {
        return false;
    }

    default void onGuiClosed() {}
    default void getSentHistory(int msgPos) {}
}