package com.stevekung.stevekungslib.data;

import java.util.stream.Collectors;

import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityLootTablesBase extends EntityLootTables
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