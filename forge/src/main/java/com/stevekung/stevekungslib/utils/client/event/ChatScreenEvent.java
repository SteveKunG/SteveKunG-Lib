package com.stevekung.stevekungslib.utils.client.event;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

public class ChatScreenEvent extends Event
{
    private final List<AbstractWidget> buttons;
    private final List<GuiEventListener> children;

    public ChatScreenEvent(List<AbstractWidget> buttons, List<GuiEventListener> children)
    {
        this.buttons = buttons;
        this.children = children;
    }

    public <T extends AbstractWidget> T addButton(T widget)
    {
        this.buttons.add(widget);
        this.children.add(widget);
        return widget;
    }

    public List<AbstractWidget> getButtons()
    {
        return this.buttons;
    }

    public List<GuiEventListener> getChildren()
    {
        return this.children;
    }

    public static class Init extends ChatScreenEvent
    {
        private final int width;
        private final int height;

        public Init(List<AbstractWidget> buttons, List<GuiEventListener> children, int width, int height)
        {
            super(buttons, children);
            this.width = width;
            this.height = height;
        }

        public int getWidth()
        {
            return this.width;
        }

        public int getHeight()
        {
            return this.height;
        }
    }

    public static class Close extends ChatScreenEvent
    {
        public Close(List<AbstractWidget> buttons, List<GuiEventListener> children)
        {
            super(buttons, children);
        }
    }

    public static class Tick extends ChatScreenEvent
    {
        private final int width;
        private final int height;

        public Tick(List<AbstractWidget> buttons, List<GuiEventListener> children, int width, int height)
        {
            super(buttons, children);
            this.width = width;
            this.height = height;
        }

        public int getWidth()
        {
            return this.width;
        }

        public int getHeight()
        {
            return this.height;
        }
    }

    @Cancelable
    public static class MouseScroll extends ChatScreenEvent
    {
        private final double mouseX;
        private final double mouseY;
        private final double scrollDelta;

        public MouseScroll(List<AbstractWidget> buttons, List<GuiEventListener> children, double mouseX, double mouseY, double scrollDelta)
        {
            super(buttons, children);
            this.mouseX = mouseX;
            this.mouseY = mouseY;
            this.scrollDelta = scrollDelta;
        }

        public double getMouseX()
        {
            return this.mouseX;
        }

        public double getMouseY()
        {
            return this.mouseY;
        }

        public double getScrollDelta()
        {
            return this.scrollDelta;
        }
    }

    public static class RenderPre extends ChatScreenEvent
    {
        private final PoseStack matrixStack;
        private final int mouseX;
        private final int mouseY;
        private final float partialTicks;

        public RenderPre(List<AbstractWidget> buttons, List<GuiEventListener> children, PoseStack matrixStack, int mouseX, int mouseY, float partialTicks)
        {
            super(buttons, children);
            this.matrixStack = matrixStack;
            this.mouseX = mouseX;
            this.mouseY = mouseY;
            this.partialTicks = partialTicks;
        }

        public PoseStack getMatrixStack()
        {
            return this.matrixStack;
        }

        public int getMouseX()
        {
            return this.mouseX;
        }

        public int getMouseY()
        {
            return this.mouseY;
        }

        public float getPartialTicks()
        {
            return this.partialTicks;
        }
    }

    public static class RenderPost extends ChatScreenEvent
    {
        private final PoseStack matrixStack;
        private final int mouseX;
        private final int mouseY;
        private final float partialTicks;

        public RenderPost(List<AbstractWidget> buttons, List<GuiEventListener> children, PoseStack matrixStack, int mouseX, int mouseY, float partialTicks)
        {
            super(buttons, children);
            this.matrixStack = matrixStack;
            this.mouseX = mouseX;
            this.mouseY = mouseY;
            this.partialTicks = partialTicks;
        }

        public PoseStack getMatrixStack()
        {
            return this.matrixStack;
        }

        public int getMouseX()
        {
            return this.mouseX;
        }

        public int getMouseY()
        {
            return this.mouseY;
        }

        public float getPartialTicks()
        {
            return this.partialTicks;
        }
    }
}