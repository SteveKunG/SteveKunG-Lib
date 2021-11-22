package com.stevekung.stevekunglib.forge.data.loot;

import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityLootBase extends EntityLoot
{
    private final String modId;

    public EntityLootBase(String modId)
    {
        this.modId = modId;
    }

    @Override
    @NotNull
    protected Iterable<EntityType<?>> getKnownEntities()
    {
        return ForgeRegistries.ENTITIES.getValues().stream().filter(type -> type.getRegistryName().getNamespace().equals(this.modId)).collect(Collectors.toList());
    }
}