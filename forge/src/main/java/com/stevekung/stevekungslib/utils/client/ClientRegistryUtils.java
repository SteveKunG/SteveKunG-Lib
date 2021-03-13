package com.stevekung.stevekungslib.utils.client;

import java.util.function.Function;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientRegistryUtils
{
    public static <E extends Entity> void registerEntityRendering(EntityType<E> entity, IRenderFactory<? super E> render)
    {
        RenderingRegistry.registerEntityRenderingHandler(entity, render);
    }

    public static <T extends BlockEntity> void registerTileEntityRendering(BlockEntityType<T> be, Function<? super BlockEntityRenderDispatcher, ? extends BlockEntityRenderer<? super T>> render)
    {
        ClientRegistry.bindTileEntityRenderer(be, render);
    }

    public static void renderTESR(BlockEntity be, PoseStack poseStack, MultiBufferSource renderType)
    {
        ClientRegistryUtils.renderTESR(be, poseStack, renderType, 0, 0);
    }

    public static void renderTESR(BlockEntity be, PoseStack poseStack, MultiBufferSource buffer, int color1, int color2)
    {
        BlockEntityRenderDispatcher.instance.renderItem(be, poseStack, buffer, color1, color2);
    }

    public static synchronized void registerKeyBinding(KeyMapping key)
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