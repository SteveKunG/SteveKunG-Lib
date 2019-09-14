package stevekung.mods.stevekungslib.utils;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.entity.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import stevekung.mods.stevekungslib.utils.enums.EntityTrackerType;

public class CommonRegistryUtils
{
    private static DeferredRegister<Block> BLOCKS;
    private static DeferredRegister<Item> ITEMS;
    private static DeferredRegister<Fluid> FLUIDS;
    private static DeferredRegister<TileEntityType<?>> TILE_ENTITIES;
    private static DeferredRegister<Effect> POTIONS;
    private static DeferredRegister<Biome> BIOMES;
    private static DeferredRegister<EntityType<?>> ENTITIES;
    private static DeferredRegister<SoundEvent> SOUND_EVENTS;

    public CommonRegistryUtils(String modId)
    {
        CommonRegistryUtils.BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, modId);
        CommonRegistryUtils.ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, modId);
        CommonRegistryUtils.FLUIDS = new DeferredRegister<>(ForgeRegistries.FLUIDS, modId);
        CommonRegistryUtils.TILE_ENTITIES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, modId);
        CommonRegistryUtils.POTIONS = new DeferredRegister<>(ForgeRegistries.POTIONS, modId);
        CommonRegistryUtils.BIOMES = new DeferredRegister<>(ForgeRegistries.BIOMES, modId);
        CommonRegistryUtils.ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES, modId);
        CommonRegistryUtils.SOUND_EVENTS = new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS, modId);
    }

    public void registerBlock(Block block, String name, ItemGroup group)
    {
        this.registerBlock(block, name, group, true);
    }

    public void registerBlock(Block block, String name, ItemGroup group, boolean useBlockItem)
    {
        CommonRegistryUtils.BLOCKS.register(name, () -> block);

        if (useBlockItem)
        {
            CommonRegistryUtils.ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().group(group)));
        }
    }

    public void registerBlock(Block block, String name, BlockItem itemBlock)
    {
        CommonRegistryUtils.BLOCKS.register(name, () -> block);
        CommonRegistryUtils.ITEMS.register(name, () -> itemBlock);
    }

    public void registerItem(Item item, String name)
    {
        CommonRegistryUtils.ITEMS.register(name, () -> item);
    }

    public void registerFluid(Fluid fluid, String name)
    {
        CommonRegistryUtils.FLUIDS.register(name, () -> fluid);
    }

    public void registerTileEntity(Supplier<TileEntity> factory, String name)
    {
        CommonRegistryUtils.TILE_ENTITIES.register(name, () -> TileEntityType.Builder.create(factory).build(null));
    }

    public void registerPotion(Effect effect, String name)
    {
        CommonRegistryUtils.POTIONS.register(name, () -> effect);
    }

    public void registerBiome(Biome biome, String name)
    {
        CommonRegistryUtils.BIOMES.register(name, () -> biome);
    }

    @SuppressWarnings("deprecation")
    public void registerBiomeType(Biome biome, BiomeDictionary.Type... biomeType)
    {
        BiomeDictionary.addTypes(biome, biomeType);

        if (biome.isMutation()) // should put to mutation after registered biomes
        {
            Biome.MUTATION_TO_BASE_ID_MAP.put(biome, Registry.BIOME.getId(Registry.BIOME.getOrDefault(new ResourceLocation(biome.getParent()))));
        }
    }

    public <T extends Entity> void registerEntity(EntityType.IFactory<T> entity, EntityClassification classifi, String name)
    {
        this.registerEntity(entity, classifi, name, EntityTrackerType.NORMAL);
    }

    public <T extends Entity> void registerEntity(EntityType.IFactory<T> entity, EntityClassification classifi, String name, EntityTrackerType type)
    {
        this.registerEntity(entity, classifi, name, type.getTrackingRange(), type.getUpdateFrequency(), type.sendsVelocityUpdates());
    }

    public <T extends Entity> void registerEntity(EntityType.IFactory<T> entity, EntityClassification classifi, String name, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
    {
        CommonRegistryUtils.ENTITIES.register(name, () -> EntityType.Builder.create(entity, classifi).setTrackingRange(trackingRange).setUpdateInterval(updateFrequency).setShouldReceiveVelocityUpdates(sendsVelocityUpdates).build(name));
    }

    public <T extends MobEntity> void registerEntityPlacement(EntityType<T> entity, EntitySpawnPlacementRegistry.PlacementType placementType, Heightmap.Type heightMapType, EntitySpawnPlacementRegistry.IPlacementPredicate<T> predicate)
    {
        EntitySpawnPlacementRegistry.register(entity, placementType, heightMapType, predicate);
    }

    public void registerProjectileDispense(Item item, IDispenseItemBehavior projectile)
    {
        DispenserBlock.registerDispenseBehavior(item, projectile);
    }

    public SoundEvent registerSound(String name)
    {
        return CommonRegistryUtils.SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(name))).get();
    }

    public SoundEvent registerRecord(String name)
    {
        return this.registerSound("record." + name);
    }
}