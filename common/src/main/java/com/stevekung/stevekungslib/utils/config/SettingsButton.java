package com.stevekung.stevekungslib.utils.config;

import java.util.List;
import java.util.Optional;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.stevekungslib.utils.client.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.TooltipAccessor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

public class SettingsButton<T extends Settings> extends Button implements TooltipAccessor
{
    private final AbstractSettings<T> enumSettings;

    public SettingsButton(int x, int y, int width, int height, AbstractSettings<T> enumSettings, Component title, Button.OnPress pressable)
    {
        super(x, y, width, height, title, pressable);
        this.enumSettings = enumSettings;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        MutableComponent component = this.getMessage().copy();
        Minecraft mc = Minecraft.getInstance();
        mc.getTextureManager().bind(WIDGETS_LOCATION);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        int i = this.getYImage(this.isHovered());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(matrixStack, this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
        this.blit(matrixStack, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
        this.renderBg(matrixStack, mc, mouseX, mouseY);
        int j = this.active ? 16777215 : 10526880;

        if (component.getString().length() > 30)
        {
            component.setStyle(component.getStyle().withFont(ClientUtils.UNICODE));
        }
        GuiComponent.drawCenteredString(matrixStack, mc.font, component, this.x + this.width / 2, this.y + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    @Override
    public Optional<List<FormattedCharSequence>> getTooltip()
    {
        return this.enumSettings.getSettingValues();
    }

    public AbstractSettings<T> getSetting()
    {
        return this.enumSettings;
    }
}