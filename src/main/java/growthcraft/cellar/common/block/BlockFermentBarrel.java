package growthcraft.cellar.common.block;

import java.util.Random;

import javax.annotation.Nonnull;

import growthcraft.cellar.shared.Reference;
import growthcraft.core.shared.block.BlockFlags;
import growthcraft.core.shared.block.BlockUtils;
import growthcraft.core.shared.block.IRotatableBlock;
import growthcraft.cellar.common.tileentity.TileEntityFermentBarrel;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFermentBarrel extends BlockCellarContainer implements IRotatableBlock {

    private static final PropertyEnum<BarrelRotation> TYPE_BARREL_ROTATION = PropertyEnum.create("brotation", BarrelRotation.class);
    private static final PropertyBool TYPE_TAP = PropertyBool.create("hastap");

    public BlockFermentBarrel(String unlocalizedName) {
        super(Material.WOOD);
        setTileEntityType(TileEntityFermentBarrel.class);
        setHardness(2.5F);
        setSoundType(SoundType.WOOD);
        this.setDefaultState(this.getBlockState().getBaseState().withProperty(TYPE_TAP, false).withProperty(TYPE_BARREL_ROTATION, BarrelRotation.HOR_NORTH));
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }

    @Override
    protected boolean shouldRestoreBlockState(World world, BlockPos pos, ItemStack stack) {
        return true;
    }

    @Override
    protected boolean shouldDropTileStack(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return true;
    }

//	@Override
//	protected boolean playerDrainTank(World world, BlockPos pos, ILegacyFluidHandler tank, ItemStack held, EntityPlayer player)
//	{
//		final FluidStack available = GrowthcraftFluidUtils.playerDrainTank(world, pos, tank, held, player);
//		if (available != null && available.amount > 0)
//		{
//			GrowthcraftCellar.CELLAR_BUS.post(new EventTankDrained(player, world, pos, available));
//			return true;
//		}
//		return false;
//	}

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    /************
     * STATES
     ************/

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE_TAP, TYPE_BARREL_ROTATION);
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE_BARREL_ROTATION, BarrelRotation.values()[meta & 0x7]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE_BARREL_ROTATION).ordinal();
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        final TileEntityFermentBarrel te = getTileEntity(worldIn, pos);
        if (te != null) {
            if (te.hasTap())
                return state.withProperty(TYPE_TAP, true);
        }

        return state;
    }

    /************
     * RENDERS
     ************/

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    /************
     * COMPARATOR
     ************/
    @SuppressWarnings("deprecation")
    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
        final TileEntityFermentBarrel te = getTileEntity(world, pos);
        if (te != null) {
            return te.getDeviceProgressScaled(15);
        }
        return 0;
    }

    /************
     * ROTATION
     ************/

    @Override
    public boolean isRotatable(IBlockAccess world, BlockPos pos, EnumFacing side) {
        return true;
    }

    public void doRotateBlock(World world, BlockPos pos, IBlockState state, EnumFacing side) {
        BarrelRotation rot = state.getValue(TYPE_BARREL_ROTATION);
        switch (rot) {
            case HOR_NORTH:
                rot = BarrelRotation.HOR_WEST;
                break;
            case HOR_WEST:
                rot = BarrelRotation.HOR_SOUTH;
                break;
            case HOR_SOUTH:
                rot = BarrelRotation.HOR_EAST;
                break;
            case HOR_EAST:
                rot = BarrelRotation.VER_NORTH;
                break;

            case VER_NORTH:
                rot = BarrelRotation.VER_WEST;
                break;
            case VER_WEST:
                rot = BarrelRotation.VER_SOUTH;
                break;
            case VER_SOUTH:
                rot = BarrelRotation.VER_EAST;
                break;
            case VER_EAST:
                rot = BarrelRotation.HOR_NORTH;
                break;
        }

        world.setBlockState(pos, state.withProperty(TYPE_BARREL_ROTATION, rot), BlockFlags.UPDATE_AND_SYNC);
    }

    protected void setDefaultDirection(World world, BlockPos pos, IBlockState state) {
        if (!world.isRemote) {
            EnumFacing facing = BlockUtils.getDefaultDirection(world, pos, state);
            boolean isHorizontal = !(facing == EnumFacing.UP || facing == EnumFacing.DOWN);
            if (!isHorizontal)
                facing = EnumFacing.WEST;    // Default vertical direction

            BarrelRotation rot = BarrelRotation.fromFacing(facing, isHorizontal);
            world.setBlockState(pos, state.withProperty(TYPE_BARREL_ROTATION, rot), BlockFlags.UPDATE_AND_SYNC);
        }
    }

    protected BarrelRotation setOrientWhenPlacing(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer) {
        EnumFacing toDecideHorVer = EnumFacing.getDirectionFromEntityLiving(pos, placer);
        boolean isHorizontal = !(toDecideHorVer == EnumFacing.UP || toDecideHorVer == EnumFacing.DOWN);
        EnumFacing facing = EnumFacing.fromAngle(placer.rotationYaw);

        BarrelRotation rot = BarrelRotation.fromFacing(facing, isHorizontal);
        worldIn.setBlockState(pos, state.withProperty(TYPE_BARREL_ROTATION, rot), BlockFlags.UPDATE_AND_SYNC);
        return rot;
    }


    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        setOrientWhenPlacing(worldIn, pos, state, placer);
    }


    public static enum BarrelRotation implements IStringSerializable {
        HOR_NORTH(1),
        HOR_SOUTH(3),
        HOR_WEST(2),
        HOR_EAST(0),
        VER_NORTH(1),
        VER_SOUTH(3),
        VER_WEST(2),
        VER_EAST(0);

        public final int rotationCW;

        BarrelRotation(int rotationCW) {
            this.rotationCW = rotationCW;
        }

        public static BarrelRotation fromFacing(EnumFacing facing, boolean isHorizontal) {
            switch (facing) {
                case NORTH:
                    return isHorizontal ? HOR_NORTH : VER_NORTH;
                case SOUTH:
                    return isHorizontal ? HOR_SOUTH : VER_SOUTH;
                default:
                case WEST:
                    return isHorizontal ? HOR_WEST : VER_WEST;
                case EAST:
                    return isHorizontal ? HOR_EAST : VER_EAST;
                case UP:
                case DOWN:
                    // NOTE: Should be not used.
                    if (isHorizontal)
                        throw new IllegalArgumentException("Must be vertical!");
                    return VER_WEST;
            }
        }

        public EnumFacing toFacing() {
            switch (this) {
                case HOR_NORTH:
                case VER_NORTH:
                    return EnumFacing.NORTH;
                case HOR_SOUTH:
                case VER_SOUTH:
                    return EnumFacing.SOUTH;
                default:
                case HOR_WEST:
                case VER_WEST:
                    return EnumFacing.WEST;
                case HOR_EAST:
                case VER_EAST:
                    return EnumFacing.EAST;
            }
        }

        @Override
        public String getName() {
            return toString().toLowerCase();
        }
    }
}
