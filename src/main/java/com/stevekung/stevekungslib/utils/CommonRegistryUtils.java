package com.stevekung.stevekungslib.utils;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attribute;
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
    private final List<DeferredRegister<?>> deferredRegistries = Lists.newArrayList();
    private final String modId;

    private final DeferredRegister<Block> blocks;
    private final DeferredRegister<Enchantment> enchantments;
    private final DeferredRegister<EntityType<?>> entityTypes;
    private final DeferredRegister<MemoryModuleType<?>> memoryModuleTypes;
    private final DeferredRegister<Activity> activities;
    private final DeferredRegister<Schedule> schedules;
    private final DeferredRegister<SensorType<?>> sensorTypes;
    private final DeferredRegister<VillagerProfession> villagerProfessions;
    private final DeferredRegister<Fluid> fluids;
    private final DeferredRegister<ContainerType<?>> containerTypes;
    private final DeferredRegister<Item> items;
    private final DeferredRegister<IRecipeSerializer<?>> recipeSerializers;
    private final DeferredRegister<ParticleType<?>> particleTypes;
    private final DeferredRegister<Effect> effects;
    private final DeferredRegister<Potion> potions;
    private final DeferredRegister<TileEntityType<?>> tileEntityTypes;
    private final DeferredRegister<SoundEvent> soundEvents;
    private final DeferredRegister<PointOfInterestType> pointOfInterestTypes;
    private final DeferredRegister<WorldCarver<?>> worldCarvers;
    private final DeferredRegister<Feature<?>> features;
    private final DeferredRegister<Placement<?>> placements;
    private final DeferredRegister<SurfaceBuilder<?>> surfaceBuilders;
    private final DeferredRegister<DataSerializerEntry> dataSerializers;
    private final DeferredRegister<Attribute> attributes;
    private final DeferredRegister<BlockStateProviderType<?>> blockStateProviderTypes;
    private final DeferredRegister<BlockPlacerType<?>> blockPlacerTypes;
    private final DeferredRegister<FoliagePlacerType<?>> foliagePlacerTypes;
    private final DeferredRegister<TreeDecoratorType<?>> treeDecoratorTypes;

    public CommonRegistryUtils(String modId)
    {
        this.modId = modId;
        this.blocks = DeferredRegister.create(ForgeRegistries.BLOCKS, modId);
        this.enchantments = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, modId);
        this.entityTypes = DeferredRegister.create(ForgeRegistries.ENTITIES, modId);
        this.memoryModuleTypes = DeferredRegister.create(ForgeRegistries.MEMORY_MODULE_TYPES, modId);
        this.activities = DeferredRegister.create(ForgeRegistries.ACTIVITIES, modId);
        this.schedules = DeferredRegister.create(ForgeRegistries.SCHEDULES, modId);
        this.sensorTypes = DeferredRegister.create(ForgeRegistries.SENSOR_TYPES, modId);
        this.villagerProfessions = DeferredRegister.create(ForgeRegistries.PROFESSIONS, modId);
        this.fluids = DeferredRegister.create(ForgeRegistries.FLUIDS, modId);
        this.containerTypes = DeferredRegister.create(ForgeRegistries.CONTAINERS, modId);
        this.items = DeferredRegister.create(ForgeRegistries.ITEMS, modId);
        this.recipeSerializers = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, modId);
        this.particleTypes = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, modId);
        this.effects = DeferredRegister.create(ForgeRegistries.POTIONS, modId);
        this.potions = DeferredRegister.create(ForgeRegistries.POTION_TYPES, modId);
        this.tileEntityTypes = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, modId);
        this.soundEvents = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, modId);
        this.pointOfInterestTypes = DeferredRegister.create(ForgeRegistries.POI_TYPES, modId);
        this.worldCarvers = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, modId);
        this.features = DeferredRegister.create(ForgeRegistries.FEATURES, modId);
        this.placements = DeferredRegister.create(ForgeRegistries.DECORATORS, modId);
        this.surfaceBuilders = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, modId);
        this.dataSerializers = DeferredRegister.create(ForgeRegistries.DATA_SERIALIZERS, modId);
        this.attributes = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, modId);
        this.blockStateProviderTypes = DeferredRegister.create(ForgeRegistries.BLOCK_STATE_PROVIDER_TYPES, modId);
        this.blockPlacerTypes = DeferredRegister.create(ForgeRegistries.BLOCK_PLACER_TYPES, modId);
        this.foliagePlacerTypes = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, modId);
        this.treeDecoratorTypes = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, modId);

        this.deferredRegistries.add(this.blocks);
        this.deferredRegistries.add(this.enchantments);
        this.deferredRegistries.add(this.entityTypes);
        this.deferredRegistries.add(this.memoryModuleTypes);
        this.deferredRegistries.add(this.activities);
        this.deferredRegistries.add(this.schedules);
        this.deferredRegistries.add(this.sensorTypes);
        this.deferredRegistries.add(this.villagerProfessions);
        this.deferredRegistries.add(this.fluids);
        this.deferredRegistries.add(this.containerTypes);
        this.deferredRegistries.add(this.items);
        this.deferredRegistries.add(this.recipeSerializers);
        this.deferredRegistries.add(this.particleTypes);
        this.deferredRegistries.add(this.effects);
        this.deferredRegistries.add(this.potions);
        this.deferredRegistries.add(this.tileEntityTypes);
        this.deferredRegistries.add(this.soundEvents);
        this.deferredRegistries.add(this.pointOfInterestTypes);
        this.deferredRegistries.add(this.worldCarvers);
        this.deferredRegistries.add(this.features);
        this.deferredRegistries.add(this.placements);
        this.deferredRegistries.add(this.surfaceBuilders);
        this.deferredRegistries.add(this.dataSerializers);
        this.deferredRegistries.add(this.attributes);
        this.deferredRegistries.add(this.blockStateProviderTypes);
        this.deferredRegistries.add(this.blockPlacerTypes);
        this.deferredRegistries.add(this.foliagePlacerTypes);
        this.deferredRegistries.add(this.treeDecoratorTypes);
    }

    // Game Object
    public <B extends Block> B registerBlock(String name, B block, Item.Properties properties)
    {
        return this.registerBlock(name, block, properties, true);
    }

    public <B extends Block> B registerBlock(String name, B block)
    {
        return this.registerBlock(name, block, null, false);
    }

    public <B extends Block> B registerBlock(String name, B block, Item.Properties properties, boolean useBlockItem)
    {
        this.blocks.register(name, () -> block);

        if (useBlockItem)
        {
            this.items.register(name, () -> new BlockItem(block, properties));
        }
        return block;
    }

    public <B extends Block> B registerBlock(String name, B block, BlockItem itemBlock)
    {
        this.blocks.register(name, () -> block);
        this.items.register(name, () -> itemBlock);
        return block;
    }

    public Enchantment registerEnchantment(String name, Enchantment enchantment)
    {
        this.enchantments.register(name, () -> enchantment);
        return enchantment;
    }

    public <E extends Entity> EntityType<E> registerEntityType(String name, EntityType.Builder<E> builder)
    {
        EntityType<E> type = builder.build(name);
        this.entityTypes.register(name, () -> type);
        return type;
    }

    public <T extends MobEntity> void registerEntityPlacement(EntityType<T> type, EntitySpawnPlacementRegistry.PlacementType placementType, Heightmap.Type heightMapType, EntitySpawnPlacementRegistry.IPlacementPredicate<T> predicate)
    {
        EntitySpawnPlacementRegistry.register(type, placementType, heightMapType, predicate);
    }

    public MemoryModuleType<?> registerMemoryModuleType(String name, MemoryModuleType<?> memoryModule)
    {
        this.memoryModuleTypes.register(name, () -> memoryModule);
        return memoryModule;
    }

    public Activity registerEntityActivity(String name, Activity activity)
    {
        this.activities.register(name, () -> activity);
        return activity;
    }

    public Schedule registerEntitySchedule(String name, Schedule schedule)
    {
        this.schedules.register(name, () -> schedule);
        return schedule;
    }

    public <U extends Sensor<?>> SensorType<U> registerEntitySensorType(String name, SensorType<U> sensorType)
    {
        this.sensorTypes.register(name, () -> sensorType);
        return sensorType;
    }

    public VillagerProfession registerVillagerProfession(String name, VillagerProfession profession)
    {
        this.villagerProfessions.register(name, () -> profession);
        return profession;
    }

    public <T extends Fluid> T registerFluid(String name, T fluid)
    {
        this.fluids.register(name, () -> fluid);
        return fluid;
    }

    public <T extends Container> ContainerType<T> registerContainerType(String name, ContainerType<T> type)
    {
        this.containerTypes.register(name, () -> type);
        return type;
    }

    public Item registerItem(String name, Item item)
    {
        this.items.register(name, () -> item);
        return item;
    }

    public <T extends IRecipe<?>> IRecipeSerializer<T> registerRecipeSerializer(String name, IRecipeSerializer<T> recipe)
    {
        this.recipeSerializers.register(name, () -> recipe);
        return recipe;
    }

    public <T extends IParticleData> ParticleType<T> registerParticleType(String name, ParticleType<T> type)
    {
        this.particleTypes.register(name, () -> type);
        return type;
    }

    public Effect registerEffect(String name, Effect effect)
    {
        this.effects.register(name, () -> effect);
        return effect;
    }

    public Potion registerPotion(String name, Potion potion)
    {
        this.potions.register(name, () -> potion);
        return potion;
    }

    public <T extends TileEntity> TileEntityType<T> registerTileEntityType(String name, TileEntityType<T> type)
    {
        this.tileEntityTypes.register(name, () -> type);
        return type;
    }

    public SoundEvent registerSound(String name, SoundEvent event)
    {
        this.soundEvents.register(name, () -> event);
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
        this.pointOfInterestTypes.register(name, () -> type);
        return type;
    }

    public <C extends ICarverConfig> WorldCarver<C> registerWorldCarver(String name, WorldCarver<C> worldCarver)
    {
        this.worldCarvers.register(name, () -> worldCarver);
        return worldCarver;
    }

    public <C extends IFeatureConfig> Feature<C> registerWorldFeature(String name, Feature<C> feature)
    {
        this.features.register(name, () -> feature);
        return feature;
    }

    public <C extends IPlacementConfig> Placement<C> registerWorldPlacement(String name, Placement<C> placement)
    {
        this.placements.register(name, () -> placement);
        return placement;
    }

    public <C extends ISurfaceBuilderConfig> SurfaceBuilder<C> registerSurfaceBuilder(String name, SurfaceBuilder<C> builder)
    {
        this.surfaceBuilders.register(name, () -> builder);
        return builder;
    }

    public DataSerializerEntry registerDataSerializer(String name, DataSerializerEntry data)
    {
        this.dataSerializers.register(name, () -> data);
        return data;
    }

    public Attribute registerAttribute(String name, Attribute attribute)
    {
        this.attributes.register(name, () -> attribute);
        return attribute;
    }

    public <P extends BlockStateProvider> BlockStateProviderType<P> registerBlockStateProviderType(String name, BlockStateProviderType<P> type)
    {
        this.blockStateProviderTypes.register(name, () -> type);
        return type;
    }

    public <P extends BlockPlacer> BlockPlacerType<P> registerBlockPlacerType(String name, BlockPlacerType<P> type)
    {
        this.blockPlacerTypes.register(name, () -> type);
        return type;
    }

    public <P extends FoliagePlacer> FoliagePlacerType<P> registerFoliagePlacerType(String name, FoliagePlacerType<P> type)
    {
        this.foliagePlacerTypes.register(name, () -> type);
        return type;
    }

    public <P extends TreeDecorator> TreeDecoratorType<P> registerTreeDecoratorType(String name, TreeDecoratorType<P> type)
    {
        this.treeDecoratorTypes.register(name, () -> type);
        return type;
    }

    // Others
    public void registerProjectileDispense(Item item, IDispenseItemBehavior projectile)
    {
        DispenserBlock.registerDispenseBehavior(item, projectile);
    }

    public void registerAll()
    {
        for (DeferredRegister<?> registry : this.deferredRegistries)
        {
            registry.register(CommonUtils.getModEventBus());
        }
    }
}