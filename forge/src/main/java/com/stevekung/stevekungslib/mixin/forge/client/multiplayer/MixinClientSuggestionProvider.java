package com.stevekung.stevekungslib.mixin.forge.client.multiplayer;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import com.stevekung.stevekungslib.utils.forge.client.command.IClientSharedSuggestionProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.network.chat.Component;

@Mixin(ClientSuggestionProvider.class)
public abstract class MixinClientSuggestionProvider implements IClientSharedSuggestionProvider
{
    @Shadow
    @Final
    Minecraft minecraft;

    @Override
    public void sendFeedback(Component component)
    {
        this.minecraft.player.displayClientMessage(component, false);
    }

    @Override
    public void sendErrorMessage(Component component)
    {
        this.minecraft.player.displayClientMessage(component.copy().withStyle(ChatFormatting.RED), false);
    }
}