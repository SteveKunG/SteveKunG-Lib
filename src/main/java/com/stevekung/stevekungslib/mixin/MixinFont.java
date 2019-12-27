package com.stevekung.stevekungslib.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.stevekung.stevekungslib.utils.ColorUtils;

import net.minecraft.client.gui.fonts.EmptyGlyph;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.client.gui.fonts.IGlyph;
import net.minecraft.client.gui.fonts.TexturedGlyph;

@Mixin(Font.class)
public abstract class MixinFont
{
    @Shadow
    @Final
    @Mutable
    private static EmptyGlyph field_212460_b;

    @Inject(method = "getGlyph(C)Lnet/minecraft/client/gui/fonts/TexturedGlyph;", cancellable = true, at = @At("HEAD"))
    private void getGlyph(char charac, CallbackInfoReturnable<TexturedGlyph> info)
    {
        if (ColorUtils.isMarker(charac))
        {
            info.setReturnValue(field_212460_b);
        }
    }

    @Inject(method = "findGlyph(C)Lnet/minecraft/client/gui/fonts/IGlyph;", cancellable = true, at = @At("HEAD"))
    private void findGlyph(char charac, CallbackInfoReturnable<IGlyph> info)
    {
        if (ColorUtils.isMarker(charac))
        {
            info.setReturnValue(() -> 0.0F);
        }
    }
}