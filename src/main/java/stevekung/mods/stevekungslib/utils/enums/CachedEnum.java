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
    public static final Hand[] handValues = Hand.values();
    public static final DyeColor[] dyeColorValues = DyeColor.values();
    public static final BiomeManager.BiomeType[] biomeValues = BiomeManager.BiomeType.values();
    public static final Direction[] facingValues = Direction.values();
    public static final Direction.Axis[] axisValues = Direction.Axis.values();
    public static final TextFormatting[] textFormatValues = TextFormatting.values();
    public static final UseAction[] actionValues = UseAction.values();
    public static final Rotation[] rotationValues = Rotation.values();
}