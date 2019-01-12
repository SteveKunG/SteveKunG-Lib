package stevekung.mods.stevekunglib.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.Potion;
import net.minecraft.tags.Tag;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import stevekung.mods.stevekunglib.utils.enums.EnumEntityTrackerType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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

    public void registerBlock(Block block, String name, @Nullable ItemGroup group, boolean useItemBlock)
    {
        ForgeRegistries.BLOCKS.register(block.setRegistryName(this.resourcePath + ":" + name));

        if (useItemBlock)
        {
            ItemBlock itemBlock = new ItemBlock(block, new Item.Builder().group(group));
            ForgeRegistries.ITEMS.register(itemBlock.setRegistryName(this.resourcePath + ":" + name));
        }
    }

    public void registerBlock(Block block, String name, ItemBlock itemBlock)
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
        //        FluidRegistry.registerFluid(fluid);TODO
    }

    public void registerForgeBucket(Fluid fluid)
    {
        //        FluidRegistry.addBucketForFluid(fluid);
    }

    public void registerTileEntity(TileEntityType<?> tile, String name)
    {
        ForgeRegistries.TILE_ENTITIES.register(tile.setRegistryName(this.resourcePath + ":" + name));
    }

    public void registerPotion(Potion potion, String name)
    {
        ForgeRegistries.POTIONS.register(potion.setRegistryName(this.resourcePath + ":" + name));
    }

    public void registerBiome(Biome biome, String name)
    {
        ForgeRegistries.BIOMES.register(biome.setRegistryName(this.resourcePath + ":" + name));
    }

    public void registerBiomeType(Biome biome, @Nonnull BiomeDictionary.Type... biomeType)
    {
        BiomeDictionary.addTypes(biome, biomeType);

        if (biome.isMutation()) // should put to mutation after registered biomes
        {
            Biome.MUTATION_TO_BASE_ID_MAP.put(biome, Biome.getIdForBiome(ForgeRegistries.BIOMES.getValue(new ResourceLocation(this.resourcePath + ":" + biome.parent))));
        }
    }

    public void registerEntity(EntityType<?> entity, String name, int backgroundColor, int foregroundColor)
    {
        this.registerEntity(entity, name, backgroundColor, foregroundColor, EnumEntityTrackerType.NORMAL);
    }

    public void registerEntity(EntityType<?> entity, String name, int backgroundColor, int foregroundColor, EnumEntityTrackerType type)
    {
        this.registerEntity(entity, name, backgroundColor, foregroundColor, type.getTrackingRange(), type.getUpdateFrequency());
    }

    public void registerEntity(EntityType<?> entity, String name, int backgroundColor, int foregroundColor, int trackingRange, int updateFrequency)
    {
        ForgeRegistries.ENTITIES.register(entity.setRegistryName(this.resourcePath + ":" + name));
        //        EntityRegistry.registerModEntity(new ResourceLocation(this.resourcePath + ":" + name), entity, this.resourcePath + "." + name, ID++, this.resourcePath, trackingRange, updateFrequency, true, backgroundColor, foregroundColor);
    }

    public void registerNonMobEntity(EntityType<?> entity, String name)
    {
        this.registerNonMobEntity(entity, name, EnumEntityTrackerType.NORMAL);
    }

    public void registerNonMobEntity(EntityType<?> entity, String name, EnumEntityTrackerType type)
    {
        this.registerNonMobEntity(entity, name, type.getTrackingRange(), type.getUpdateFrequency(), type.sendsVelocityUpdates());
    }

    public void registerNonMobEntity(EntityType<?> entity, String name, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
    {
        ForgeRegistries.ENTITIES.register(entity.setRegistryName(this.resourcePath + ":" + name));
        //        EntityRegistry.registerModEntity(new ResourceLocation(this.resourcePath + ":" + name), entity, this.resourcePath + "." + name, ID++, this.resourcePath, trackingRange, updateFrequency, sendsVelocityUpdates);TODO
    }

    public void registerEntityPlacement(EntityType<?> entity, EntitySpawnPlacementRegistry.SpawnPlacementType placementType, Heightmap.Type heightMapType, Tag<Block> tag)
    {
        EntitySpawnPlacementRegistry.register(entity, placementType, heightMapType, tag);
    }

    public void registerProjectileDispense(Item item, IBehaviorDispenseItem projectile)
    {
        BlockDispenser.registerDispenseBehavior(item, projectile);
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
        return LootTableList.register(new ResourceLocation(this.resourcePath + ":entities/" + name));
    }

    public ResourceLocation registerEntitySubLoot(String folder, String name)
    {
        return LootTableList.register(new ResourceLocation(this.resourcePath + ":entities/" + folder + "/" + name));
    }

    public ResourceLocation registerChestLoot(String name)
    {
        return LootTableList.register(new ResourceLocation(this.resourcePath + ":chests/" + name));
    }

    public ResourceLocation registerGameplayLoot(String name)
    {
        return LootTableList.register(new ResourceLocation(this.resourcePath + ":gameplay/" + name));
    }

    public ResourceLocation registerFishingLoot(String name)
    {
        return LootTableList.register(new ResourceLocation(this.resourcePath + ":gameplay/fishing/" + name));
    }
}