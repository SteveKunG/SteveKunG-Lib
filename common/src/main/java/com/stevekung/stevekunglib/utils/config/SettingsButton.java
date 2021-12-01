package com.stevekung.stevekunglib.utils.config;

import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.stevekunglib.utils.client.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.TooltipAccessor;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
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
        var component = this.getMessage().copy();
        var mc = Minecraft.getInstance();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        var i = this.getYImage(this.isHoveredOrFocused());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(matrixStack, this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
        this.blit(matrixStack, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
        this.renderBg(matrixStack, mc, mouseX, mouseY);
        var j = this.active ? 16777215 : 10526880;

        if (component.getString().length() > 30)
        {
            component.setStyle(component.getStyle().withFont(ClientUtils.UNICODE));
        }
        GuiComponent.drawCenteredString(matrixStack, mc.font, component, this.x + this.width / 2, this.y + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    @Override
    public List<FormattedCharSequence> getTooltip()
    {
        return this.enumSettings.getSettingValues();
    }

    public AbstractSettings<T> getSetting()
    {
        return this.enumSettings;
    }
}