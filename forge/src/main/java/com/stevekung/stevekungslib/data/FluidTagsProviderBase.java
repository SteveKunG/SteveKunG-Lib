package com.stevekung.stevekungslib.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FluidTagsProviderBase extends FluidTagsProvider
{
    public FluidTagsProviderBase(DataGenerator generator, String modid, ExistingFileHelper helper)
    {
        super(generator, modid, helper);
    }

    protected TagAppender<Fluid> getWaterTag()
    {
        return this.tag(FluidTags.WATER);
    }

    protected TagAppender<Fluid> getLavaTag()
    {
        return this.tag(FluidTags.LAVA);
    }
}