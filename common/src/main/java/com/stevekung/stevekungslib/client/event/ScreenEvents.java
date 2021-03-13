package com.stevekung.stevekungslib.client.event;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import me.shedaniel.architectury.event.Event;
import me.shedaniel.architectury.event.EventFactory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.world.InteractionResult;

public interface ScreenEvents
{
    Event<ChatScreenInitEvent> CHAT_SCREEN_INIT = EventFactory.createLoop();
    Event<ChatScreenCloseEvent> CHAT_SCREEN_CLOSE = EventFactory.createLoop();
    Event<ChatScreenTickEvent> CHAT_SCREEN_TICK = EventFactory.createLoop();
    Event<ChatScreenMouseScrollEvent> CHAT_SCREEN_MOUSE_SCROLL = EventFactory.createInteractionResult();
    Event<ChatScreenRenderPreEvent> CHAT_SCREEN_RENDER_PRE = EventFactory.createLoop();
    Event<ChatScreenRenderPostEvent> CHAT_SCREEN_RENDER_POST = EventFactory.createLoop();

    @Environment(EnvType.CLIENT)
    interface ChatScreenInitEvent
    {
        void init(List<AbstractWidget> buttons, List<GuiEventListener> children, int width, int height);
    }

    @Environment(EnvType.CLIENT)
    interface ChatScreenCloseEvent
    {
        void close(List<AbstractWidget> buttons, List<GuiEventListener> children);
    }

    @Environment(EnvType.CLIENT)
    interface ChatScreenTickEvent
    {
        void tick(List<AbstractWidget> buttons, List<GuiEventListener> children, int width, int height);
    }

    @Environment(EnvType.CLIENT)
    interface ChatScreenMouseScrollEvent
    {
        InteractionResult mouseScroll(List<AbstractWidget> buttons, List<GuiEventListener> children, double mouseX, double mouseY, double scrollDelta);
    }

    @Environment(EnvType.CLIENT)
    interface ChatScreenRenderPreEvent
    {
        void renderPre(List<AbstractWidget> buttons, List<GuiEventListener> children, PoseStack poseStack, int mouseX, int mouseY, float partialTicks);
    }

    @Environment(EnvType.CLIENT)
    interface ChatScreenRenderPostEvent
    {
        void renderPost(List<AbstractWidget> buttons, List<GuiEventListener> children, PoseStack poseStack, int mouseX, int mouseY, float partialTicks);
    }
}