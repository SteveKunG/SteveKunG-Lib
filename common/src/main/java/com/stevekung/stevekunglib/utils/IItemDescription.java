package com.stevekung.stevekunglib.utils;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public interface IItemDescription
{
    void addDescription(ItemStack itemStack, List<Component> list);
}