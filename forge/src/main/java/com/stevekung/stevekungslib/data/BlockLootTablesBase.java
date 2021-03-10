package com.stevekung.stevekungslib.data;

import java.util.stream.Collectors;

import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockLootTablesBase extends BlockLoot
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