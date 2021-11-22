package com.stevekung.stevekunglib.mixin.client.gui.screens;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.stevekunglib.client.event.ScreenEvents;
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

    @Inject(method = "init", at = @At("TAIL"))
    private void stevekung_lib$init(CallbackInfo info)
    {
        ScreenEvents.CHAT_SCREEN_INIT.invoker().init(this.renderables, this.children, this.width, this.height);
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void stevekung_lib$renderPre(PoseStack poseStack, int mouseX, int mouseY, float partialTicks, CallbackInfo info)
    {
        ScreenEvents.CHAT_SCREEN_RENDER_PRE.invoker().renderPre(this.renderables, this.children, poseStack, mouseX, mouseY, partialTicks);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void stevekung_lib$renderPost(PoseStack poseStack, int mouseX, int mouseY, float partialTicks, CallbackInfo info)
    {
        ScreenEvents.CHAT_SCREEN_RENDER_POST.invoker().renderPost(this.renderables, this.children, poseStack, mouseX, mouseY, partialTicks);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void stevekung_lib$tick(CallbackInfo info)
    {
        ScreenEvents.CHAT_SCREEN_TICK.invoker().tick(this.renderables, this.children, this.width, this.height);
    }

    @Inject(method = "mouseScrolled", cancellable = true, at = @At("HEAD"))
    private void stevekung_lib$mouseScrolled(double mouseX, double mouseY, double scrollDelta, CallbackInfoReturnable<Boolean> info)
    {
        var event = ScreenEvents.CHAT_SCREEN_MOUSE_SCROLL.invoker().mouseScroll(this.renderables, this.children, mouseX, mouseY, scrollDelta);

        if (event == EventResult.interruptTrue())
        {
            info.setReturnValue(false);
        }
    }
}