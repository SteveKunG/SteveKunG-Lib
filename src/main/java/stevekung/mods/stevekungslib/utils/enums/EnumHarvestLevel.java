package stevekung.mods.stevekungslib.utils.enums;

public enum EnumHarvestLevel
{
    PICKAXE,
    AXE,
    SHOVEL;

    @Override
    public String toString()
    {
        return this.name().toLowerCase();
    }
}