package com.stevekung.stevekungslib.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.stevekung.stevekungslib.utils.client.command.IClientSuggestionProvider;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;

@Mixin(ClientSuggestionProvider.class)
public abstract class MixinClientSuggestionProvider implements IClientSuggestionProvider
{
    @Shadow
    @Final
    private Minecraft mc;

    @Override
    public void sendFeedback(TextComponent text)
    {
        this.mc.player.sendStatusMessage(text, false);
    }

    @Override
    public void sendErrorMessage(TextComponent text)
    {
        this.mc.player.sendStatusMessage(text.applyTextStyle(TextFormatting.RED), false);
    }
}