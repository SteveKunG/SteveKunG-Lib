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

    protected String itemToCriterion(IItemProvider provider)
    {
        return "has_" + provider.asItem().getRegistryName().getPath();
    }

    protected String tagToCriterion(ITag.INamedTag<?> tag)
    {
        return "has_" + tag.getName().getPath() + "_tag";
    }

    protected String itemToString(IItemProvider base)
    {
        return base.asItem().getRegistryName().getPath();
    }

    protected ResourceLocation itemToStringFrom(IItemProvider base, IItemProvider required)
    {
        return this.modLocation(this.itemToString(base) + "_from_" + this.itemToString(required));
    }

    protected ResourceLocation itemToStringFrom(ITag.INamedTag<?> base, IItemProvider required)
    {
        return this.modLocation(base.getName().getPath() + "_from_" + this.itemToString(required));
    }

    protected ResourceLocation itemToStringFrom(IItemProvider base, ITag.INamedTag<?> required)
    {
        return this.modLocation(this.itemToString(base) + "_from_" + required.getName().getPath());
    }

    protected ResourceLocation itemToStringFrom(ITag.INamedTag<?> base, ITag.INamedTag<?> required)
    {
        return this.modLocation(required.getName().getPath() + "_from_" + required.getName().getPath());
    }

    protected ResourceLocation modLocation(String name)
    {
        return new ResourceLocation(this.modid, name);
    }
}