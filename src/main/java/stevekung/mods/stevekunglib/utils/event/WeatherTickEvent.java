package stevekung.mods.stevekunglib.utils.event;

import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.eventhandler.Event;

public class WeatherTickEvent extends Event
{
    private final WorldServer worldServer;
    private final Chunk chunk;
    private final int chunkX;
    private final int chunkZ;

    public WeatherTickEvent(WorldServer worldServer, Chunk chunk, int chunkX, int chunkZ)
    {
        this.worldServer = worldServer;
        this.chunk = chunk;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    public WorldServer getWorldServer()
    {
        return this.worldServer;
    }

    public Chunk getChunk()
    {
        return this.chunk;
    }

    public int getChunkX()
    {
        return this.chunkX;
    }

    public int getChunkZ()
    {
        return this.chunkZ;
    }
}