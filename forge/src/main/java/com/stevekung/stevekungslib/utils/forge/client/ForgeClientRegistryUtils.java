package com.stevekung.stevekungslib.utils.forge.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

public class ForgeClientRegistryUtils
{
    public static void registerEntityShader(Class<? extends Entity> entityClass, ResourceLocation shader)
    {
        ClientRegistry.registerEntityShader(entityClass, shader);
    }

    public static ResourceLocation getEntityShader(Class<? extends Entity> entityClass)
    {
        return ClientRegistry.getEntityShader(entityClass);
    }
}