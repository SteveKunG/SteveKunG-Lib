package stevekung.mods.stevekunglib.utils;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface IItemDescription
{
    void addDescription(ItemStack itemStack, List<String> list);
}