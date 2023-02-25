package stevekung.mods.stevekunglib.utils;

import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import stevekung.mods.stevekunglib.utils.event.WeatherTickEvent;

public class EventHooksCommon
{
    public static void onWeatherTick(WorldServer worldServer, Chunk chunk, int chunkX, int chunkZ)
    {
        MinecraftForge.EVENT_BUS.post(new WeatherTickEvent(worldServer, chunk, chunkX, chunkZ));
    }
}