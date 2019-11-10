package com.stevekung.stevekungslib.client.gui;

import java.util.List;

import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.Widget;

public interface IChatScreen
{
    void init(List<Widget> buttons, List<IGuiEventListener> children, int width, int height);
    void render(List<Widget> buttons, int mouseX, int mouseY, float partialTicks);
    void tick(List<Widget> buttons, List<IGuiEventListener> children, int width, int height);
    void removed();
    boolean mouseScrolled(double mouseX, double mouseY, double scrollDelta);
}