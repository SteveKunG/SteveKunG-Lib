package com.stevekung.stevekunglib.forge.utils.client.command;

import com.mojang.brigadier.CommandDispatcher;

public interface IClientCommand
{
    void register(CommandDispatcher<IClientSharedSuggestionProvider> dispatcher);
}