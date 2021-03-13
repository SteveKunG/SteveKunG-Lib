package com.stevekung.stevekungslib.utils.client.event;

import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class AddRainParticleEvent extends Event
{
    private final Level level;
    private final double x;
    private final double y;
    private final double z;

    public AddRainParticleEvent(Level level, double x, double y, double z)
    {
        this.level = level;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Level getLevel()
    {
        return this.level;
    }

    public double getX()
    {
        return this.x;
    }

    public double getY()
    {
        return this.y;
    }

    public double getZ()
    {
        return this.z;
    }
}