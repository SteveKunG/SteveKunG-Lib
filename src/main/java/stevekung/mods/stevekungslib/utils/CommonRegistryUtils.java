package stevekung.mods.stevekungslib.utils;

import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.entity.*;
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
import net.minecraft.world.storage.loot.LootTables;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import stevekung.mods.stevekungslib.utils.enums.EntityTrackerType;

public class CommonRegistryUtils
{
    private final String resourcePath;

    public CommonRegistryUtils(@Nonnull String resourcePath)
    {
        this.resourcePath = resourcePath;
    }

    public void registerBlock(Block block, String name, ItemGroup group)
    {
        this.registerBlock(block, name, group, true);
    }

    public void registerBlock(Block block, String name, @Nullable ItemGroup group, boolean useBlockItem)
    {
        ForgeRegistries.BLOCKS.register(block.setRegistryName(this.resourcePath + ":" + name));

        if (useBlockItem)
        {
            BlockItem itemBlock = new BlockItem(block, new Item.Properties().group(group));
            ForgeRegistries.ITEMS.register(itemBlock.setRegistryName(this.resourcePath + ":" + name));
        }
    }

    public void registerBlock(Block block, String name, BlockItem itemBlock)
    {
        ForgeRegistries.BLOCKS.register(block.setRegistryName(this.resourcePath + ":" + name));
        ForgeRegistries.ITEMS.register(itemBlock.setRegistryName(this.resourcePath + ":" + name));
    }

    public void registerItem(Item item, String name)
    {
        ForgeRegistries.ITEMS.register(item.setRegistryName(this.resourcePath + ":" + name));
    }

    public void registerFluid(Fluid fluid)
    {
        //FluidRegistry.registerFluid(fluid);TODO
    }

    public void registerForgeBucket(Fluid fluid)
    {
        //FluidRegistry.addBucketForFluid(fluid);TODO
    }

    public <T extends TileEntity> void registerTileEntity(Supplier<? extends T> factory, String name)
    {
        ForgeRegistries.TILE_ENTITIES.register(TileEntityType.Builder.create(factory).build(null).setRegistryName(this.resourcePath + ":" + name));
    }

    public void registerPotion(Effect potion, String name)
    {
        ForgeRegistries.POTIONS.register(potion.setRegistryName(this.resourcePath + ":" + name));
    }

    public void registerBiome(Biome biome, String name)
    {
        ForgeRegistries.BIOMES.register(biome.setRegistryName(this.resourcePath + ":" + name));
    }

    @SuppressWarnings("deprecation")
    public void registerBiomeType(Biome biome, @Nonnull BiomeDictionary.Type... biomeType)
    {
        BiomeDictionary.addTypes(biome, biomeType);

        if (biome.isMutation()) // should put to mutation after registered biomes
        {
            Biome.MUTATION_TO_BASE_ID_MAP.put(biome, Registry.BIOME.getId(ForgeRegistries.BIOMES.getValue(new ResourceLocation(this.resourcePath + ":" + biome.getParent()))));
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
        ForgeRegistries.ENTITIES.register(EntityType.Builder.create(entity, classifi).setTrackingRange(trackingRange).setUpdateInterval(updateFrequency).setShouldReceiveVelocityUpdates(sendsVelocityUpdates).build(this.resourcePath + ":" + name));
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
        ResourceLocation resource = new ResourceLocation(this.resourcePath + ":" + name);
        SoundEvent event = new SoundEvent(resource).setRegistryName(resource);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }

    public SoundEvent registerRecord(String name)
    {
        return this.registerSound("record." + name);
    }

    public ResourceLocation registerEntityLoot(String name)
    {
        return LootTables.register(new ResourceLocation(this.resourcePath + ":entities/" + name));
    }

    public ResourceLocation registerEntitySubLoot(String folder, String name)
    {
        return LootTables.register(new ResourceLocation(this.resourcePath + ":entities/" + folder + "/" + name));
    }

    public ResourceLocation registerChestLoot(String name)
    {
        return LootTables.register(new ResourceLocation(this.resourcePath + ":chests/" + name));
    }

    public ResourceLocation registerGameplayLoot(String name)
    {
        return LootTables.register(new ResourceLocation(this.resourcePath + ":gameplay/" + name));
    }

    public ResourceLocation registerFishingLoot(String name)
    {
        return LootTables.register(new ResourceLocation(this.resourcePath + ":gameplay/fishing/" + name));
    }
}