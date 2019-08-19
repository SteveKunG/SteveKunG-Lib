//package stevekung.mods.stevekungslib.todo.mixin;
//
//import java.util.Random;
//
//import org.spongepowered.asm.mixin.Final;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Overwrite;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//import net.minecraft.block.material.Material;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.EntityRenderer;
//import net.minecraft.entity.Entity;
//import net.minecraft.init.Blocks;
//import net.minecraft.init.SoundEvents;
//import net.minecraft.util.EnumParticleTypes;
//import net.minecraft.util.SoundCategory;
//import net.minecraft.util.math.AxisAlignedBB;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.world.World;
//import net.minecraft.world.biome.Biome;
//import stevekung.mods.stevekunglib.utils.client.EventHooksClient;
//
//@Mixin(EntityRenderer.class)
//public class EntityRendererMixin
//{
//    @Shadow
//    private int rendererUpdateCount;
//
//    @Shadow
//    private int rainSoundCounter;
//
//    @Shadow
//    @Final
//    private Minecraft mc;
//
//    @Shadow
//    @Final
//    private Random random;
//
//    @Inject(method = "setupCameraTransform(FI)V", cancellable = true, at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/EntityRenderer.orientCamera(F)V", shift = At.Shift.BEFORE))
//    private void injectCameraEvent(float partialTicks, int pass, CallbackInfo info)
//    {
//        EventHooksClient.onCameraTransform(this.rendererUpdateCount, partialTicks);
//    }
//
//    @Overwrite
//    public void addRainParticles()
//    {
//        float f = this.mc.world.getRainStrength(1.0F);
//
//        if (!this.mc.gameSettings.fancyGraphics)
//        {
//            f /= 2.0F;
//        }
//
//        if (f != 0.0F)
//        {
//            this.random.setSeed(this.rendererUpdateCount * 312987231L);
//            Entity entity = this.mc.getRenderViewEntity();
//            World world = this.mc.world;
//            BlockPos blockpos = new BlockPos(entity);
//            double d0 = 0.0D;
//            double d1 = 0.0D;
//            double d2 = 0.0D;
//            int j = 0;
//            int k = (int)(100.0F * f * f);
//
//            if (this.mc.gameSettings.particleSetting == 1)
//            {
//                k >>= 1;
//            }
//            else if (this.mc.gameSettings.particleSetting == 2)
//            {
//                k = 0;
//            }
//
//            for (int l = 0; l < k; ++l)
//            {
//                BlockPos blockpos1 = world.getPrecipitationHeight(blockpos.add(this.random.nextInt(10) - this.random.nextInt(10), 0, this.random.nextInt(10) - this.random.nextInt(10)));
//                Biome biome = world.getBiome(blockpos1);
//                BlockPos blockpos2 = blockpos1.down();
//                IBlockState iblockstate = world.getBlockState(blockpos2);
//
//                if (blockpos1.getY() <= blockpos.getY() + 10 && blockpos1.getY() >= blockpos.getY() - 10 && biome.canRain() && biome.getTemperature(blockpos1) >= 0.15F)
//                {
//                    double d3 = this.random.nextDouble();
//                    double d4 = this.random.nextDouble();
//                    AxisAlignedBB axisalignedbb = iblockstate.getBoundingBox(world, blockpos2);
//
//                    if (iblockstate.getMaterial() != Material.LAVA && iblockstate.getBlock() != Blocks.MAGMA)
//                    {
//                        if (iblockstate.getMaterial() != Material.AIR)
//                        {
//                            ++j;
//
//                            if (this.random.nextInt(j) == 0)
//                            {
//                                d0 = blockpos2.getX() + d3;
//                                d1 = blockpos2.getY() + 0.1F + axisalignedbb.maxY - 1.0D;
//                                d2 = blockpos2.getZ() + d4;
//                            }
//                            if (!EventHooksClient.onAddRainParticle(world, blockpos2.getX() + d3, blockpos2.getY() + 0.1F + axisalignedbb.maxY, blockpos2.getZ() + d4))
//                            {
//                                this.mc.world.spawnParticle(EnumParticleTypes.WATER_DROP, blockpos2.getX() + d3, blockpos2.getY() + 0.1F + axisalignedbb.maxY, blockpos2.getZ() + d4, 0.0D, 0.0D, 0.0D, new int[0]);
//                            }
//                        }
//                    }
//                    else
//                    {
//                        this.mc.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, blockpos1.getX() + d3, blockpos1.getY() + 0.1F - axisalignedbb.minY, blockpos1.getZ() + d4, 0.0D, 0.0D, 0.0D, new int[0]);
//                    }
//                }
//            }
//
//            if (j > 0 && this.random.nextInt(3) < this.rainSoundCounter++)
//            {
//                this.rainSoundCounter = 0;
//
//                if (d1 > blockpos.getY() + 1 && world.getPrecipitationHeight(blockpos).getY() > MathHelper.floor(blockpos.getY()))
//                {
//                    this.mc.world.playSound(d0, d1, d2, SoundEvents.WEATHER_RAIN_ABOVE, SoundCategory.WEATHER, 0.1F, 0.5F, false);
//                }
//                else
//                {
//                    this.mc.world.playSound(d0, d1, d2, SoundEvents.WEATHER_RAIN, SoundCategory.WEATHER, 0.2F, 1.0F, false);
//                }
//            }
//        }
//    }
//}