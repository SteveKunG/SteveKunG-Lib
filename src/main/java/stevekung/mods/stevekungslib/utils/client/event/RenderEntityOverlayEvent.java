package stevekung.mods.stevekungslib.utils.client.event;

import net.minecraft.entity.Entity;
import net.minecraftforge.eventbus.api.Event;

public class RenderEntityOverlayEvent extends Event
{
    private final Entity entity;
    private final double x;
    private final double y;
    private final double z;
    private final float partialTicks;

    public RenderEntityOverlayEvent(Entity entity, double x, double y, double z, float partialTicks)
    {
        this.entity = entity;
        this.x = x;
        this.y = y;
        this.z = z;
        this.partialTicks = partialTicks;
    }

    public Entity getEntity()
    {
        return this.entity;
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

    public float getPartialTicks()
    {
        return this.partialTicks;
    }
}