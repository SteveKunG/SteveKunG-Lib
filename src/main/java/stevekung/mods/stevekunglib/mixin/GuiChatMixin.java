package stevekung.mods.stevekunglib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import stevekung.mods.stevekunglib.client.gui.GuiChatRegistry;
import stevekung.mods.stevekunglib.client.gui.IGuiChat;

@Mixin(GuiChat.class)
public class GuiChatMixin extends GuiScreen
{
    @Inject(method = "initGui", at = @At("TAIL"))
    private void stevekunglib$initGui(CallbackInfo info)
    {
        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.initGui(this.buttonList, this.width, this.height));
    }

    @Inject(method = "drawScreen", at = @At("TAIL"))
    private void stevekunglib$drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo info)
    {
        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.drawScreen(this.buttonList, mouseX, mouseY, partialTicks));
    }

    @Inject(method = "updateScreen", at = @At("TAIL"))
    private void stevekunglib$updateScreen(CallbackInfo info)
    {
        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.updateScreen(this.buttonList, this.width, this.height));
    }

    @Inject(method = "keyTyped", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/GuiNewChat.scroll(I)V", shift = At.Shift.AFTER, ordinal = 0))
    private void stevekunglib$keyTypedPageUp(char typedChar, int keyCode, CallbackInfo ci)
    {
        GuiChatRegistry.getGuiChatList().forEach(IGuiChat::pageUp);
    }

    @Inject(method = "keyTyped", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/GuiNewChat.scroll(I)V", shift = At.Shift.AFTER, ordinal = 1))
    private void stevekunglib$keyTypedPageDown(char typedChar, int keyCode, CallbackInfo ci)
    {
        GuiChatRegistry.getGuiChatList().forEach(IGuiChat::pageDown);
    }

    @Inject(method = "getSentHistory", at = @At("TAIL"))
    private void stevekunglib$getSentHistory(int msgPos, CallbackInfo info)
    {
        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.getSentHistory(msgPos));
    }

    @Inject(method = "mouseClicked", at = @At("TAIL"))
    private void stevekunglib$mouseClicked(int mouseX, int mouseY, int mouseButton, CallbackInfo info)
    {
        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.mouseClicked(mouseX, mouseY, mouseButton));
    }

    @Inject(method = "onGuiClosed", at = @At("TAIL"))
    private void stevekunglib$onGuiClosed(CallbackInfo info)
    {
        GuiChatRegistry.getGuiChatList().forEach(IGuiChat::onGuiClosed);
    }

    @Inject(method = "handleMouseInput", at = @At("TAIL"))
    private void stevekunglib$handleMouseInput(CallbackInfo info)
    {
        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.handleMouseInput(this.width, this.height));
    }
}