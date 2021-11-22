package com.stevekung.stevekunglib.forge.mixin.client.multiplayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.stevekung.stevekunglib.forge.utils.client.command.ClientCommands;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundCommandsPacket;

@Mixin(ClientPacketListener.class)
public class MixinClientPacketListener
{
    @Shadow
    CommandDispatcher<SharedSuggestionProvider> commands;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void stevekung_lib$init(Minecraft mc, Screen screen, Connection connection, GameProfile profile, CallbackInfo info)
    {
        ClientCommands.buildSuggestion(this.commands);
        ClientCommands.buildDispatcher();
    }

    @Inject(method = "handleCommands", at = @At("TAIL"))
    private void stevekung_lib$handleCommands(ClientboundCommandsPacket packet, CallbackInfo info)
    {
        ClientCommands.buildSuggestion(this.commands);
    }
}