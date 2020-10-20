package com.stevekung.stevekungslib.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraft.data.TagsProvider;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FluidTagsProviderBase extends FluidTagsProvider
{
    public FluidTagsProviderBase(DataGenerator generator, String modid, ExistingFileHelper helper)
    {
        super(generator, modid, helper);
    }

    protected TagsProvider.Builder<Fluid> getWaterTag()
    {
        return this.getOrCreateBuilder(FluidTags.WATER);
    }

    protected TagsProvider.Builder<Fluid> getLavaTag()
    {
        return this.getOrCreateBuilder(FluidTags.LAVA);
    }
}