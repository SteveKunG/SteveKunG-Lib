package com.stevekung.stevekungslib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.stevekung.stevekungslib.client.gui.ChatScreenRegistry;
import com.stevekung.stevekungslib.client.gui.IChatScreen;

import net.minecraft.client.gui.screen.Screen;

@Mixin(Screen.class)
public abstract class MixinScreen
{
    @Inject(method = "onClose()V", at = @At("RETURN"))
    private void onClose(CallbackInfo info)
    {
        ChatScreenRegistry.getChatScreen().forEach(IChatScreen::onClose);
    }
}