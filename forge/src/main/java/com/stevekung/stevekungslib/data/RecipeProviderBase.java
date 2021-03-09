package com.stevekung.stevekungslib.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.ItemLike;

public class RecipeProviderBase extends RecipeProvider
{
    private final String modid;

    public RecipeProviderBase(DataGenerator generator, String modid)
    {
        super(generator);
        this.modid = modid;
    }

    protected String toCriterion(ItemLike provider)
    {
        return "has_" + this.toString(provider);
    }

    protected String toCriterion(Tag.Named<?> tag)
    {
        return "has_" + tag.getName().getPath() + "_tag";
    }

    protected ResourceLocation toSmelting(ItemLike provider)
    {
        return this.modLoc(this.toString(provider) + "_from_smelting");
    }

    protected ResourceLocation toBlasting(ItemLike provider)
    {
        return this.modLoc(this.toString(provider) + "_from_blasting");
    }

    protected ResourceLocation from(ItemLike base, ItemLike required)
    {
        return this.modLoc(this.toString(base) + "_from_" + this.toString(required));
    }

    protected ResourceLocation from(Tag.Named<?> base, ItemLike required)
    {
        return this.modLoc(base.getName().getPath() + "_from_" + this.toString(required));
    }

    protected ResourceLocation from(ItemLike base, Tag.Named<?> required)
    {
        return this.modLoc(this.toString(base) + "_from_" + required.getName().getPath());
    }

    protected ResourceLocation from(Tag.Named<?> base, Tag.Named<?> required)
    {
        return this.modLoc(required.getName().getPath() + "_from_" + required.getName().getPath());
    }

    protected String toString(ItemLike base)
    {
        return base.asItem().getRegistryName().getPath();
    }

    protected ResourceLocation modLoc(String name)
    {
        return new ResourceLocation(this.modid, name);
    }
}