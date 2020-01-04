package com.stevekung.stevekungslib.mixin;

import java.util.Collections;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.stevekung.stevekungslib.core.SteveKunGLib;
import com.stevekung.stevekungslib.utils.client.ClientUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.client.gui.fonts.FontResourceManager;
import net.minecraft.client.gui.fonts.providers.IGlyphProvider;
import net.minecraft.client.gui.fonts.providers.UnicodeTextureGlyphProvider;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

@Mixin(FontResourceManager.class)
public abstract class MixinFontResourceManager
{
    @Shadow
    @Final
    @Mutable
    private TextureManager textureManager;

    @Inject(method = "getFontRenderer(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/client/gui/FontRenderer;", at = @At("HEAD"))
    private void getFontRenderer(ResourceLocation id, CallbackInfoReturnable<FontRenderer> info)
    {
        ClientUtils.unicodeFontRenderer = new FontRenderer(this.textureManager, new Font(this.textureManager, new ResourceLocation(SteveKunGLib.MOD_ID, "unicode")));
        IGlyphProvider provider = new UnicodeTextureGlyphProvider.Factory(new ResourceLocation("font/glyph_sizes.bin"), "minecraft:font/unicode_page_%s.png").create(Minecraft.getInstance().getResourceManager());
        ClientUtils.unicodeFontRenderer.setGlyphProviders(Collections.singletonList(provider));
        SteveKunGLib.LOGGER.info("Unicode Font initialized");
    }
}