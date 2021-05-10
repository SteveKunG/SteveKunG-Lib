package com.stevekung.stevekungslib.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;

public class ForgeKeyMappingBase extends KeyMapping
{
    public ForgeKeyMappingBase(String description, IKeyConflictContext context, KeyModifier keyModifier, InputConstants.Type inputType, int keyCode, String modId)
    {
        super(description, context, keyModifier, inputType, keyCode, "key.categories." + modId);
    }

    public ForgeKeyMappingBase(String description, String modId, int keyCode)
    {
        super(description, keyCode, "key.categories." + modId);
    }
}