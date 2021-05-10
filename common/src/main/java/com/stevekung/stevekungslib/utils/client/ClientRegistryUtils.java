package com.stevekung.stevekungslib.utils.client;

import java.util.function.Function;

import com.mojang.blaze3d.vertex.PoseStack;
import me.shedaniel.architectury.registry.BlockEntityRenderers;
import me.shedaniel.architectury.registry.KeyBindings;
import me.shedaniel.architectury.registry.entity.EntityRenderers;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ClientRegistryUtils
{
    public static <E extends Entity> void registerEntityRendering(EntityType<E> entity, Function<EntityRenderDispatcher, EntityRenderer<E>> render)
    {
        EntityRenderers.register(entity, render);
    }

    public static <T extends BlockEntity> void registerTileEntityRendering(BlockEntityType<T> be, Function<BlockEntityRenderDispatcher, BlockEntityRenderer<T>> render)
    {
        BlockEntityRenderers.registerRenderer(be, render);
    }

    public static void renderTESR(BlockEntity be, PoseStack poseStack, MultiBufferSource buffer)
    {
        ClientRegistryUtils.renderTESR(be, poseStack, buffer, 0, 0);
    }

    public static void renderTESR(BlockEntity be, PoseStack poseStack, MultiBufferSource buffer, int color1, int color2)
    {
        BlockEntityRenderDispatcher.instance.renderItem(be, poseStack, buffer, color1, color2);
    }

    public static void registerKeyBinding(KeyMapping key)
    {
        KeyBindings.registerKeyBinding(key);
    }
}