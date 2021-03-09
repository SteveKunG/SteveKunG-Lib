package com.stevekung.stevekungslib.keybinding;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;

public class KeyBindingBase extends KeyMapping
{
    public KeyBindingBase(String description, IKeyConflictContext context, KeyModifier keyModifier, InputConstants.Type inputType, int keyCode, String modId)
    {
        super(description, context, keyModifier, inputType, keyCode, "key.categories." + modId);
    }

    public KeyBindingBase(String description, String modId, int keyCode)
    {
        super(description, keyCode, "key.categories." + modId);
    }
}