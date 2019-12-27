package com.stevekung.stevekungslib.core;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public class SteveKunGLibMixinConnector implements IMixinConnector
{
    @Override
    public void connect()
    {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.stevekung's_lib.json");
    }
}