package stevekung.mods.stevekungslib.utils.enums;

public enum HarvestLevel
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