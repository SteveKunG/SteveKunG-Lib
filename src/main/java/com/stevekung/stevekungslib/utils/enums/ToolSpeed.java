package com.stevekung.stevekungslib.utils.enums;

public enum ToolSpeed
{
    WOOD(-3.2F),
    COMMON(-3.0F);

    private final float speed;

    private ToolSpeed(float speed)
    {
        this.speed = speed;
    }

    public float getSpeed()
    {
        return this.speed;
    }
}