package com.stevekung.stevekungslib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.stevekung.stevekungslib.utils.client.command.ClientCommands;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SCommandListPacket;

@Mixin(ClientPlayNetHandler.class)
public class MixinClientPlayNetHandler
{
    @Shadow
    private CommandDispatcher<ISuggestionProvider> commandDispatcher;

    @Inject(method = "<init>(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/gui/screen/Screen;Lnet/minecraft/network/NetworkManager;Lcom/mojang/authlib/GameProfile;)V", at = @At("RETURN"))
    private void onConstruct(Minecraft mc, Screen screen, NetworkManager manager, GameProfile profile, CallbackInfo info)
    {
        ClientCommands.buildSuggestion(this.commandDispatcher);
        ClientCommands.buildDispatcher();
    }

    @Inject(method = "handleCommandList(Lnet/minecraft/network/play/server/SCommandListPacket;)V", at = @At("RETURN"))
    private void onCommandTree(SCommandListPacket packet, CallbackInfo info)
    {
        ClientCommands.buildSuggestion(this.commandDispatcher);
    }
}