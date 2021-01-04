package com.stevekung.stevekungslib.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.RecipeProvider;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class RecipeProviderBase extends RecipeProvider
{
    private final String modid;

    public RecipeProviderBase(DataGenerator generator, String modid)
    {
        super(generator);
        this.modid = modid;
    }

    protected String toCriterion(IItemProvider provider)
    {
        return "has_" + this.toString(provider);
    }

    protected String toCriterion(ITag.INamedTag<?> tag)
    {
        return "has_" + tag.getName().getPath() + "_tag";
    }

    protected ResourceLocation toSmelting(IItemProvider provider)
    {
        return this.modLoc(this.toString(provider) + "_from_smelting");
    }

    protected ResourceLocation toBlasting(IItemProvider provider)
    {
        return this.modLoc(this.toString(provider) + "_from_blasting");
    }

    protected ResourceLocation from(IItemProvider base, IItemProvider required)
    {
        return this.modLoc(this.toString(base) + "_from_" + this.toString(required));
    }

    protected ResourceLocation from(ITag.INamedTag<?> base, IItemProvider required)
    {
        return this.modLoc(base.getName().getPath() + "_from_" + this.toString(required));
    }

    protected ResourceLocation from(IItemProvider base, ITag.INamedTag<?> required)
    {
        return this.modLoc(this.toString(base) + "_from_" + required.getName().getPath());
    }

    protected ResourceLocation from(ITag.INamedTag<?> base, ITag.INamedTag<?> required)
    {
        return this.modLoc(required.getName().getPath() + "_from_" + required.getName().getPath());
    }

    protected String toString(IItemProvider base)
    {
        return base.asItem().getRegistryName().getPath();
    }

    protected ResourceLocation modLoc(String name)
    {
        return new ResourceLocation(this.modid, name);
    }
}