package com.stevekung.stevekungslib.utils;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

public interface IItemDescription
{
    void addDescription(ItemStack itemStack, List<ITextComponent> list);
}