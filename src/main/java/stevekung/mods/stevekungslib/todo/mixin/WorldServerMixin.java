//package stevekung.mods.stevekungslib.todo.mixin;
//
//import java.util.Iterator;
//import java.util.Random;
//
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//import net.minecraft.server.management.PlayerChunkMap;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.WorldServer;
//import net.minecraft.world.chunk.Chunk;
//import stevekung.mods.stevekunglib.utils.EventHooksCommon;
//
//@Mixin(value = WorldServer.class, priority = 10000)
//public abstract class WorldServerMixin
//{
//    private final WorldServer that = (WorldServer) (Object) this;
//    private int updateLCG_N = new Random().nextInt();
//
//    @Shadow
//    private PlayerChunkMap playerChunkMap;
//
//    @Shadow
//    protected abstract BlockPos adjustPosToNearbyEntity(BlockPos pos);
//
//    @Inject(method = "updateBlocks()V", cancellable = true, at = @At(value = "INVOKE", target = "net/minecraft/profiler/Profiler.startSection(Ljava/lang/String;)V", shift = At.Shift.AFTER, ordinal = 0))
//    private void injectWeatherTickEvent(CallbackInfo info)
//    {
//        for (Iterator<Chunk> iterator = this.that.getPersistentChunkIterable(this.playerChunkMap.getChunkIterator()); iterator.hasNext();)
//        {
//            Chunk chunk = iterator.next();
//            int chunkX = chunk.x * 16;
//            int chunkZ = chunk.z * 16;
//            this.updateLCG_N = this.updateLCG_N * 3 + 1013904223;
//            int l = this.updateLCG_N >> 2;
//            BlockPos strikePos = this.adjustPosToNearbyEntity(new BlockPos(chunkX + (l & 15), 0, chunkZ + (l >> 8 & 15)));
//            EventHooksCommon.onWeatherTick(this.that, chunkX, chunkZ, strikePos);
//        }
//    }
//}