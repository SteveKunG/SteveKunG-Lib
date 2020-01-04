package com.stevekung.stevekungslib.mixin;

import java.util.Collections;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.stevekung.stevekungslib.core.SteveKunGLib;
import com.stevekung.stevekungslib.utils.client.ClientUtils;

import net.minecraft.client.GameConfiguration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.client.gui.fonts.providers.IGlyphProvider;
import net.minecraft.client.gui.fonts.providers.UnicodeTextureGlyphProvider;
import net.minecraft.util.ResourceLocation;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft
{
    @Inject(method = "<init>", at = @At("RETURN"))
    private void create(GameConfiguration gameConfig, CallbackInfo info)
    {
        Minecraft mc = Minecraft.getInstance();
        ClientUtils.unicodeFontRenderer = new FontRenderer(mc.textureManager, new Font(mc.textureManager, new ResourceLocation(SteveKunGLib.MOD_ID, "unicode")));
        IGlyphProvider provider = new UnicodeTextureGlyphProvider.Factory(new ResourceLocation("font/glyph_sizes.bin"), "minecraft:font/unicode_page_%s.png").create(Minecraft.getInstance().getResourceManager());
        ClientUtils.unicodeFontRenderer.setGlyphProviders(Collections.singletonList(provider));
        SteveKunGLib.LOGGER.info("Unicode Font initialized");
    }
}