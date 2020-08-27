package stevekung.mods.stevekunglib.utils;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import stevekung.mods.stevekunglib.utils.event.WeatherTickEvent;

public class EventHooksCommon
{
    public static void onWeatherTick(World world, Chunk chunk, BlockPos strikePos)
    {
        MinecraftForge.EVENT_BUS.post(new WeatherTickEvent(world, chunk, strikePos));
    }
}