package com.stevekung.stevekungslib.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.LanguageProvider;

public abstract class LanguageProviderBase extends LanguageProvider
{
    public LanguageProviderBase(DataGenerator generator, String modid, String locale)
    {
        super(generator, modid, locale);
    }

    protected void add(MobEffect key, String name, boolean hasPotion)
    {
        this.add(key.getDescriptionId(), name);

        if (hasPotion)
        {
            this.addPotionSet(key, name);
        }
    }

    protected void addPotionSet(MobEffect key, String name)
    {
        this.add(Items.POTION.getDescriptionId() + ".effect." + key.getRegistryName().getPath(), "Potion of " + name);
        this.add(Items.SPLASH_POTION.getDescriptionId() + ".effect." + key.getRegistryName().getPath(), "Splash Potion of " + name);
        this.add(Items.LINGERING_POTION.getDescriptionId() + ".effect." + key.getRegistryName().getPath(), "Lingering Potion of " + name);
        this.add(Items.TIPPED_ARROW.getDescriptionId() + ".effect." + key.getRegistryName().getPath(), "Arrow of " + name);
    }

    protected void add(DamageSource source, String name)
    {
        this.add("death.attack." + source.getMsgId(), name);
    }
}