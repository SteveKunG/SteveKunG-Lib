package com.stevekung.stevekungslib.utils;

import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ProjectileDispenseBehaviorBase extends ProjectileDispenseBehavior
{
    private final Class<? extends ProjectileEntity> projectile;
    private final boolean isArrow;

    public ProjectileDispenseBehaviorBase(Class<? extends ProjectileEntity> projectile)
    {
        this(projectile, false);
    }

    public ProjectileDispenseBehaviorBase(Class<? extends ProjectileEntity> projectile, boolean isArrow)
    {
        this.projectile = projectile;
        this.isArrow = isArrow;
    }

    @Override
    protected ProjectileEntity getProjectileEntity(World world, IPosition pos, ItemStack itemStack)
    {
        if (this.isArrow)
        {
            try
            {
                ArrowEntity arrow = (ArrowEntity)this.projectile.getConstructor(World.class, double.class, double.class, double.class).newInstance(world, pos.getX(), pos.getY(), pos.getZ());
                arrow.pickupStatus = ArrowEntity.PickupStatus.ALLOWED;
                return arrow;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            try
            {
                return this.projectile.getConstructor(World.class, double.class, double.class, double.class).newInstance(world, pos.getX(), pos.getY(), pos.getZ());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
}