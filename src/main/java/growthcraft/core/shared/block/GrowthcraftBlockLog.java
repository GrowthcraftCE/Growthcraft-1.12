package growthcraft.core.shared.block;

import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

public class GrowthcraftBlockLog extends BlockLog {

    public static final PropertyEnum<EnumAxis> LOG_AXIS = PropertyEnum.<BlockLog.EnumAxis>create("axis", BlockLog.EnumAxis.class);

    public GrowthcraftBlockLog(String modid, String unlocalizedName) {
        super();
        this.setTranslationKey(unlocalizedName);
        this.setRegistryName(new ResourceLocation(modid, unlocalizedName));
        this.setHardness(2.0F);
        this.setSoundType(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = this.getDefaultState();

        switch (meta & 12) {
            case 0:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
                break;
            case 4:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
                break;
            case 8:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
                break;
            default:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
        }

        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;

        switch (state.getValue(LOG_AXIS)) {
            case Y:
                i = 0;
                break;
            case X:
                i = 4;
                break;
            case Z:
                i = 8;
                break;
            case NONE:
                i = 12;
                break;
            default:
                i = 0;
        }

        return i;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{LOG_AXIS});
    }

}