package stevekung.mods.stevekungslib.utils;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import stevekung.mods.stevekungslib.utils.event.WeatherTickEvent;

public class EventHooksCommon
{
    public static void onWeatherTick(World world, int chunkX, int chunkZ, BlockPos strikePos)
    {
        MinecraftForge.EVENT_BUS.post(new WeatherTickEvent(world, chunkX, chunkZ, strikePos));
    }
}