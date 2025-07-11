package com.stevekung.lib.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.GuiScreen;
import com.stevekung.lib.client.gui.GuiChatRegistry;

@Mixin(GuiScreen.class)
public class GuiScreenMixin
{
    @Inject(method = "mouseReleased", at = @At("TAIL"))
    private void stevekung_lib$mouseReleased(int mouseX, int mouseY, int mouseButton, CallbackInfo info)
    {
        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.mouseReleased(mouseX, mouseY, mouseButton));
    }

    @Inject(method = "mouseClickMove", at = @At("TAIL"))
    private void stevekung_lib$mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick, CallbackInfo info)
    {
        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick));
    }
}