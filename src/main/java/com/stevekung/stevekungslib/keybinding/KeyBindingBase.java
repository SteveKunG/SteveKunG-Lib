package com.stevekung.stevekungslib.keybinding;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;

public class KeyBindingBase extends KeyBinding
{
    public KeyBindingBase(String description, String modid, IKeyConflictContext context, KeyModifier keyModifier, InputMappings.Input keyCode)
    {
        super(description, context, keyModifier, keyCode, "key." + modid + ".category");
    }

    public KeyBindingBase(String description, String modid, int keyCode)
    {
        super(description, keyCode, "key." + modid + ".category");
    }
}