package com.stevekung.stevekungslib.mixin.client.multiplayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.stevekung.stevekungslib.utils.client.command.ClientCommands;
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
    private CommandDispatcher<SharedSuggestionProvider> commands;

    @Inject(method = "<init>(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/gui/screens/Screen;Lnet/minecraft/network/Connection;Lcom/mojang/authlib/GameProfile;)V", at = @At("TAIL"))
    private void init(Minecraft mc, Screen screen, Connection connection, GameProfile profile, CallbackInfo info)
    {
        ClientCommands.buildSuggestion(this.commands);
        ClientCommands.buildDispatcher();
    }

    @Inject(method = "handleCommands(Lnet/minecraft/network/protocol/game/ClientboundCommandsPacket;)V", at = @At("TAIL"))
    private void handleCommands(ClientboundCommandsPacket packet, CallbackInfo info)
    {
        ClientCommands.buildSuggestion(this.commands);
    }
}