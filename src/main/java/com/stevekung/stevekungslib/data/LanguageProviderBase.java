package com.stevekung.stevekungslib.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.data.LanguageProvider;

public abstract class LanguageProviderBase extends LanguageProvider
{
    public LanguageProviderBase(DataGenerator generator, String modid, String locale)
    {
        super(generator, modid, locale);
    }

    protected void add(Effect key, String name, boolean hasPotion)
    {
        this.add(key.getName(), name);

        if (hasPotion)
        {
            this.addPotionSet(key, name);
        }
    }

    protected void addPotionSet(Effect key, String name)
    {
        this.add(Items.POTION.getTranslationKey() + ".effect." + key.getRegistryName().getPath(), "Potion of " + name);
        this.add(Items.SPLASH_POTION.getTranslationKey() + ".effect." + key.getRegistryName().getPath(), "Splash Potion of " + name);
        this.add(Items.LINGERING_POTION.getTranslationKey() + ".effect." + key.getRegistryName().getPath(), "Lingering Potion of " + name);
        this.add(Items.TIPPED_ARROW.getTranslationKey() + ".effect." + key.getRegistryName().getPath(), "Arrow of " + name);
    }

    protected void add(DamageSource source, String name)
    {
        this.add("death.attack." + source.getDamageType(), name);
    }
}