package com.stevekung.stevekungslib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.stevekung.stevekungslib.client.event.ChatScreenEvent;
import com.stevekung.stevekungslib.utils.CommonUtils;

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
        CommonUtils.post(new ChatScreenEvent.Close(this.buttons, this.children));
    }

    @Inject(method = "init()V", at = @At("RETURN"))
    private void init(CallbackInfo info)
    {
        CommonUtils.post(new ChatScreenEvent.Init(this.buttons, this.children, this.width, this.height));
    }

    @Inject(method = "render(IIF)V", at = @At("HEAD"))
    private void renderPre(int mouseX, int mouseY, float partialTicks, CallbackInfo info)
    {
        CommonUtils.post(new ChatScreenEvent.RenderPre(this.buttons, this.children, mouseX, mouseY, partialTicks));
    }

    @Inject(method = "render(IIF)V", at = @At("RETURN"))
    private void renderPost(int mouseX, int mouseY, float partialTicks, CallbackInfo info)
    {
        CommonUtils.post(new ChatScreenEvent.RenderPost(this.buttons, this.children, mouseX, mouseY, partialTicks));
    }

    @Inject(method = "tick()V", at = @At("RETURN"))
    private void tick(CallbackInfo info)
    {
        CommonUtils.post(new ChatScreenEvent.Tick(this.buttons, this.children, this.width, this.height));
    }

    @Inject(method = "mouseScrolled(DDD)Z", cancellable = true, at = @At("HEAD"))
    private void mouseScrolled(double mouseX, double mouseY, double scrollDelta, CallbackInfoReturnable<Boolean> info)
    {
        ChatScreenEvent.MouseScroll event = new ChatScreenEvent.MouseScroll(this.buttons, this.children, mouseX, mouseY, scrollDelta);

        if (CommonUtils.post(event))
        {
            info.setReturnValue(false);
        }
    }
}