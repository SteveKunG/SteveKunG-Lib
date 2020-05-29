package com.stevekung.stevekungslib.utils;

import java.util.ArrayList;
import java.util.List;

import com.stevekung.stevekungslib.utils.enums.EntityTrackerType;

import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.schedule.Schedule;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.particles.ParticleType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.registries.DataSerializerEntry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CommonRegistryUtils
{
    private static final List<DeferredRegister<?>> ALL_REGISTRIES = new ArrayList<>();
    private final String modId;

    private static DeferredRegister<Block> BLOCKS;
    private static DeferredRegister<Enchantment> ENCHANTMENTS;
    private static DeferredRegister<EntityType<?>> ENTITY_TYPES;
    private static DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES;
    private static DeferredRegister<Activity> ACTIVITIES;
    private static DeferredRegister<Schedule> SCHEDULES;
    private static DeferredRegister<SensorType<?>> SENSOR_TYPES;
    private static DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS;
    private static DeferredRegister<Fluid> FLUIDS;
    private static DeferredRegister<ContainerType<?>> CONTAINER_TYPES;
    private static DeferredRegister<Item> ITEMS;
    private static DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS;
    private static DeferredRegister<ParticleType<?>> PARTICLE_TYPES;
    private static DeferredRegister<Effect> EFFECTS;
    private static DeferredRegister<Potion> POTIONS;
    private static DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES;
    private static DeferredRegister<SoundEvent> SOUND_EVENTS;
    private static DeferredRegister<PointOfInterestType> POINT_OF_INTEREST_TYPES;
    private static DeferredRegister<Biome> BIOMES;
    private static DeferredRegister<BiomeProviderType<?, ?>> BIOME_PROVIDER_TYPES;
    private static DeferredRegister<ModDimension> MOD_DIMENSIONS;
    private static DeferredRegister<WorldCarver<?>> WORLD_CARVERS;
    private static DeferredRegister<Feature<?>> FEATURES;
    private static DeferredRegister<Placement<?>> PLACEMENTS;
    private static DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS;
    private static DeferredRegister<DataSerializerEntry> DATA_SERIALIZERS;

    public CommonRegistryUtils(String modId)
    {
        this.modId = modId;
        CommonRegistryUtils.BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, modId);
        CommonRegistryUtils.ENCHANTMENTS = new DeferredRegister<>(ForgeRegistries.ENCHANTMENTS, modId);
        CommonRegistryUtils.ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.ENTITIES, modId);
        CommonRegistryUtils.MEMORY_MODULE_TYPES = new DeferredRegister<>(ForgeRegistries.MEMORY_MODULE_TYPES, modId);
        CommonRegistryUtils.ACTIVITIES = new DeferredRegister<>(ForgeRegistries.ACTIVITIES, modId);
        CommonRegistryUtils.SCHEDULES = new DeferredRegister<>(ForgeRegistries.SCHEDULES, modId);
        CommonRegistryUtils.SENSOR_TYPES = new DeferredRegister<>(ForgeRegistries.SENSOR_TYPES, modId);
        CommonRegistryUtils.VILLAGER_PROFESSIONS = new DeferredRegister<>(ForgeRegistries.PROFESSIONS, modId);
        CommonRegistryUtils.FLUIDS = new DeferredRegister<>(ForgeRegistries.FLUIDS, modId);
        CommonRegistryUtils.CONTAINER_TYPES = new DeferredRegister<>(ForgeRegistries.CONTAINERS, modId);
        CommonRegistryUtils.ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, modId);
        CommonRegistryUtils.RECIPE_SERIALIZERS = new DeferredRegister<>(ForgeRegistries.RECIPE_SERIALIZERS, modId);
        CommonRegistryUtils.PARTICLE_TYPES = new DeferredRegister<>(ForgeRegistries.PARTICLE_TYPES, modId);
        CommonRegistryUtils.EFFECTS = new DeferredRegister<>(ForgeRegistries.POTIONS, modId);
        CommonRegistryUtils.POTIONS = new DeferredRegister<>(ForgeRegistries.POTION_TYPES, modId);
        CommonRegistryUtils.TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, modId);
        CommonRegistryUtils.SOUND_EVENTS = new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS, modId);
        CommonRegistryUtils.POINT_OF_INTEREST_TYPES = new DeferredRegister<>(ForgeRegistries.POI_TYPES, modId);
        CommonRegistryUtils.BIOMES = new DeferredRegister<>(ForgeRegistries.BIOMES, modId);
        CommonRegistryUtils.BIOME_PROVIDER_TYPES = new DeferredRegister<>(ForgeRegistries.BIOME_PROVIDER_TYPES, modId);
        CommonRegistryUtils.MOD_DIMENSIONS = new DeferredRegister<>(ForgeRegistries.MOD_DIMENSIONS, modId);
        CommonRegistryUtils.WORLD_CARVERS = new DeferredRegister<>(ForgeRegistries.WORLD_CARVERS, modId);
        CommonRegistryUtils.FEATURES = new DeferredRegister<>(ForgeRegistries.FEATURES, modId);
        CommonRegistryUtils.PLACEMENTS = new DeferredRegister<>(ForgeRegistries.DECORATORS, modId);
        CommonRegistryUtils.SURFACE_BUILDERS = new DeferredRegister<>(ForgeRegistries.SURFACE_BUILDERS, modId);
        CommonRegistryUtils.DATA_SERIALIZERS = new DeferredRegister<>(ForgeRegistries.DATA_SERIALIZERS, modId);

        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.BLOCKS);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.ENCHANTMENTS);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.ENTITY_TYPES);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.MEMORY_MODULE_TYPES);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.ACTIVITIES);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.SCHEDULES);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.SENSOR_TYPES);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.VILLAGER_PROFESSIONS);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.FLUIDS);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.CONTAINER_TYPES);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.ITEMS);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.RECIPE_SERIALIZERS);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.PARTICLE_TYPES);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.EFFECTS);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.POTIONS);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.TILE_ENTITY_TYPES);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.SOUND_EVENTS);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.POINT_OF_INTEREST_TYPES);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.BIOMES);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.BIOME_PROVIDER_TYPES);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.MOD_DIMENSIONS);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.WORLD_CARVERS);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.FEATURES);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.PLACEMENTS);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.SURFACE_BUILDERS);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.DATA_SERIALIZERS);
    }

    // Game Object
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

    public void registerEnchantment(Enchantment enchantment, String name)
    {
        CommonRegistryUtils.ENCHANTMENTS.register(name, () -> enchantment);
    }

    public void registerEntityType(EntityType.IFactory<Entity> entity, EntityClassification classifi, String name)
    {
        this.registerEntityType(entity, classifi, name, EntityTrackerType.NORMAL);
    }

    public void registerEntityType(EntityType.IFactory<Entity> entity, EntityClassification classifi, String name, EntityTrackerType type)
    {
        this.registerEntityType(entity, classifi, name, type.getTrackingRange(), type.getUpdateFrequency(), type.sendsVelocityUpdates());
    }

    public void registerEntityType(EntityType.IFactory<Entity> entity, EntityClassification classifi, String name, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
    {
        CommonRegistryUtils.ENTITY_TYPES.register(name, () -> EntityType.Builder.create(entity, classifi).setTrackingRange(trackingRange).setUpdateInterval(updateFrequency).setShouldReceiveVelocityUpdates(sendsVelocityUpdates).build(name));
    }

    public void registerEntityPlacement(EntityType<MobEntity> entity, EntitySpawnPlacementRegistry.PlacementType placementType, Heightmap.Type heightMapType, EntitySpawnPlacementRegistry.IPlacementPredicate<MobEntity> predicate)
    {
        EntitySpawnPlacementRegistry.register(entity, placementType, heightMapType, predicate);
    }

    public void registerMemoryModuleType(MemoryModuleType<?> memoryModule, String name)
    {
        CommonRegistryUtils.MEMORY_MODULE_TYPES.register(name, () -> memoryModule);
    }

    public void registerEntityActivity(Activity activity, String name)
    {
        CommonRegistryUtils.ACTIVITIES.register(name, () -> activity);
    }

    public void registerEntitySchedule(Schedule schedule, String name)
    {
        CommonRegistryUtils.SCHEDULES.register(name, () -> schedule);
    }

    public void registerEntitySensorType(SensorType<?> sensorType, String name)
    {
        CommonRegistryUtils.SENSOR_TYPES.register(name, () -> sensorType);
    }

    public void registerVillagerProfession(VillagerProfession profession, String name)
    {
        CommonRegistryUtils.VILLAGER_PROFESSIONS.register(name, () -> profession);
    }

    public void registerFluid(Fluid fluid, String name)
    {
        CommonRegistryUtils.FLUIDS.register(name, () -> fluid);
    }

    public void registerContainerType(ContainerType<?> type, String name)
    {
        CommonRegistryUtils.CONTAINER_TYPES.register(name, () -> type);
    }

    public void registerItem(Item item, String name)
    {
        CommonRegistryUtils.ITEMS.register(name, () -> item);
    }

    public void registerRecipeSerializer(IRecipeSerializer<?> recipe, String name)
    {
        CommonRegistryUtils.RECIPE_SERIALIZERS.register(name, () -> recipe);
    }

    public void registerParticleType(ParticleType<?> type, String name)
    {
        CommonRegistryUtils.PARTICLE_TYPES.register(name, () -> type);
    }

    public void registerEffect(Effect effect, String name)
    {
        CommonRegistryUtils.EFFECTS.register(name, () -> effect);
    }

    public void registerPotion(Potion potion, String name)
    {
        CommonRegistryUtils.POTIONS.register(name, () -> potion);
    }

    public void registerTileEntityType(TileEntityType<?> type, String name)
    {
        CommonRegistryUtils.TILE_ENTITY_TYPES.register(name, () -> type);
    }

    public void registerSound(SoundEvent event, String name)
    {
        CommonRegistryUtils.SOUND_EVENTS.register(name, () -> event);
    }

    public SoundEvent createSound(String name)
    {
        return new SoundEvent(new ResourceLocation(this.modId, name));
    }

    public void registerMusicDisc(SoundEvent event, String name)
    {
        this.registerSound(event, "music_disc." + name);
    }

    public void registerPointOfInterestType(PointOfInterestType type, String name)
    {
        CommonRegistryUtils.POINT_OF_INTEREST_TYPES.register(name, () -> type);
    }

    public void registerBiome(Biome biome, String name)
    {
        CommonRegistryUtils.BIOMES.register(name, () -> biome);
    }

    public void registerBiomeProviderType(BiomeProviderType<?, ?> type, String name)
    {
        CommonRegistryUtils.BIOME_PROVIDER_TYPES.register(name, () -> type);
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

    public void registerDimension(ModDimension dimension, String name)
    {
        CommonRegistryUtils.MOD_DIMENSIONS.register(name, () -> dimension);
    }

    public void registerWorldCarver(WorldCarver worldCarver, String name)
    {
        CommonRegistryUtils.WORLD_CARVERS.register(name, () -> worldCarver);
    }

    public void registerWorldFeature(Feature<?> feature, String name)
    {
        CommonRegistryUtils.FEATURES.register(name, () -> feature);
    }

    public void registerWorldPlacement(Placement<?> placement, String name)
    {
        CommonRegistryUtils.PLACEMENTS.register(name, () -> placement);
    }

    public void registerSurfaceBuilder(SurfaceBuilder<?> builder, String name)
    {
        CommonRegistryUtils.SURFACE_BUILDERS.register(name, () -> builder);
    }

    public void registerDataSerializer(DataSerializerEntry data, String name)
    {
        CommonRegistryUtils.DATA_SERIALIZERS.register(name, () -> data);
    }

    // Others
    public void registerProjectileDispense(Item item, IDispenseItemBehavior projectile)
    {
        DispenserBlock.registerDispenseBehavior(item, projectile);
    }

    public void registerAll()
    {
        for (DeferredRegister<?> registry : CommonRegistryUtils.ALL_REGISTRIES)
        {
            registry.register(CommonUtils.getModEventBus());
        }
    }
}