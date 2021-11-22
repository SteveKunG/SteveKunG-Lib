package com.stevekung.stevekunglib.forge.data.loot;

import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockLootBase extends BlockLoot
{
    private final String modId;

    public BlockLootBase(String modId)
    {
        this.modId = modId;
    }

    @Override
    @NotNull
    protected Iterable<Block> getKnownBlocks()
    {
        return ForgeRegistries.BLOCKS.getValues().stream().filter(type -> type.getRegistryName().getNamespace().equals(this.modId)).collect(Collectors.toList());
    }
}