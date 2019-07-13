package stevekung.mods.stevekunglib.utils.client;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import stevekung.mods.stevekunglib.utils.client.event.AddRainParticleEvent;
import stevekung.mods.stevekunglib.utils.client.event.CameraTransformEvent;
import stevekung.mods.stevekunglib.utils.client.event.FirstPersonViewOverlayEvent;
import stevekung.mods.stevekunglib.utils.client.event.RenderEntityOverlayEvent;

public class EventHooksClient
{
    public static void onCameraTransform(int rendererUpdateCount, float partialTicks)
    {
        MinecraftForge.EVENT_BUS.post(new CameraTransformEvent(rendererUpdateCount, partialTicks));
    }

    public static boolean onAddRainParticle(World world, double x, double y, double z)
    {
        return MinecraftForge.EVENT_BUS.post(new AddRainParticleEvent(world, x, y, z));
    }

    public static void onRenderFirstPersonViewOverlay()
    {
        MinecraftForge.EVENT_BUS.post(new FirstPersonViewOverlayEvent());
    }

    public static void onRenderEntityOverlay(Entity entity, double x, double y, double z, float partialTicks)
    {
        MinecraftForge.EVENT_BUS.post(new RenderEntityOverlayEvent(entity, x, y, z, partialTicks));
    }
}