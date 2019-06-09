package stevekung.mods.stevekungslib.utils.client;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class RenderUtils
{
    public static void bindTexture(ResourceLocation resource)
    {
        Minecraft.getInstance().getTextureManager().bindTexture(resource);
    }

    public static void bindTexture(String resource)
    {
        Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(resource));
    }

    public static void disableLighting()
    {
        GlStateManager.disableLighting();
        GlStateManager.disableLight(0);
        GlStateManager.disableLight(1);
        GlStateManager.disableColorMaterial();
    }

    public static void enableLighting()
    {
        GlStateManager.enableLighting();
        GlStateManager.enableLight(0);
        GlStateManager.enableLight(1);
        GlStateManager.enableColorMaterial();
    }
}