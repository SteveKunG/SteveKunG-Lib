package com.stevekung.stevekungslib.client.event;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;

public interface ScreenEvents
{
    Event<ChatScreenInitEvent> CHAT_SCREEN_INIT = EventFactory.createLoop();
    Event<ChatScreenCloseEvent> CHAT_SCREEN_CLOSE = EventFactory.createLoop();
    Event<ChatScreenTickEvent> CHAT_SCREEN_TICK = EventFactory.createLoop();
    Event<ChatScreenMouseScrollEvent> CHAT_SCREEN_MOUSE_SCROLL = EventFactory.createEventResult();
    Event<ChatScreenRenderPreEvent> CHAT_SCREEN_RENDER_PRE = EventFactory.createLoop();
    Event<ChatScreenRenderPostEvent> CHAT_SCREEN_RENDER_POST = EventFactory.createLoop();

    @Environment(EnvType.CLIENT)
    interface ChatScreenInitEvent
    {
        void init(List<Widget> renderables, List<GuiEventListener> children, int width, int height);
    }

    @Environment(EnvType.CLIENT)
    interface ChatScreenCloseEvent
    {
        void close(List<Widget> renderables, List<GuiEventListener> children);
    }

    @Environment(EnvType.CLIENT)
    interface ChatScreenTickEvent
    {
        void tick(List<Widget> renderables, List<GuiEventListener> children, int width, int height);
    }

    @Environment(EnvType.CLIENT)
    interface ChatScreenMouseScrollEvent
    {
        EventResult mouseScroll(List<Widget> renderables, List<GuiEventListener> children, double mouseX, double mouseY, double scrollDelta);
    }

    @Environment(EnvType.CLIENT)
    interface ChatScreenRenderPreEvent
    {
        void renderPre(List<Widget> renderables, List<GuiEventListener> children, PoseStack poseStack, int mouseX, int mouseY, float partialTicks);
    }

    @Environment(EnvType.CLIENT)
    interface ChatScreenRenderPostEvent
    {
        void renderPost(List<Widget> renderables, List<GuiEventListener> children, PoseStack poseStack, int mouseX, int mouseY, float partialTicks);
    }
}