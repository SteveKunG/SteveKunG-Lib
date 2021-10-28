package com.stevekung.stevekungslib.utils.forge.client.command;

import com.mojang.brigadier.CommandDispatcher;

public interface IClientCommand
{
    void register(CommandDispatcher<IClientSharedSuggestionProvider> dispatcher);
}