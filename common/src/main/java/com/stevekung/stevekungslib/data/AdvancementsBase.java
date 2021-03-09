package com.stevekung.stevekungslib.data;

import java.util.function.Consumer;

import net.minecraft.advancements.Advancement;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public abstract class AdvancementsBase implements Consumer<Consumer<Advancement>>
{
    private final String modId;
    private final String root;

    public AdvancementsBase(String modId, String root)
    {
        this.modId = modId;
        this.root = root;
    }

    protected TranslatableComponent title(String name)
    {
        return new TranslatableComponent("advancement." + this.modId + "." + name + ".title");
    }

    protected TranslatableComponent description(String name)
    {
        return new TranslatableComponent("advancement." + this.modId + "." + name + ".description");
    }

    protected String modLoc(String name)
    {
        return new ResourceLocation(this.modId, this.root + "/" + name).toString();
    }
}