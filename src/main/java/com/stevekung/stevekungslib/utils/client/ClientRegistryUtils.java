package com.stevekung.stevekungslib.utils.client;

import javax.annotation.Nonnull;

import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientRegistryUtils
{
    private final String resourcePath;

    public ClientRegistryUtils(@Nonnull String resourcePath)
    {
        this.resourcePath = resourcePath;
    }

    public void registerSpriteTexture(TextureStitchEvent.Pre event, String texture)
    {
        event.addSprite(new ResourceLocation(this.resourcePath + ":" + texture));
    }

    public static <E extends Entity> void registerEntityRendering(Class<E> entity, IRenderFactory<E> render)
    {
        RenderingRegistry.registerEntityRenderingHandler(entity, render);
    }

    public static <T extends TileEntity> void registerTileEntityRendering(Class<T> tile, TileEntityRenderer<? super T> render)
    {
        ClientRegistry.bindTileEntitySpecialRenderer(tile, render);
    }

    public static void renderTESR(TileEntity tile)
    {
        ClientRegistryUtils.renderTESR(tile, 0.0D);
    }

    public static void renderTESR(TileEntity tile, double yOffset)
    {
        TileEntityRendererDispatcher.instance.render(tile, 0.0D, yOffset, 0.0D, 0.0F);
    }
}