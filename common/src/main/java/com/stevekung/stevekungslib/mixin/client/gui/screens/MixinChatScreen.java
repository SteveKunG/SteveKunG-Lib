package com.stevekung.stevekungslib.mixin.client.gui.screens;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.stevekungslib.client.event.ScreenEvents;
import dev.architectury.event.EventResult;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;

@Mixin(ChatScreen.class)
public class MixinChatScreen extends Screen
{
    MixinChatScreen()
    {
        super(null);
    }

    @Override
    public void removed()
    {
        super.removed();
        ScreenEvents.CHAT_SCREEN_CLOSE.invoker().close(this.renderables, this.children);
    }

    @Inject(method = "init()V", at = @At("TAIL"))
    private void init(CallbackInfo info)
    {
        ScreenEvents.CHAT_SCREEN_INIT.invoker().init(this.renderables, this.children, this.width, this.height);
    }

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V", at = @At("HEAD"))
    private void renderPre(PoseStack poseStack, int mouseX, int mouseY, float partialTicks, CallbackInfo info)
    {
        ScreenEvents.CHAT_SCREEN_RENDER_PRE.invoker().renderPre(this.renderables, this.children, poseStack, mouseX, mouseY, partialTicks);
    }

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V", at = @At("TAIL"))
    private void renderPost(PoseStack poseStack, int mouseX, int mouseY, float partialTicks, CallbackInfo info)
    {
        ScreenEvents.CHAT_SCREEN_RENDER_POST.invoker().renderPost(this.renderables, this.children, poseStack, mouseX, mouseY, partialTicks);
    }

    @Inject(method = "tick()V", at = @At("TAIL"))
    private void tick(CallbackInfo info)
    {
        ScreenEvents.CHAT_SCREEN_TICK.invoker().tick(this.renderables, this.children, this.width, this.height);
    }

    @Inject(method = "mouseScrolled(DDD)Z", cancellable = true, at = @At("HEAD"))
    private void mouseScrolled(double mouseX, double mouseY, double scrollDelta, CallbackInfoReturnable<Boolean> info)
    {
        var event = ScreenEvents.CHAT_SCREEN_MOUSE_SCROLL.invoker().mouseScroll(this.renderables, this.children, mouseX, mouseY, scrollDelta);

        if (event == EventResult.interruptTrue())
        {
            info.setReturnValue(false);
        }
    }
}