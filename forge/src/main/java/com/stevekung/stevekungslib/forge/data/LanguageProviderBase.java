package com.stevekung.stevekungslib.forge.data;

import org.apache.commons.lang3.text.WordUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;

@SuppressWarnings("deprecation")
public abstract class LanguageProviderBase extends LanguageProvider
{
    public LanguageProviderBase(DataGenerator generator, String modid, String locale)
    {
        super(generator, modid, locale);
    }

    protected void add(MobEffect effect, String name, boolean hasPotion)
    {
        this.add(effect.getDescriptionId(), name);

        if (hasPotion)
        {
            this.addPotionSet(effect, name);
        }
    }

    protected void addPotionSet(MobEffect effect, String name)
    {
        this.add(Items.POTION.getDescriptionId() + ".effect." + effect.getRegistryName().getPath(), "Potion of " + name);
        this.add(Items.SPLASH_POTION.getDescriptionId() + ".effect." + effect.getRegistryName().getPath(), "Splash Potion of " + name);
        this.add(Items.LINGERING_POTION.getDescriptionId() + ".effect." + effect.getRegistryName().getPath(), "Lingering Potion of " + name);
        this.add(Items.TIPPED_ARROW.getDescriptionId() + ".effect." + effect.getRegistryName().getPath(), "Arrow of " + name);
    }

    protected void add(DamageSource source, String name)
    {
        this.add("death.attack." + source.getMsgId(), name);
    }

    protected void add(Block key)
    {
        this.add(key.getDescriptionId(), WordUtils.capitalize(key.getRegistryName().getPath().replace("_", " ")));
    }

    protected void add(Item key)
    {
        this.add(key.getDescriptionId(), WordUtils.capitalize(key.getRegistryName().getPath().replace("_", " ")));
    }

    protected void add(EntityType<?> key)
    {
        this.add(key.getDescriptionId(), WordUtils.capitalize(key.getRegistryName().getPath().replace("_", " ")));
    }

    protected void add(MobEffect key)
    {
        this.add(key.getDescriptionId(), WordUtils.capitalize(key.getRegistryName().getPath().replace("_", " ")));
    }
}