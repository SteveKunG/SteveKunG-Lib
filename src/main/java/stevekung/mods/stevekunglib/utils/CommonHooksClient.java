package stevekung.mods.stevekunglib.utils;

import net.minecraftforge.common.MinecraftForge;
import stevekung.mods.stevekunglib.utils.client.event.CameraTransformEvent;

public class CommonHooksClient
{
    public static void onCameraTransform(int rendererUpdateCount, float partialTicks)
    {
        MinecraftForge.EVENT_BUS.post(new CameraTransformEvent(rendererUpdateCount, partialTicks));
    }
}