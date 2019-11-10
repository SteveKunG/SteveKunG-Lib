package com.stevekung.stevekungslib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.stevekung.stevekungslib.client.gui.ChatScreenRegistry;
import com.stevekung.stevekungslib.client.gui.IChatScreen;

import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

@Mixin(ChatScreen.class)
public abstract class MixinChatScreen extends Screen
{
    public MixinChatScreen(ITextComponent title)
    {
        super(title);
    }

    @Inject(method = "init()V", at = @At("RETURN"))
    private void init(CallbackInfo info)
    {
        ChatScreenRegistry.getChatScreen().forEach(gui -> gui.init(this.buttons, this.children, this.width, this.height));
    }

    @Inject(method = "render(IIF)V", at = @At("RETURN"))
    private void render(int mouseX, int mouseY, float partialTicks, CallbackInfo info)
    {
        ChatScreenRegistry.getChatScreen().forEach(gui -> gui.render(this.buttons, mouseX, mouseY, partialTicks));
    }

    @Inject(method = "tick()V", at = @At("RETURN"))
    private void tick(CallbackInfo info)
    {
        ChatScreenRegistry.getChatScreen().forEach(gui -> gui.tick(this.buttons, this.children, this.width, this.height));
    }

    @Inject(method = "removed()V", at = @At("RETURN"))
    private void removed(CallbackInfo info)
    {
        ChatScreenRegistry.getChatScreen().forEach(IChatScreen::removed);
    }

    @Inject(method = "mouseScrolled(DDD)Z", at = @At("RETURN"))
    private void mouseScrolled(double mouseX, double mouseY, double scrollDelta, CallbackInfoReturnable<Boolean> info)
    {
        ChatScreenRegistry.getChatScreen().forEach(gui -> gui.mouseScrolled(mouseX, mouseY, scrollDelta));
    }
}