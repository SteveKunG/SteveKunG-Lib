package com.stevekung.stevekungslib.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.stevekung.stevekungslib.utils.client.command.ClientCommands;
import com.stevekung.stevekungslib.utils.client.command.IClientSuggestionProvider;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.command.CommandException;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

@Mixin(ClientPlayerEntity.class)
public abstract class MixinClientPlayerEntity
{
    private final ClientPlayerEntity that = (ClientPlayerEntity) (Object) this;

    @Shadow
    @Final
    @Mutable
    protected Minecraft mc;

    @Inject(method = "sendChatMessage(Ljava/lang/String;)V", at = @At("HEAD"), cancellable = true)
    private void sendChatMessage(String message, CallbackInfo info)
    {
        if (message.length() < 2 || !message.startsWith("/"))
        {
            return;
        }
        if (!ClientCommands.hasCommand(message.substring(1).split(" ")[0]))
        {
            return;
        }

        boolean cancel = false;

        try
        {
            // The game freezes when using heavy commands. Run your heavy code somewhere else pls
            int result = ClientCommands.execute(message.substring(1), (IClientSuggestionProvider) new ClientSuggestionProvider(this.that.connection, this.mc));

            if (result != 0)
            {
                cancel = true; // Prevent sending the message
            }
        }
        catch (CommandException e)
        {
            this.that.sendStatusMessage(e.getComponent().func_230531_f_().func_240699_a_(TextFormatting.RED), false);
            cancel = true;
        }
        catch (CommandSyntaxException e)
        {
            this.that.sendStatusMessage(new StringTextComponent(e.getContext()).func_240699_a_(TextFormatting.RED), false);
            cancel = true;
        }
        catch (Exception e)
        {
            this.that.sendStatusMessage(new TranslationTextComponent("command.failed").func_240699_a_(TextFormatting.RED), false);
            cancel = true;
        }

        if (cancel)
        {
            info.cancel();
        }
    }
}