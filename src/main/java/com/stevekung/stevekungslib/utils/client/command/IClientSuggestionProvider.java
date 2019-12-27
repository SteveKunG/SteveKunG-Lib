package com.stevekung.stevekungslib.utils.client.command;

import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.text.ITextComponent;

public interface IClientSuggestionProvider extends ISuggestionProvider
{
    void sendFeedback(ITextComponent text);
    void sendErrorMessage(ITextComponent text);
}