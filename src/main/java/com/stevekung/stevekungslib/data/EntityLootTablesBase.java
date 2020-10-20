package com.stevekung.stevekungslib.data;

import java.util.stream.Collectors;

import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class EntityLootTablesBase extends EntityLootTables
{
    @Override
    protected Iterable<EntityType<?>> getKnownEntities()
    {
        return ForgeRegistries.ENTITIES.getValues().stream().filter(type -> type.getRegistryName().getNamespace().equals(this.getModId())).collect(Collectors.toList());
    }

    protected abstract String getModId();
}