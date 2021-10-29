package com.stevekung.stevekungslib.forge.utils.client.command;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

@SuppressWarnings("unchecked")
public class ClientCommands
{
    private static final CommandDispatcher<IClientSharedSuggestionProvider> DISPATCHER = new CommandDispatcher<>();
    private static final List<IClientCommand> CLIENT_COMMANDS = Lists.newArrayList();

    public static void register(IClientCommand command)
    {
        ClientCommands.CLIENT_COMMANDS.add(command);
    }

    private static List<IClientCommand> getCommands()
    {
        return ClientCommands.CLIENT_COMMANDS;
    }

    public static LiteralArgumentBuilder<IClientSharedSuggestionProvider> literal(String name)
    {
        return LiteralArgumentBuilder.literal(name);
    }

    public static <T> RequiredArgumentBuilder<IClientSharedSuggestionProvider, T> argument(String name, ArgumentType<T> type)
    {
        return RequiredArgumentBuilder.argument(name, type);
    }

    public static void buildDispatcher()
    {
        ClientCommands.getCommands().forEach(command -> command.register(DISPATCHER));
    }

    public static void buildSuggestion(CommandDispatcher dispatcher)
    {
        ClientCommands.getCommands().forEach(command -> command.register(dispatcher));
    }

    public static int execute(String input, IClientSharedSuggestionProvider provider) throws CommandSyntaxException
    {
        return DISPATCHER.execute(input, provider);
    }

    public static boolean hasCommand(String name)
    {
        return DISPATCHER.findNode(Collections.singleton(name)) != null;
    }
}