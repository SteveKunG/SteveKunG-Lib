package com.stevekung.stevekunglib.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

public class KeyMappingBase extends KeyMapping
{
    public KeyMappingBase(String description, InputConstants.Type inputType, int keyCode, String modId)
    {
        super(description, inputType, keyCode, "key.categories." + modId);
    }

    public KeyMappingBase(String description, int keyCode, String modId)
    {
        super(description, keyCode, "key.categories." + modId);
    }
}