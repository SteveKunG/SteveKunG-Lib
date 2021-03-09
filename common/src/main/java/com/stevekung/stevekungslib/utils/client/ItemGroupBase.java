package com.stevekung.stevekungslib.utils.client;

import java.util.Comparator;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ItemGroupBase extends CreativeModeTab
{
    private ItemStack itemStack;
    private Comparator<ItemStack> sorter;

    public ItemGroupBase(String name)
    {
        super(name);
    }

    @Override
    public ItemStack makeIcon()
    {
        return this.itemStack;
    }

    @Override
    public void fillItemList(NonNullList<ItemStack> list)
    {
        super.fillItemList(list);

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