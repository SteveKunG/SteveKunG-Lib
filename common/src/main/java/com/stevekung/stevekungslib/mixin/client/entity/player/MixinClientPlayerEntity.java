package com.stevekung.stevekungslib.mixin.client.entity.player;

import com.stevekung.stevekungslib.utils.client.command.ClientCommands;
import com.stevekung.stevekungslib.utils.client.command.IClientSuggestionProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

@Mixin(LocalPlayer.class)
public class MixinClientPlayerEntity
{
    private final LocalPlayer that = (LocalPlayer) (Object) this;

    @Shadow
    @Final
    protected Minecraft minecraft;

    @Inject(method = "chat(Ljava/lang/String;)V", at = @At("HEAD"), cancellable = true)
    private void chat(String message, CallbackInfo info)
    {
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
            int result = ClientCommands.execute(message.substring(1), (IClientSuggestionProvider) new ClientSuggestionProvider(this.that.connection, this.minecraft));

            if (result != 0)
            {
                info.cancel(); // Prevent sending the message
            }
        }
        catch (CommandRuntimeException e)
        {
            this.that.displayClientMessage(e.getComponent().copy().withStyle(ChatFormatting.RED), false);
            info.cancel();
        }
        catch (CommandSyntaxException e)
        {
            this.that.displayClientMessage(new TextComponent(e.getContext()).withStyle(ChatFormatting.RED), false);
            info.cancel();
        }
        catch (Exception e)
        {
            this.that.displayClientMessage(new TranslatableComponent("command.failed").withStyle(ChatFormatting.RED), false);
            info.cancel();
        }
    }
}