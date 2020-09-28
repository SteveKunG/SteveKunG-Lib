package com.stevekung.stevekungslib.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
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
        CommonRegistryUtils.BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, modId);
        CommonRegistryUtils.ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, modId);
        CommonRegistryUtils.ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, modId);
        CommonRegistryUtils.MEMORY_MODULE_TYPES = DeferredRegister.create(ForgeRegistries.MEMORY_MODULE_TYPES, modId);
        CommonRegistryUtils.ACTIVITIES = DeferredRegister.create(ForgeRegistries.ACTIVITIES, modId);
        CommonRegistryUtils.SCHEDULES = DeferredRegister.create(ForgeRegistries.SCHEDULES, modId);
        CommonRegistryUtils.SENSOR_TYPES = DeferredRegister.create(ForgeRegistries.SENSOR_TYPES, modId);
        CommonRegistryUtils.VILLAGER_PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, modId);
        CommonRegistryUtils.FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, modId);
        CommonRegistryUtils.CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, modId);
        CommonRegistryUtils.ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, modId);
        CommonRegistryUtils.RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, modId);
        CommonRegistryUtils.PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, modId);
        CommonRegistryUtils.EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, modId);
        CommonRegistryUtils.POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, modId);
        CommonRegistryUtils.TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, modId);
        CommonRegistryUtils.SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, modId);
        CommonRegistryUtils.POINT_OF_INTEREST_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, modId);
        CommonRegistryUtils.BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, modId);
        CommonRegistryUtils.BIOME_PROVIDER_TYPES = DeferredRegister.create(ForgeRegistries.BIOME_PROVIDER_TYPES, modId);
        CommonRegistryUtils.MOD_DIMENSIONS = DeferredRegister.create(ForgeRegistries.MOD_DIMENSIONS, modId);
        CommonRegistryUtils.WORLD_CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, modId);
        CommonRegistryUtils.FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, modId);
        CommonRegistryUtils.PLACEMENTS = DeferredRegister.create(ForgeRegistries.DECORATORS, modId);
        CommonRegistryUtils.SURFACE_BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, modId);
        CommonRegistryUtils.DATA_SERIALIZERS = DeferredRegister.create(ForgeRegistries.DATA_SERIALIZERS, modId);

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
    public Block registerBlock(Block block, String name, ItemGroup group)
    {
        return this.registerBlock(block, name, group, true);
    }

    public Block registerBlock(Block block, String name, ItemGroup group, boolean useBlockItem)
    {
        if (useBlockItem)
        {
            CommonRegistryUtils.ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().group(group)));
        }
        return CommonRegistryUtils.BLOCKS.register(name, () -> block).get();
    }

    public Enchantment registerEnchantment(Enchantment enchantment, String name)
    {
        return CommonRegistryUtils.ENCHANTMENTS.register(name, () -> enchantment).get();
    }

    public <T extends Entity> EntityType<T> registerEntityType(EntityType.Builder<T> builder, String name)
    {
        return CommonRegistryUtils.ENTITY_TYPES.register(name, () -> builder.build(name)).get();
    }

    public void registerEntityPlacement(EntityType<MobEntity> entity, EntitySpawnPlacementRegistry.PlacementType placementType, Heightmap.Type heightMapType, EntitySpawnPlacementRegistry.IPlacementPredicate<MobEntity> predicate)
    {
        EntitySpawnPlacementRegistry.register(entity, placementType, heightMapType, predicate);
    }

    public MemoryModuleType<?> registerMemoryModuleType(MemoryModuleType<?> memoryModule, String name)
    {
        return CommonRegistryUtils.MEMORY_MODULE_TYPES.register(name, () -> memoryModule).get();
    }

    public Activity registerEntityActivity(Activity activity, String name)
    {
        return CommonRegistryUtils.ACTIVITIES.register(name, () -> activity).get();
    }

    public Schedule registerEntitySchedule(Schedule schedule, String name)
    {
        return CommonRegistryUtils.SCHEDULES.register(name, () -> schedule).get();
    }

    public SensorType<?> registerEntitySensorType(SensorType<?> sensorType, String name)
    {
        return CommonRegistryUtils.SENSOR_TYPES.register(name, () -> sensorType).get();
    }

    public VillagerProfession registerVillagerProfession(VillagerProfession profession, String name)
    {
        return CommonRegistryUtils.VILLAGER_PROFESSIONS.register(name, () -> profession).get();
    }

    public Fluid registerFluid(Fluid fluid, String name)
    {
        return CommonRegistryUtils.FLUIDS.register(name, () -> fluid).get();
    }

    public ContainerType<?> registerContainerType(ContainerType<?> type, String name)
    {
        return CommonRegistryUtils.CONTAINER_TYPES.register(name, () -> type).get();
    }

    public Item registerItem(Item item, String name)
    {
        return CommonRegistryUtils.ITEMS.register(name, () -> item).get();
    }

    public IRecipeSerializer<?> registerRecipeSerializer(IRecipeSerializer<?> recipe, String name)
    {
        return CommonRegistryUtils.RECIPE_SERIALIZERS.register(name, () -> recipe).get();
    }

    public ParticleType<?> registerParticleType(ParticleType<?> type, String name)
    {
        return CommonRegistryUtils.PARTICLE_TYPES.register(name, () -> type).get();
    }

    public Effect registerEffect(Effect effect, String name)
    {
        return CommonRegistryUtils.EFFECTS.register(name, () -> effect).get();
    }

    public Potion registerPotion(Potion potion, String name)
    {
        return CommonRegistryUtils.POTIONS.register(name, () -> potion).get();
    }

    public TileEntityType<?> registerTileEntityType(TileEntityType<?> type, String name)
    {
        return CommonRegistryUtils.TILE_ENTITY_TYPES.register(name, () -> type).get();
    }

    public SoundEvent registerSound(SoundEvent event, String name)
    {
        return CommonRegistryUtils.SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(this.modId, name))).get();
    }

    public SoundEvent registerMusicDisc(SoundEvent event, String name)
    {
        return CommonRegistryUtils.SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(this.modId, "music_disc." + name))).get();
    }

    public PointOfInterestType registerPointOfInterestType(PointOfInterestType type, String name)
    {
        return CommonRegistryUtils.POINT_OF_INTEREST_TYPES.register(name, () -> type).get();
    }

    public Biome registerBiome(Biome biome, String name)
    {
        return CommonRegistryUtils.BIOMES.register(name, () -> biome).get();
    }

    public BiomeProviderType<?, ?> registerBiomeProviderType(BiomeProviderType<?, ?> type, String name)
    {
        return CommonRegistryUtils.BIOME_PROVIDER_TYPES.register(name, () -> type).get();
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

    public ModDimension registerDimension(ModDimension dimension, String name)
    {
        return CommonRegistryUtils.MOD_DIMENSIONS.register(name, () -> dimension).get();
    }

    public WorldCarver<?> registerWorldCarver(WorldCarver<?> worldCarver, String name)
    {
        return CommonRegistryUtils.WORLD_CARVERS.register(name, () -> worldCarver).get();
    }

    public Feature<?> registerWorldFeature(Feature<?> feature, String name)
    {
        return CommonRegistryUtils.FEATURES.register(name, () -> feature).get();
    }

    public Placement<?> registerWorldPlacement(Placement<?> placement, String name)
    {
        return CommonRegistryUtils.PLACEMENTS.register(name, () -> placement).get();
    }

    public SurfaceBuilder<?> registerSurfaceBuilder(SurfaceBuilder<?> builder, String name)
    {
        return CommonRegistryUtils.SURFACE_BUILDERS.register(name, () -> builder).get();
    }

    public DataSerializerEntry registerDataSerializer(DataSerializerEntry data, String name)
    {
        return CommonRegistryUtils.DATA_SERIALIZERS.register(name, () -> data).get();
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