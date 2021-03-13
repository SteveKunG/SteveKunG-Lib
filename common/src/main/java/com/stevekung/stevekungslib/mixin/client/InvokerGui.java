package com.stevekung.stevekungslib.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.client.gui.Gui;

@Mixin(Gui.class)
public interface InvokerGui
{
    @Accessor("overlayMessageTime")
    void setOverlayMessageTime(int time);
}