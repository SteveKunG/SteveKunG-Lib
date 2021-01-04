package com.stevekung.stevekungslib.data;

import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockLootTablesBase extends BlockLootTables
{
    private final String modId;

    public BlockLootTablesBase(String modId)
    {
        this.modId = modId;
    }

    @Override
    protected Iterable<Block> getKnownBlocks()
    {
        return ForgeRegistries.BLOCKS.getValues().stream().filter(type -> type.getRegistryName().getNamespace().equals(this.modId)).collect(Collectors.toList());
    }
}