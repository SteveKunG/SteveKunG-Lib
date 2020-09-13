package com.stevekung.stevekungslib.mixin.client;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
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
    private static EmptyGlyph EMPTY_GLYPH;

    @Inject(method = "getGlyph(C)Lnet/minecraft/client/gui/fonts/TexturedGlyph;", cancellable = true, at = @At("HEAD"))
    private void getGlyph(char charac, CallbackInfoReturnable<TexturedGlyph> info)
    {
        if (ColorUtils.isMarker(charac))
        {
            info.setReturnValue(EMPTY_GLYPH);
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