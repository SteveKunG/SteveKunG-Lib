package com.stevekung.stevekungslib.utils.client.command;

import com.mojang.brigadier.CommandDispatcher;

public interface IClientCommand
{
    void register(CommandDispatcher<IClientSharedSuggestionProvider> dispatcher);
}