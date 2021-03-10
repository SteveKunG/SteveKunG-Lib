package com.stevekung.stevekungslib.mixin.client.multiplayer;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import com.stevekung.stevekungslib.utils.client.command.IClientSuggestionProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.network.chat.Component;

@Mixin(ClientSuggestionProvider.class)
public abstract class MixinClientSuggestionProvider implements IClientSuggestionProvider
{
    @Shadow
    @Final
    private Minecraft minecraft;

    @Override
    public void sendFeedback(Component text)
    {
        this.minecraft.player.displayClientMessage(text, false);
    }

    @Override
    public void sendErrorMessage(Component text)
    {
        this.minecraft.player.displayClientMessage(text.copy().withStyle(ChatFormatting.RED), false);
    }
}