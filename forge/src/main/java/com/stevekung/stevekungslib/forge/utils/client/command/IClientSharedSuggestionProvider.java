package com.stevekung.stevekungslib.forge.utils.client.command;

import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;

public interface IClientSharedSuggestionProvider extends SharedSuggestionProvider
{
    void sendFeedback(Component component);

    void sendErrorMessage(Component component);
}