package stevekung.mods.stevekungslib.utils.enums;

public enum EnumToolSpeed
{
    WOOD(-3.2F),
    COMMON(-3.0F);

    private final float speed;

    EnumToolSpeed(float speed)
    {
        this.speed = speed;
    }

    public float getSpeed()
    {
        return this.speed;
    }
}