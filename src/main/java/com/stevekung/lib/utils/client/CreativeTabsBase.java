package com.stevekung.lib.utils.client;

import java.util.Comparator;

import com.stevekung.lib.utils.LoggerSL;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabsBase extends CreativeTabs
{
    private ItemStack itemStack;
    private Comparator<ItemStack> tabSorter;

    public CreativeTabsBase(String name)
    {
        super(CreativeTabs.getNextID(), name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack createIcon()
    {
        return this.itemStack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIcon()
    {
        return this.itemStack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllRelevantItems(NonNullList<ItemStack> list)
    {
        super.displayAllRelevantItems(list);

        if (this.tabSorter != null)
        {
            try
            {
                list.sort(this.tabSorter);
            }
            catch (Exception e)
            {
                LoggerSL.error("Couldn't sort creative tabs", e);
            }
        }
    }

    public void setTabSorter(Comparator<ItemStack> tabSorter)
    {
        this.tabSorter = tabSorter;
    }

    public void setDisplayItemStack(ItemStack itemStack)
    {
        this.itemStack = itemStack;
    }
}