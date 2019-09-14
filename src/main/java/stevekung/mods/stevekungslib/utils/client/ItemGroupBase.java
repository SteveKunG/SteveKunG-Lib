package stevekung.mods.stevekungslib.utils.client;

import java.util.Comparator;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemGroupBase extends ItemGroup
{
    private ItemStack itemStack;
    private Comparator<ItemStack> sorter;

    public ItemGroupBase(String name)
    {
        super(name);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack createIcon()
    {
        return this.itemStack;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
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