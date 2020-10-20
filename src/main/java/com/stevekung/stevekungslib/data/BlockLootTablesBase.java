package com.stevekung.stevekungslib.data;

import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class BlockLootTablesBase extends BlockLootTables
{
    @Override
    protected Iterable<Block> getKnownBlocks()
    {
        return ForgeRegistries.BLOCKS.getValues().stream().filter(type -> type.getRegistryName().getNamespace().equals(this.getModId())).collect(Collectors.toList());
    }

    protected abstract String getModId();
}