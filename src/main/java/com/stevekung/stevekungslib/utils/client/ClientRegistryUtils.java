package com.stevekung.stevekungslib.utils.client;

import java.util.function.Function;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientRegistryUtils
{
    public static <E extends Entity> void registerEntityRendering(EntityType<E> entity, IRenderFactory<? super E> render)
    {
        RenderingRegistry.registerEntityRenderingHandler(entity, render);
    }

    public static <T extends TileEntity> void registerTileEntityRendering(TileEntityType<T> tile, Function<? super TileEntityRendererDispatcher, ? extends TileEntityRenderer<? super T>> render)
    {
        ClientRegistry.bindTileEntityRenderer(tile, render);
    }

    public static void renderTESR(TileEntity tile, MatrixStack stack, IRenderTypeBuffer renderType)
    {
        ClientRegistryUtils.renderTESR(tile, stack, renderType, 0, 0);
    }

    public static void renderTESR(TileEntity tile, MatrixStack stack, IRenderTypeBuffer renderType, int color1, int color2)
    {
        TileEntityRendererDispatcher.instance.renderItem(tile, stack, renderType, color1, color2);
    }

    public static synchronized void registerKeyBinding(KeyBinding key)
    {
        ClientRegistry.registerKeyBinding(key);
    }

    public static void registerEntityShader(Class<? extends Entity> entityClass, ResourceLocation shader)
    {
        ClientRegistry.registerEntityShader(entityClass, shader);
    }

    public static ResourceLocation getEntityShader(Class<? extends Entity> entityClass)
    {
        return ClientRegistry.getEntityShader(entityClass);
    }
}