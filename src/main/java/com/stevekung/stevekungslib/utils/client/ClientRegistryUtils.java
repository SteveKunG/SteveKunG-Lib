package com.stevekung.stevekungslib.utils.client;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
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

    public static <E extends Entity> void registerEntityRendering(EntityType<E> entity, IRenderFactory<? super E> render)
    {
        RenderingRegistry.registerEntityRenderingHandler(entity, render);
    }

    public static <T extends TileEntity> void registerTileEntityRendering(TileEntityType<T> tile, TileEntityRenderer<? super T> render)
    {
        ClientRegistry.bindTileEntityRenderer(tile, render);
    }

    public static void renderTESR(TileEntity tile, MatrixStack stack, IRenderTypeBuffer renderType)
    {
        ClientRegistryUtils.renderTESR(tile, stack, renderType, 0, 0);
    }

    public static void renderTESR(TileEntity tile, MatrixStack stack, IRenderTypeBuffer renderType, int color1, int color2)
    {
        TileEntityRendererDispatcher.instance.func_228852_a_(tile, stack, renderType, color1, color2);
    }

    public static void registerBlockColor(IBlockColor blockColor, Block block)
    {
        Minecraft.getInstance().getBlockColors().register(blockColor, block);
    }

    public static void registerItemColor(IItemColor blockColor, Block block)
    {
        Minecraft.getInstance().getItemColors().register(blockColor, block);
    }

    public static void registerItemColor(IItemColor blockColor, Item item)
    {
        Minecraft.getInstance().getItemColors().register(blockColor, item);
    }
}