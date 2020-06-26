package com.stevekung.stevekungslib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.blaze3d.matrix.MatrixStack;
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
    public void func_231164_f_()
    {
        super.func_231164_f_();
        CommonUtils.post(new ChatScreenEvent.Close(this.field_230710_m_, this.field_230705_e_));
    }

    @Inject(method = "func_231160_c_()V", at = @At("RETURN"))
    private void init(CallbackInfo info)
    {
        CommonUtils.post(new ChatScreenEvent.Init(this.field_230710_m_, this.field_230705_e_, this.field_230708_k_, this.field_230709_l_));
    }

    @Inject(method = "func_230430_a_(Lcom/mojang/blaze3d/matrix/MatrixStack;IIF)V", at = @At("HEAD"))
    private void renderPre(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, CallbackInfo info)
    {
        CommonUtils.post(new ChatScreenEvent.RenderPre(this.field_230710_m_, this.field_230705_e_, mouseX, mouseY, partialTicks));
    }

    @Inject(method = "func_230430_a_(Lcom/mojang/blaze3d/matrix/MatrixStack;IIF)V", at = @At("RETURN"))
    private void renderPost(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, CallbackInfo info)
    {
        CommonUtils.post(new ChatScreenEvent.RenderPost(this.field_230710_m_, this.field_230705_e_, mouseX, mouseY, partialTicks));
    }

    @Inject(method = "func_231023_e_()V", at = @At("RETURN"))
    private void tick(CallbackInfo info)
    {
        CommonUtils.post(new ChatScreenEvent.Tick(this.field_230710_m_, this.field_230705_e_, this.field_230708_k_, this.field_230709_l_));
    }

    @Inject(method = "func_231043_a_(DDD)Z", cancellable = true, at = @At("HEAD"))
    private void mouseScrolled(double mouseX, double mouseY, double scrollDelta, CallbackInfoReturnable<Boolean> info)
    {
        ChatScreenEvent.MouseScroll event = new ChatScreenEvent.MouseScroll(this.field_230710_m_, this.field_230705_e_, mouseX, mouseY, scrollDelta);

        if (CommonUtils.post(event))
        {
            info.setReturnValue(false);
        }
    }
}