package stevekung.mods.stevekunglib.utils.event;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

public class WeatherTickEvent extends Event
{
    private final World world;
    private final int chunkX;
    private final int chunkZ;
    private final BlockPos strikePos;

    public WeatherTickEvent(World world, int chunkX, int chunkZ, BlockPos strikePos)
    {
        this.world = world;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.strikePos = strikePos;
    }

    public World getWorld()
    {
        return this.world;
    }

    public int getChunkX()
    {
        return this.chunkX;
    }

    public int getChunkZ()
    {
        return this.chunkZ;
    }

    public BlockPos getStrikePos()
    {
        return this.strikePos;
    }
}