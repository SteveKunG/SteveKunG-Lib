//package stevekung.mods.stevekungslib.todo.mixin;
//
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//import net.minecraft.client.gui.Gui;
//import net.minecraft.client.gui.GuiScreen;
//import stevekung.mods.stevekunglib.client.gui.GuiChatRegistry;
//
//@Mixin(GuiScreen.class)
//public abstract class GuiScreenMixin extends Gui
//{
//    @Inject(method = "mouseReleased(III)V", at = @At("RETURN"))
//    private void mouseReleased(int mouseX, int mouseY, int mouseButton, CallbackInfo info)
//    {
//        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.mouseReleased(mouseX, mouseY, mouseButton));
//    }
//
//    @Inject(method = "mouseClickMove(IIIJ)V", at = @At("RETURN"))
//    private void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick, CallbackInfo info)
//    {
//        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick));
//    }
//}