package com.stevekung.stevekungslib.keybinding;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;

public class KeyBindingBase extends KeyBinding
{
    public KeyBindingBase(String description, IKeyConflictContext context, KeyModifier keyModifier, InputMappings.Type inputType, int keyCode, String modId)
    {
        super(description, context, keyModifier, inputType, keyCode, "key." + modId + ".categories");
    }

    public KeyBindingBase(String description, String modId, int keyCode)
    {
        super(description, keyCode, "key." + modId + ".categories");
    }
}