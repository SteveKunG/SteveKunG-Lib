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

    @Override
    public void onClose()
    {
        super.onClose();
        ChatScreenRegistry.getChatScreen().forEach(IChatScreen::onClose);
    }

    @Inject(method = "init()V", at = @At("RETURN"))
    private void init(CallbackInfo info)
    {
        ChatScreenRegistry.getChatScreen().forEach(screen -> screen.init(this.buttons, this.children, this.width, this.height));
    }

    @Inject(method = "render(IIF)V", at = @At("HEAD"))
    private void render(int mouseX, int mouseY, float partialTicks, CallbackInfo info)
    {
        ChatScreenRegistry.getChatScreen().forEach(screen -> screen.render(this.buttons, mouseX, mouseY, partialTicks));
    }

    @Inject(method = "tick()V", at = @At("RETURN"))
    private void tick(CallbackInfo info)
    {
        ChatScreenRegistry.getChatScreen().forEach(screen -> screen.tick(this.buttons, this.children, this.width, this.height));
    }

    @Inject(method = "mouseScrolled(DDD)Z", cancellable = true, at = @At("RETURN"))
    private void mouseScrolled(double mouseX, double mouseY, double scrollDelta, CallbackInfoReturnable<Boolean> info)
    {
        for (IChatScreen screen : ChatScreenRegistry.getChatScreen())
        {
            info.setReturnValue(screen.mouseScrolled(mouseX, mouseY, scrollDelta));
        }
    }
}