package com.stevekung.stevekungslib.forge.mixin.client.player;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.stevekung.stevekungslib.forge.utils.client.command.ClientCommands;
import com.stevekung.stevekungslib.forge.utils.client.command.IClientSharedSuggestionProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

@Mixin(LocalPlayer.class)
public class MixinLocalPlayer
{
    @Shadow
    @Final
    Minecraft minecraft;

    @Inject(method = "chat(Ljava/lang/String;)V", at = @At("HEAD"), cancellable = true)
    private void chat(String message, CallbackInfo info)
    {
        var player = (LocalPlayer) (Object) this;

        if (message.length() < 2 || !message.startsWith("/"))
        {
            return;
        }
        if (!ClientCommands.hasCommand(message.substring(1).split(" ")[0]))
        {
            return;
        }

        try
        {
            // The game freezes when using heavy commands. Run your heavy code somewhere else pls
            var result = ClientCommands.execute(message.substring(1), (IClientSharedSuggestionProvider) new ClientSuggestionProvider(player.connection, this.minecraft));

            if (result != 0)
            {
                info.cancel(); // Prevent sending the message
            }
        }
        catch (CommandRuntimeException e)
        {
            player.displayClientMessage(e.getComponent().copy().withStyle(ChatFormatting.RED), false);
            info.cancel();
        }
        catch (CommandSyntaxException e)
        {
            player.displayClientMessage(new TextComponent(e.getContext()).withStyle(ChatFormatting.RED), false);
            info.cancel();
        }
        catch (Exception e)
        {
            player.displayClientMessage(new TranslatableComponent("command.failed").withStyle(ChatFormatting.RED), false);
            info.cancel();
        }
    }
}