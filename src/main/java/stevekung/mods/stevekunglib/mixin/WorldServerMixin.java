package stevekung.mods.stevekunglib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import stevekung.mods.stevekunglib.utils.EventHooksCommon;

@Mixin(value = WorldServer.class, priority = 10000)
public class WorldServerMixin
{
    @Unique
    private Chunk chunk;

    @Unique
    private int chunkX;

    @Unique
    private int chunkZ;

    @ModifyVariable(method = "updateBlocks", at = @At(value = "INVOKE", target = "net/minecraft/profiler/Profiler.endStartSection(Ljava/lang/String;)V", ordinal = 2), index = 5, ordinal = 0)
    private Chunk stevekunglib$getChunk(Chunk chunk)
    {
        return this.chunk = chunk;
    }

    @ModifyVariable(method = "updateBlocks", at = @At(value = "INVOKE", target = "net/minecraft/profiler/Profiler.endStartSection(Ljava/lang/String;)V", ordinal = 2), index = 6, ordinal = 1)
    private int stevekunglib$getChunkX(int j)
    {
        return this.chunkX = j;
    }

    @ModifyVariable(method = "updateBlocks", at = @At(value = "INVOKE", target = "net/minecraft/profiler/Profiler.endStartSection(Ljava/lang/String;)V", ordinal = 2), index = 6, ordinal = 2)
    private int stevekunglib$getChunkZ(int k)
    {
        return this.chunkZ = k;
    }

    @Inject(method = "updateBlocks", at = @At(value = "INVOKE", target = "net/minecraft/profiler/Profiler.endStartSection(Ljava/lang/String;)V", ordinal = 2))
    private void stevekunglib$injectWeatherTickEvent(CallbackInfo info)
    {
        EventHooksCommon.onWeatherTick(WorldServer.class.cast(this), this.chunk, this.chunkX, this.chunkZ);
    }
}