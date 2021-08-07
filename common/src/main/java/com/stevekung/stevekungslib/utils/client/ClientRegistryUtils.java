package com.stevekung.stevekungslib.utils.client;

import java.util.function.Supplier;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.level.entity.EntityRendererRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ClientRegistryUtils
{
    public static <T extends Entity> void registerEntityRendering(Supplier<EntityType<? extends T>> type, EntityRendererProvider<T> provider)
    {
        EntityRendererRegistry.register(type, provider);
    }

    public static <T extends BlockEntity> void registerTileEntityRendering(BlockEntityType<T> type, BlockEntityRendererProvider<? super T> provider)
    {
        BlockEntityRendererRegistry.register(type, provider);
    }

    public static void renderTESR(BlockEntity be, PoseStack poseStack, MultiBufferSource buffer)
    {
        ClientRegistryUtils.renderTESR(be, poseStack, buffer, 0, 0);
    }

    public static void renderTESR(BlockEntity be, PoseStack poseStack, MultiBufferSource buffer, int color1, int color2)
    {
        Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(be, poseStack, buffer, color1, color2);
    }

    public static void registerKeyBinding(KeyMapping key)
    {
        KeyMappingRegistry.register(key);
    }
}