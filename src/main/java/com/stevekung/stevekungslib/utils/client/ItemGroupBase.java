package com.stevekung.stevekungslib.utils.client;

import java.util.Comparator;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemGroupBase extends ItemGroup
{
    private ItemStack itemStack;
    private Comparator<ItemStack> sorter;

    public ItemGroupBase(String name)
    {
        super(name);
    }

    @Override
    public ItemStack createIcon()
    {
        return this.itemStack;
    }

    @Override
    public void fill(NonNullList<ItemStack> list)
    {
        super.fill(list);

        if (this.sorter != null)
        {
            try
            {
                list.sort(this.sorter);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void setTabSorter(Comparator<ItemStack> tabSorter)
    {
        this.sorter = tabSorter;
    }

    public void setDisplayItemStack(ItemStack itemStack)
    {
        this.itemStack = itemStack;
    }
}