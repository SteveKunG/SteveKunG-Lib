package com.stevekung.stevekunglib.utils;

import java.util.List;

import com.google.common.collect.Lists;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.blockplacers.BlockPlacer;
import net.minecraft.world.level.levelgen.feature.blockplacers.BlockPlacerType;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratorConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderConfiguration;
import net.minecraft.world.level.material.Fluid;

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
    private final DeferredRegister<MenuType<?>> containerTypes;
    private final DeferredRegister<Item> items;
    private final DeferredRegister<RecipeSerializer<?>> recipeSerializers;
    private final DeferredRegister<ParticleType<?>> particleTypes;
    private final DeferredRegister<MobEffect> effects;
    private final DeferredRegister<Potion> potions;
    private final DeferredRegister<BlockEntityType<?>> tileEntityTypes;
    private final DeferredRegister<SoundEvent> soundEvents;
    private final DeferredRegister<PoiType> pointOfInterestTypes;
    private final DeferredRegister<WorldCarver<?>> worldCarvers;
    private final DeferredRegister<Feature<?>> features;
    private final DeferredRegister<FeatureDecorator<?>> placements;
    private final DeferredRegister<SurfaceBuilder<?>> surfaceBuilders;
    private final DeferredRegister<Attribute> attributes;
    private final DeferredRegister<BlockStateProviderType<?>> blockStateProviderTypes;
    private final DeferredRegister<BlockPlacerType<?>> blockPlacerTypes;
    private final DeferredRegister<FoliagePlacerType<?>> foliagePlacerTypes;
    private final DeferredRegister<TreeDecoratorType<?>> treeDecoratorTypes;

    public CommonRegistryUtils(String modId)
    {
        this.modId = modId;
        this.blocks = DeferredRegister.create(modId, Registry.BLOCK_REGISTRY);
        this.enchantments = DeferredRegister.create(modId, Registry.ENCHANTMENT_REGISTRY);
        this.entityTypes = DeferredRegister.create(modId, Registry.ENTITY_TYPE_REGISTRY);
        this.memoryModuleTypes = DeferredRegister.create(modId, Registry.MEMORY_MODULE_TYPE_REGISTRY);
        this.activities = DeferredRegister.create(modId, Registry.ACTIVITY_REGISTRY);
        this.schedules = DeferredRegister.create(modId, Registry.SCHEDULE_REGISTRY);
        this.sensorTypes = DeferredRegister.create(modId, Registry.SENSOR_TYPE_REGISTRY);
        this.villagerProfessions = DeferredRegister.create(modId, Registry.VILLAGER_PROFESSION_REGISTRY);
        this.fluids = DeferredRegister.create(modId, Registry.FLUID_REGISTRY);
        this.containerTypes = DeferredRegister.create(modId, Registry.MENU_REGISTRY);
        this.items = DeferredRegister.create(modId, Registry.ITEM_REGISTRY);
        this.recipeSerializers = DeferredRegister.create(modId, Registry.RECIPE_SERIALIZER_REGISTRY);
        this.particleTypes = DeferredRegister.create(modId, Registry.PARTICLE_TYPE_REGISTRY);
        this.effects = DeferredRegister.create(modId, Registry.MOB_EFFECT_REGISTRY);
        this.potions = DeferredRegister.create(modId, Registry.POTION_REGISTRY);
        this.tileEntityTypes = DeferredRegister.create(modId, Registry.BLOCK_ENTITY_TYPE_REGISTRY);
        this.soundEvents = DeferredRegister.create(modId, Registry.SOUND_EVENT_REGISTRY);
        this.pointOfInterestTypes = DeferredRegister.create(modId, Registry.POINT_OF_INTEREST_TYPE_REGISTRY);
        this.worldCarvers = DeferredRegister.create(modId, Registry.CARVER_REGISTRY);
        this.features = DeferredRegister.create(modId, Registry.FEATURE_REGISTRY);
        this.placements = DeferredRegister.create(modId, Registry.DECORATOR_REGISTRY);
        this.surfaceBuilders = DeferredRegister.create(modId, Registry.SURFACE_BUILDER_REGISTRY);
        this.attributes = DeferredRegister.create(modId, Registry.ATTRIBUTE_REGISTRY);
        this.blockStateProviderTypes = DeferredRegister.create(modId, Registry.BLOCK_STATE_PROVIDER_TYPE_REGISTRY);
        this.blockPlacerTypes = DeferredRegister.create(modId, Registry.BLOCK_PLACER_TYPE_REGISTRY);
        this.foliagePlacerTypes = DeferredRegister.create(modId, Registry.FOLIAGE_PLACER_TYPE_REGISTRY);
        this.treeDecoratorTypes = DeferredRegister.create(modId, Registry.TREE_DECORATOR_TYPE_REGISTRY);

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
        var type = builder.build(name);
        this.entityTypes.register(name, () -> type);
        return type;
    }

    public <T extends Mob> void registerEntityPlacement(EntityType<T> type, SpawnPlacements.Type placementType, Heightmap.Types heightMapType, SpawnPlacements.SpawnPredicate<T> predicate)
    {
        SpawnPlacements.register(type, placementType, heightMapType, predicate);
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

    public <T extends AbstractContainerMenu> MenuType<T> registerContainerType(String name, MenuType<T> type)
    {
        this.containerTypes.register(name, () -> type);
        return type;
    }

    public Item registerItem(String name, Item item)
    {
        this.items.register(name, () -> item);
        return item;
    }

    public <T extends Recipe<?>> RecipeSerializer<T> registerRecipeSerializer(String name, RecipeSerializer<T> recipe)
    {
        this.recipeSerializers.register(name, () -> recipe);
        return recipe;
    }

    public <T extends ParticleOptions> ParticleType<T> registerParticleType(String name, ParticleType<T> type)
    {
        this.particleTypes.register(name, () -> type);
        return type;
    }

    public MobEffect registerEffect(String name, MobEffect effect)
    {
        this.effects.register(name, () -> effect);
        return effect;
    }

    public Potion registerPotion(String name, Potion potion)
    {
        this.potions.register(name, () -> potion);
        return potion;
    }

    public <T extends BlockEntity> BlockEntityType<T> registerTileEntityType(String name, BlockEntityType<T> type)
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

    public PoiType registerPointOfInterestType(String name, PoiType type)
    {
        this.pointOfInterestTypes.register(name, () -> type);
        return type;
    }

    public <C extends CarverConfiguration> WorldCarver<C> registerWorldCarver(String name, WorldCarver<C> worldCarver)
    {
        this.worldCarvers.register(name, () -> worldCarver);
        return worldCarver;
    }

    public <C extends FeatureConfiguration> Feature<C> registerWorldFeature(String name, Feature<C> feature)
    {
        this.features.register(name, () -> feature);
        return feature;
    }

    public <C extends DecoratorConfiguration> FeatureDecorator<C> registerWorldPlacement(String name, FeatureDecorator<C> placement)
    {
        this.placements.register(name, () -> placement);
        return placement;
    }

    public <C extends SurfaceBuilderConfiguration> SurfaceBuilder<C> registerSurfaceBuilder(String name, SurfaceBuilder<C> builder)
    {
        this.surfaceBuilders.register(name, () -> builder);
        return builder;
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
    public void registerProjectileDispense(Item item, DispenseItemBehavior projectile)
    {
        DispenserBlock.registerBehavior(item, projectile);
    }

    public void registerAll()
    {
        for (var registry : this.deferredRegistries)
        {
            registry.register();
        }
    }
}