package com.stevekung.stevekungslib.utils.client.command;

import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;

public interface IClientSuggestionProvider extends SharedSuggestionProvider
{
    void sendFeedback(Component text);
    void sendErrorMessage(Component text);
}