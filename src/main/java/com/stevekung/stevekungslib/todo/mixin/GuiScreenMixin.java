//package com.stevekung.stevekungslib.todo.mixin;
//
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//import com.stevekung.stevekungslib.client.gui.ChatScreenRegistry;
//
//import net.minecraft.client.gui.INestedGuiEventHandler;
//
//@Mixin(INestedGuiEventHandler.class)
//public abstract class GuiScreenMixin
//{
//    private final INestedGuiEventHandler that = (INestedGuiEventHandler) this;
//
//    @Inject(method = "mouseReleased(DDI)Z", at = @At("RETURN"))
//    private void mouseReleased(double mouseX, double mouseY, int mouseButton, CallbackInfoReturnable info)
//    {
//        ChatScreenRegistry.getChatScreen().forEach(gui -> gui.mouseReleased(mouseX, mouseY, mouseButton));
//
//        info.setReturnValue(this.that.getEventListenerForPos(mouseX, mouseY).filter(listener ->
//        {
//            return listener.mouseReleased(mouseX, mouseY, mouseButton);
//        }).isPresent());
//    }
//
//    @Inject(method = "mouseClickMove(IIIJ)V", at = @At("RETURN"))
//    private void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick, CallbackInfo info)
//    {
//        //ChatScreenRegistry.getChatScreen().forEach(gui -> gui.mouseDragged(mouseX, mouseY, clickedMouseButton, timeSinceLastClick));TODO
//    }
//}