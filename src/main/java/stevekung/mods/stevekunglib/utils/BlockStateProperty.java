package stevekung.mods.stevekunglib.utils;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.IStringSerializable;
import stevekung.mods.stevekunglib.utils.enums.CachedEnum;

public class BlockStateProperty
{
    /**
     * Used for Custom TNT
     */
    public static final BooleanProperty EXPLODE = BooleanProperty.create("explode");

    /**
     * Used for Custom Basic Leaves
     */
    public static final BooleanProperty DECAYABLE = BooleanProperty.create("decayable");
    public static final BooleanProperty CHECK_DECAY = BooleanProperty.create("check_decay");

    /**
     * Used for Custom Block Facing
     */
    public static final DirectionProperty FACING_HORIZON = DirectionProperty.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final DirectionProperty FACING_ALL = DirectionProperty.create("facing");

    /**
     * Used for Custom Wood Log
     */
    public static final EnumProperty<EnumAxis> AXIS = EnumProperty.create("axis", EnumAxis.class);

    /**
     * Used for Custom Cake
     */
    public static final IntegerProperty BITES = IntegerProperty.create("bites", 0, 6);

    /**
     * Used for Custom Farmland
     */
    public static final IntegerProperty MOISTURE = IntegerProperty.create("moisture", 0, 1);

    /**
     * Used for Custom Snow Layer
     */
    public static final IntegerProperty LAYERS = IntegerProperty.create("layers", 1, 8);

    /**
     * Used for Custom Plant with tickable
     */
    public static final IntegerProperty AGE_7 = IntegerProperty.create("age", 0, 7);
    public static final IntegerProperty AGE_15 = IntegerProperty.create("age", 0, 15);

    public enum EnumAxis implements IStringSerializable
    {
        X,
        Y,
        Z;

        public static final EnumAxis[] values = EnumAxis.values();

        @Override
        public String toString()
        {
            return this.name().toLowerCase();
        }

        @Override
        public String getName()
        {
            return this.name().toLowerCase();
        }

        public static EnumAxis fromFacingAxis(Axis axis)
        {
            switch (SwitchAxis.AXIS_LOOKUP[axis.ordinal()])
            {
            case 0:
                return X;
            default:
            case 1:
                return Y;
            case 2:
                return Z;
            }
        }
    }

    public static class SwitchAxis
    {
        static final int[] AXIS_LOOKUP = new int[CachedEnum.axisValues.length];

        static
        {
            AXIS_LOOKUP[Axis.X.ordinal()] = 0;
            AXIS_LOOKUP[Axis.Y.ordinal()] = 1;
            AXIS_LOOKUP[Axis.Z.ordinal()] = 2;
        }
    }

    public static class SwitchEnumAxis
    {
        static final int[] AXIS_LOOKUP = new int[EnumAxis.values.length];

        static
        {
            AXIS_LOOKUP[EnumAxis.X.ordinal()] = 0;
            AXIS_LOOKUP[EnumAxis.Z.ordinal()] = 2;
        }
    }
}