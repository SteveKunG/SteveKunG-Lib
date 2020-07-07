package com.stevekung.stevekungslib.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import com.stevekung.stevekungslib.utils.client.command.IClientSuggestionProvider;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

@Mixin(ClientSuggestionProvider.class)
public abstract class MixinClientSuggestionProvider implements IClientSuggestionProvider
{
    @Shadow
    @Final
    @Mutable
    private Minecraft mc;

    @Override
    public void sendFeedback(ITextComponent text)
    {
        this.mc.player.sendStatusMessage(text, false);
    }

    @Override
    public void sendErrorMessage(ITextComponent text)
    {
        this.mc.player.sendStatusMessage(text.deepCopy().func_240699_a_(TextFormatting.RED), false);//TODO Test
    }
}