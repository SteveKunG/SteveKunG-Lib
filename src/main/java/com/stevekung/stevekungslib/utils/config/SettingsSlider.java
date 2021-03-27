package com.stevekung.stevekungslib.utils.config;

import java.util.List;
import java.util.Optional;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.stevekung.stevekungslib.utils.client.ClientUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IBidiTooltip;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;

public class SettingsSlider<T extends Settings> extends AbstractSettingsSlider<T> implements IBidiTooltip
{
    private final SliderPercentageSettings<T> setting;

    public SettingsSlider(T settings, int x, int y, int width, int height, SliderPercentageSettings<T> setting)
    {
        super(settings, x, y, width, height, (float)setting.normalizeValue(setting.get(settings)));
        this.setting = setting;
        this.func_230979_b_();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        IFormattableTextComponent component = this.getMessage().deepCopy();
        Minecraft mc = Minecraft.getInstance();
        mc.getTextureManager().bindTexture(WIDGETS_LOCATION);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        int i = this.getYImage(this.isHovered());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(matrixStack, this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
        this.blit(matrixStack, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
        this.renderBg(matrixStack, mc, mouseX, mouseY);
        int j = this.getFGColor();

        if (component.getString().length() > 30)
        {
            component.setStyle(component.getStyle().setFontId(ClientUtils.UNICODE));
        }
        AbstractGui.drawCenteredString(matrixStack, mc.fontRenderer, component, this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
    }

    @Override
    protected void func_230972_a_()
    {
        this.setting.set(this.settings, this.setting.denormalizeValue(this.sliderValue));
        this.settings.save();
    }

    @Override
    protected void func_230979_b_()
    {
        this.setMessage(this.setting.getMessage(this.settings));
    }

    @Override
    public Optional<List<IReorderingProcessor>> func_241867_d()
    {
        return this.setting.getSettingValues();
    }
}