package stevekung.mods.stevekunglib.utils.event;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.eventhandler.Event;

public class WeatherTickEvent extends Event
{
    private final World world;
    private final Chunk chunk;
    private final BlockPos strikePos;

    public WeatherTickEvent(World world, Chunk chunk, BlockPos strikePos)
    {
        this.world = world;
        this.chunk = chunk;
        this.strikePos = strikePos;
    }

    public World getWorld()
    {
        return this.world;
    }

    public Chunk getChunk()
    {
        return this.chunk;
    }

    public BlockPos getStrikePos()
    {
        return this.strikePos;
    }
}