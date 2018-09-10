package growthcraft.core.shared.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nonnull;

/**
 * BlockPaddyBase
 *
 * The BlockPaddyBase is designed to be mainly used for the BlockPaddy, which is a Rice Paddy for growing rice. There
 * could be other uses for flooding a field of FARMLAND for gorwing crops.
 *
 * A paddy is designed to be created by activating a FARMLAND block with a Growthcraft Cultivator and then adding a
 * Fluid of choice to the paddy block. Typically we would only add water to a paddy block, but you never know what the
 * future entails (hint towards liquid uranium maybe for hyper growing crops?).
 *
 * Ideally you should be able to place a FluidStack into a BlockPaddy that will in-turn "flood" the neighbor blocks.
 *
 */
public class BlockPaddyBase extends GrowthcraftBlockBase implements IPaddy {

    // FluidTank to store the fluid that will hydrate the paddy.
    private FluidTank fluidTank0;

    public static final PropertyBool IS_FILLED = PropertyBool.create("is_filled");
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");

    public BlockPaddyBase(Material material) {
        super(material);
        this.setTickRandomly(true);
        this.setDefaultState(
            this.blockState.getBaseState()
                    .withProperty(IS_FILLED, false)
                    .withProperty(NORTH, false)
                    .withProperty(EAST, false)
                    .withProperty(SOUTH, false)
                    .withProperty(WEST, false)
        );

        // TODO: Add FluidTank capacity to User Config.
        fluidTank0 = new FluidTank(1000);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{ IS_FILLED, NORTH, EAST, SOUTH, WEST });
    }

    /**
     * Retrieve the fluid that is currently stored in the RicePaddy.
     *
     * @return
     */
    @Nonnull
    @Override
    public FluidStack getFluidStack() {
        return this.fluidTank0.getFluid();
    }


    @Nonnull
    @Override
    public Fluid getFillingFluid() {

        // TODO: return the fluid that was used to fill the paddy.

        return null;
    }

    @Override
    public int getMaxPaddyMeta(IBlockAccess world, int x, int y, int z) {

        return 0;
    }

    /**
     * Determins if this block is filled with a fluid or not.
     *
     * @param world
     * @param x
     * @param y
     * @param z
     * @param meta
     * @return
     */
    @Override
    public boolean isFilledWithFluid(IBlockAccess world, int x, int y, int z, int meta) {
        return this.getDefaultState().getValue(IS_FILLED);
    }

    /**
     * Determines if this block can connect to the given block passed through the parameters.
     *
     * @param world
     * @param pos BlockPos if the block the check.
     * @param meta
     * @return Returns true if the passed block is an instance of BlockPaddyBase
     */
    public boolean canConnectPaddyTo(IBlockAccess world, BlockPos pos, int meta) {
        IBlockState state = world.getBlockState(pos);
        return BlockCheck.isBlockPaddy(state.getBlock());
    }

    @Override
    public boolean isBelowFillingFluid(IBlockAccess world, int x, int y, int z) {
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);

        // TODO: If activated with Water Bucket, we need to fill the paddy.

        // TODO: If activated with a Rice Seed and the paddy is flooded, then we need to plant the rice.

    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        super.onNeighborChange(world, pos, neighbor);

        // TODO: If neighbor is a RicePaddy, then we need to connect the textures.

    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }
}
