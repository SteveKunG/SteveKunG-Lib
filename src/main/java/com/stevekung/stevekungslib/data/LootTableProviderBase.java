package com.stevekung.stevekungslib.data;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.util.ResourceLocation;

public class LootTableProviderBase extends LootTableProvider
{
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> tables = Lists.newArrayList();

    public LootTableProviderBase(DataGenerator generator)
    {
        super(generator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables()
    {
        return Collections.unmodifiableList(this.tables);
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker tracker)
    {
        map.forEach((resource, loot) -> LootTableManager.validateLootTable(tracker, resource, loot));
    }

    public LootTableProviderBase addTable(Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet> table)
    {
        this.tables.add(table);
        return this;
    }
}