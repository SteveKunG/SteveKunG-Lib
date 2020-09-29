package com.stevekung.stevekungslib.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.schedule.Schedule;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.BlockStateProviderType;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
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
    private static DeferredRegister<WorldCarver<?>> WORLD_CARVERS;
    private static DeferredRegister<Feature<?>> FEATURES;
    private static DeferredRegister<Placement<?>> PLACEMENTS;
    private static DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS;
    private static DeferredRegister<DataSerializerEntry> DATA_SERIALIZERS;
    private static DeferredRegister<Attribute> ATTRIBUTES;
    private static DeferredRegister<BlockStateProviderType<?>> BLOCK_STATE_PROVIDER_TYPES;
    private static DeferredRegister<BlockPlacerType<?>> BLOCK_PLACER_TYPES;
    private static DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES;
    private static DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES;

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
        CommonRegistryUtils.WORLD_CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, modId);
        CommonRegistryUtils.FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, modId);
        CommonRegistryUtils.PLACEMENTS = DeferredRegister.create(ForgeRegistries.DECORATORS, modId);
        CommonRegistryUtils.SURFACE_BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, modId);
        CommonRegistryUtils.DATA_SERIALIZERS = DeferredRegister.create(ForgeRegistries.DATA_SERIALIZERS, modId);
        CommonRegistryUtils.ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, modId);
        CommonRegistryUtils.BLOCK_STATE_PROVIDER_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_STATE_PROVIDER_TYPES, modId);
        CommonRegistryUtils.BLOCK_PLACER_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_PLACER_TYPES, modId);
        CommonRegistryUtils.FOLIAGE_PLACER_TYPES = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, modId);
        CommonRegistryUtils.TREE_DECORATOR_TYPES = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, modId);

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
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.WORLD_CARVERS);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.FEATURES);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.PLACEMENTS);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.SURFACE_BUILDERS);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.DATA_SERIALIZERS);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.ATTRIBUTES);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.BLOCK_STATE_PROVIDER_TYPES);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.BLOCK_PLACER_TYPES);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.FOLIAGE_PLACER_TYPES);
        CommonRegistryUtils.ALL_REGISTRIES.add(CommonRegistryUtils.TREE_DECORATOR_TYPES);
    }

    // Game Object
    public Block registerBlock(String name, Block block, ItemGroup group)
    {
        return this.registerBlock(name, block, group, true);
    }

    public Block registerBlock(String name, Block block)
    {
        return this.registerBlock(name, block, null, false);
    }

    public Block registerBlock(String name, Block block, ItemGroup group, boolean useBlockItem)
    {
        CommonRegistryUtils.BLOCKS.register(name, () -> block);

        if (useBlockItem)
        {
            CommonRegistryUtils.ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().group(group)));
        }
        return block;
    }

    public Block registerBlock(String name, Block block, BlockItem itemBlock)
    {
        CommonRegistryUtils.BLOCKS.register(name, () -> block);
        CommonRegistryUtils.ITEMS.register(name, () -> itemBlock);
        return block;
    }

    public Enchantment registerEnchantment(String name, Enchantment enchantment)
    {
        CommonRegistryUtils.ENCHANTMENTS.register(name, () -> enchantment);
        return enchantment;
    }

    public <E extends Entity> EntityType<E> registerEntityType(String name, EntityType.Builder<E> builder)
    {
        EntityType<E> type = builder.build(name);
        CommonRegistryUtils.ENTITY_TYPES.register(name, () -> type);
        return type;
    }

    public <T extends MobEntity> void registerEntityPlacement(EntityType<T> type, EntitySpawnPlacementRegistry.PlacementType placementType, Heightmap.Type heightMapType, EntitySpawnPlacementRegistry.IPlacementPredicate<T> predicate)
    {
        EntitySpawnPlacementRegistry.register(type, placementType, heightMapType, predicate);
    }

    public void registerEntityAttributes(EntityType<? extends LivingEntity> type, AttributeModifierMap.MutableAttribute mutableAttribute)
    {
        GlobalEntityTypeAttributes.put(type, mutableAttribute.create());
    }

    public MemoryModuleType<?> registerMemoryModuleType(String name, MemoryModuleType<?> memoryModule)
    {
        CommonRegistryUtils.MEMORY_MODULE_TYPES.register(name, () -> memoryModule);
        return memoryModule;
    }

    public Activity registerEntityActivity(String name, Activity activity)
    {
        CommonRegistryUtils.ACTIVITIES.register(name, () -> activity);
        return activity;
    }

    public Schedule registerEntitySchedule(String name, Schedule schedule)
    {
        CommonRegistryUtils.SCHEDULES.register(name, () -> schedule);
        return schedule;
    }

    public <U extends Sensor<?>> SensorType<U> registerEntitySensorType(String name, SensorType<U> sensorType)
    {
        CommonRegistryUtils.SENSOR_TYPES.register(name, () -> sensorType);
        return sensorType;
    }

    public VillagerProfession registerVillagerProfession(String name, VillagerProfession profession)
    {
        CommonRegistryUtils.VILLAGER_PROFESSIONS.register(name, () -> profession);
        return profession;
    }

    public Fluid registerFluid(String name, Fluid fluid)
    {
        CommonRegistryUtils.FLUIDS.register(name, () -> fluid);
        return fluid;
    }

    public <T extends Container> ContainerType<T> registerContainerType(String name, ContainerType<T> type)
    {
        CommonRegistryUtils.CONTAINER_TYPES.register(name, () -> type);
        return type;
    }

    public Item registerItem(String name, Item item)
    {
        CommonRegistryUtils.ITEMS.register(name, () -> item);
        return item;
    }

    public <T extends IRecipe<?>> IRecipeSerializer<T> registerRecipeSerializer(String name, IRecipeSerializer<T> recipe)
    {
        CommonRegistryUtils.RECIPE_SERIALIZERS.register(name, () -> recipe);
        return recipe;
    }

    public <T extends IParticleData> ParticleType<T> registerParticleType(String name, ParticleType<T> type)
    {
        CommonRegistryUtils.PARTICLE_TYPES.register(name, () -> type);
        return type;
    }

    public Effect registerEffect(String name, Effect effect)
    {
        CommonRegistryUtils.EFFECTS.register(name, () -> effect);
        return effect;
    }

    public Potion registerPotion(String name, Potion potion)
    {
        CommonRegistryUtils.POTIONS.register(name, () -> potion);
        return potion;
    }

    public <T extends TileEntity> TileEntityType<T> registerTileEntityType(String name, TileEntityType<T> type)
    {
        CommonRegistryUtils.TILE_ENTITY_TYPES.register(name, () -> type);
        return type;
    }

    public SoundEvent registerSound(String name, SoundEvent event)
    {
        CommonRegistryUtils.SOUND_EVENTS.register(name, () -> event);
        return event;
    }

    public SoundEvent createSound(String name)
    {
        return new SoundEvent(new ResourceLocation(this.modId, name));
    }

    public void registerMusicDisc(String name, SoundEvent event)
    {
        this.registerSound("music_disc." + name, event);
    }

    public PointOfInterestType registerPointOfInterestType(String name, PointOfInterestType type)
    {
        CommonRegistryUtils.POINT_OF_INTEREST_TYPES.register(name, () -> type);
        return type;
    }

    public <C extends ICarverConfig> WorldCarver<C> registerWorldCarver(String name, WorldCarver<C> worldCarver)
    {
        CommonRegistryUtils.WORLD_CARVERS.register(name, () -> worldCarver);
        return worldCarver;
    }

    public <C extends IFeatureConfig> Feature<C> registerWorldFeature(String name, Feature<C> feature)
    {
        CommonRegistryUtils.FEATURES.register(name, () -> feature);
        return feature;
    }

    public <C extends IPlacementConfig> Placement<C> registerWorldPlacement(String name, Placement<C> placement)
    {
        CommonRegistryUtils.PLACEMENTS.register(name, () -> placement);
        return placement;
    }

    public <C extends ISurfaceBuilderConfig> SurfaceBuilder<C> registerSurfaceBuilder(String name, SurfaceBuilder<C> builder)
    {
        CommonRegistryUtils.SURFACE_BUILDERS.register(name, () -> builder);
        return builder;
    }

    public DataSerializerEntry registerDataSerializer(String name, DataSerializerEntry data)
    {
        CommonRegistryUtils.DATA_SERIALIZERS.register(name, () -> data);
        return data;
    }

    public Attribute registerAttribute(String name, Attribute attribute)
    {
        CommonRegistryUtils.ATTRIBUTES.register(name, () -> attribute);
        return attribute;
    }

    public <P extends BlockStateProvider> BlockStateProviderType<P> registerBlockStateProviderType(String name, BlockStateProviderType<P> type)
    {
        CommonRegistryUtils.BLOCK_STATE_PROVIDER_TYPES.register(name, () -> type);
        return type;
    }

    public <P extends BlockPlacer> BlockPlacerType<P> registerBlockPlacerType(String name, BlockPlacerType<P> type)
    {
        CommonRegistryUtils.BLOCK_PLACER_TYPES.register(name, () -> type);
        return type;
    }

    public <P extends FoliagePlacer> FoliagePlacerType<P> registerFoliagePlacerType(String name, FoliagePlacerType<P> type)
    {
        CommonRegistryUtils.FOLIAGE_PLACER_TYPES.register(name, () -> type);
        return type;
    }

    public <P extends TreeDecorator> TreeDecoratorType<P> registerTreeDecoratorType(String name, TreeDecoratorType<P> type)
    {
        CommonRegistryUtils.TREE_DECORATOR_TYPES.register(name, () -> type);
        return type;
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