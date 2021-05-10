package com.stevekung.stevekungslib.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

public class KeyMappingBase extends KeyMapping
{
    public KeyMappingBase(String description, InputConstants.Type inputType, int keyCode, String modId)
    {
        super(description, inputType, keyCode, "key.categories." + modId);
    }

    public KeyMappingBase(String description, String modId, int keyCode)
    {
        super(description, keyCode, "key.categories." + modId);
    }
}