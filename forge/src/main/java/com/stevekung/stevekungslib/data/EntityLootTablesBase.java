package com.stevekung.stevekungslib.data;

import net.minecraft.data.loot.EntityLoot;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.stream.Collectors;

public class EntityLootTablesBase extends EntityLoot
{
    private final String modId;

    public EntityLootTablesBase(String modId)
    {
        this.modId = modId;
    }

    @Override
    protected Iterable<EntityType<?>> getKnownEntities()
    {
        return ForgeRegistries.ENTITIES.getValues().stream().filter(type -> type.getRegistryName().getNamespace().equals(this.modId)).collect(Collectors.toList());
    }
}