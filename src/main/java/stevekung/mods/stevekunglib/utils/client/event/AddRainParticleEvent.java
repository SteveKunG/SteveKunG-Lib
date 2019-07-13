package stevekung.mods.stevekunglib.utils.client.event;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class AddRainParticleEvent extends Event
{
    private final World world;
    private final double x;
    private final double y;
    private final double z;

    public AddRainParticleEvent(World world, double x, double y, double z)
    {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public World getWorld()
    {
        return this.world;
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