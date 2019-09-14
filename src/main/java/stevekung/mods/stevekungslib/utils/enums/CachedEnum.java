package stevekung.mods.stevekungslib.utils.enums;

import net.minecraft.item.DyeColor;
import net.minecraft.item.UseAction;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Rotation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.BiomeManager;

public class CachedEnum
{
    public static final Hand[] HAND = Hand.values();
    public static final DyeColor[] DYE_COLOR = DyeColor.values();
    public static final BiomeManager.BiomeType[] BIOME_TYPE = BiomeManager.BiomeType.values();
    public static final Direction[] DIRECTION = Direction.values();
    public static final Direction.Axis[] DIRECTION_AXIS = Direction.Axis.values();
    public static final TextFormatting[] TEXT_FORMATTING = TextFormatting.values();
    public static final UseAction[] USE_ACTION = UseAction.values();
    public static final Rotation[] ROTATION = Rotation.values();
}