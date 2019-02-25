package stevekung.mods.stevekungslib.utils;

import java.util.List;

import net.minecraft.item.ItemStack;

public interface IItemDescription
{
    void addDescription(ItemStack itemStack, List<String> list);
}