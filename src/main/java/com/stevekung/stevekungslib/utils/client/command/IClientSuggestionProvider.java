package com.stevekung.stevekungslib.utils.client.command;

import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.text.TextComponent;

public interface IClientSuggestionProvider extends ISuggestionProvider
{
    void sendFeedback(TextComponent text);
    void sendErrorMessage(TextComponent text);
}