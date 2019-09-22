//package com.stevekung.stevekungslib.todo.mixin;
//
//import java.io.IOException;
//
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//import net.minecraft.client.gui.GuiChat;
//import net.minecraft.client.gui.GuiScreen;
//import net.minecraft.util.ITabCompleter;
//import stevekung.mods.stevekunglib.client.gui.GuiChatRegistry;
//import stevekung.mods.stevekunglib.client.gui.IGuiChat;
//
//@Mixin(GuiChat.class)
//public abstract class GuiChatMixin extends GuiScreen implements ITabCompleter
//{
//    @Inject(method = "initGui()V", at = @At("RETURN"))
//    private void initGui(CallbackInfo info)
//    {
//        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.initGui(this.buttonList, this.width, this.height));
//    }
//
//    @Inject(method = "drawScreen(IIF)V", at = @At("RETURN"))
//    private void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo info)
//    {
//        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.drawScreen(this.buttonList, mouseX, mouseY, partialTicks));
//    }
//
//    @Inject(method = "updateScreen()V", at = @At("RETURN"))
//    private void updateScreen(CallbackInfo info)
//    {
//        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.updateScreen(this.buttonList, this.width, this.height));
//    }
//
//    @Inject(method = "keyTyped(CI)V", cancellable = true, at = @At(value = "INVOKE", target = "net/minecraft/client/gui/GuiNewChat.scroll(I)V", shift = At.Shift.AFTER, ordinal = 0))
//    private void keyTypedPageUp(char typedChar, int keyCode, CallbackInfo ci)
//    {
//        GuiChatRegistry.getGuiChatList().forEach(IGuiChat::pageUp);
//    }
//
//    @Inject(method = "keyTyped(CI)V", cancellable = true, at = @At(value = "INVOKE", target = "net/minecraft/client/gui/GuiNewChat.scroll(I)V", shift = At.Shift.AFTER, ordinal = 1))
//    private void keyTypedPageDown(char typedChar, int keyCode, CallbackInfo ci)
//    {
//        GuiChatRegistry.getGuiChatList().forEach(IGuiChat::pageDown);
//    }
//
//    @Inject(method = "getSentHistory(I)V", at = @At("RETURN"))
//    private void getSentHistory(int msgPos, CallbackInfo info)
//    {
//        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.getSentHistory(msgPos));
//    }
//
//    @Inject(method = "mouseClicked(III)V", at = @At("RETURN"))
//    private void mouseClicked(int mouseX, int mouseY, int mouseButton, CallbackInfo info)
//    {
//        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.mouseClicked(mouseX, mouseY, mouseButton));
//    }
//
//    @Inject(method = "onGuiClosed()V", at = @At("RETURN"))
//    private void onGuiClosed(CallbackInfo info)
//    {
//        GuiChatRegistry.getGuiChatList().forEach(IGuiChat::onGuiClosed);
//    }
//
//    @Inject(method = "handleMouseInput()V", at = @At("RETURN"))
//    private void handleMouseInput(CallbackInfo info) throws IOException
//    {
//        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.handleMouseInput(this.width, this.height));
//    }
//}