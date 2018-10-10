package growthcraft.core.shared.block;

import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.rice.shared.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Random;

/**
 * BlockPaddyBase
 *
 * The BlockPaddyBase is designed to be mainly used for the BlockPaddy, which is a Rice Paddy for growing rice. There
 * could be other uses for flooding a field of FARMLAND for growing crops.
 *
 * A paddy is designed to be created by activating a FARMLAND block with a Growthcraft Cultivator and then adding a
 * Fluid of choice to the paddy block. Typically we would only add water to a paddy block, but you never know what the
 * future entails (hint towards liquid uranium maybe for hyper growing crops?).
 *
 * Ideally you should be able to place a FluidStack into a BlockPaddy that will in-turn "flood" the neighbor blocks.
 *
 */
public class BlockPaddyBase extends GrowthcraftBlockBase {

    public static final PropertyBool MOISTURE = PropertyBool.create("moisture");
    public static final PropertyBool IS_RADIOACTIVE = PropertyBool.create("is_radioactive");
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");

    private Fluid fluidSource;

    public BlockPaddyBase(Material material) {
        super(material);
        this.setTickRandomly(true);
        this.setDefaultState(
            this.blockState.getBaseState()
                    .withProperty(MOISTURE, false)
                    .withProperty(IS_RADIOACTIVE, false)
                    .withProperty(NORTH, false)
                    .withProperty(EAST, false)
                    .withProperty(SOUTH, false)
                    .withProperty(WEST, false)
        );

    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{ MOISTURE, IS_RADIOACTIVE, NORTH, EAST, SOUTH, WEST });
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(MOISTURE, state.getValue(MOISTURE))
                .withProperty(MOISTURE, state.getValue(IS_RADIOACTIVE))
                .withProperty(NORTH, canConnectPaddyTo(worldIn, pos, EnumFacing.NORTH))
                .withProperty(EAST, canConnectPaddyTo(worldIn, pos, EnumFacing.EAST))
                .withProperty(SOUTH, canConnectPaddyTo(worldIn, pos, EnumFacing.SOUTH))
                .withProperty(WEST, canConnectPaddyTo(worldIn,pos,EnumFacing.WEST));
    }

    public int getMaxPaddyMeta(IBlockAccess world, int x, int y, int z) {
        return 0;
    }

    /**
     * Determines if this block can connect to the given block passed through the parameters.
     *
     * @param world
     * @param pos BlockPos if the block the check.
     * @param facing Facing side of this block to be checked.
     * @return Returns true if the passed block is an instance of BlockPaddyBase
     */
    public boolean canConnectPaddyTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        Block block = world.getBlockState(pos.offset(facing)).getBlock();
        return BlockCheck.isBlockPaddy(block);
    }

    /**
     * Default onBlockActivation.
     *
     * @param worldIn
     * @param pos
     * @param state
     * @param playerIn
     * @param hand
     * @param facing
     * @param hitX
     * @param hitY
     * @param hitZ
     * @return
     */
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
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

    /**
     * Determine if the block above is a crop or not.
     *
     * @param worldIn
     * @param pos
     * @return
     */
    public boolean hasCrops(World worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos.up()).getBlock();
        return block instanceof IPlantable && this.canSustainPlant(worldIn.getBlockState(pos), worldIn, pos, EnumFacing.UP, (IPlantable)block);
    }

    /**
     * On each tick update, we need to determine if we still have a fluid source.
     *
     * @param worldIn
     * @param pos
     * @param state
     * @param rand
     */
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        // Get the some current state values so that we do not have to recalculate some of them.
        boolean isConnectedNorth = (boolean)state.getValue(NORTH);
        boolean isConnectedEast = (boolean)state.getValue(EAST);
        boolean isConnectedSouth = (boolean)state.getValue(SOUTH);
        boolean isConnectedWest = (boolean)state.getValue(WEST);

        if (this.hasRadioactiveSource(worldIn, pos)) {
            worldIn.setBlockState(pos, state.withProperty(MOISTURE, true)
                    .withProperty(IS_RADIOACTIVE, true)
                    .withProperty(NORTH, isConnectedNorth)
                    .withProperty(EAST, isConnectedEast)
                    .withProperty(SOUTH, isConnectedSouth)
                    .withProperty(WEST, isConnectedWest), 3
            );
        } else if ( this.hasFluidSource(worldIn, pos) ) {
            worldIn.setBlockState(pos, state.withProperty(MOISTURE, true)
                    .withProperty(IS_RADIOACTIVE, false)
                    .withProperty(NORTH, isConnectedNorth)
                    .withProperty(EAST, isConnectedEast)
                    .withProperty(SOUTH, isConnectedSouth)
                    .withProperty(WEST, isConnectedWest), 3
            );
        } else {
            worldIn.setBlockState(pos, state.withProperty(MOISTURE, false)
                    .withProperty(IS_RADIOACTIVE, false)
                    .withProperty(NORTH, isConnectedNorth)
                    .withProperty(EAST, isConnectedEast)
                    .withProperty(SOUTH, isConnectedSouth)
                    .withProperty(WEST, isConnectedWest), 3
            );
        }
    }

    /**
     * Determine is this RicePaddy has a valid fluid source near by.
     *
     * @param worldIn
     * @param pos
     * @return
     */
    public boolean hasFluidSource(World worldIn, BlockPos pos) {
        Iterator var3 = BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4), pos.add(4, 1, 4)).iterator();

        BlockPos.MutableBlockPos blockpos$mutableblockpos;
        blockpos$mutableblockpos = (BlockPos.MutableBlockPos)var3.next();

        boolean hasFluid = false;

        while (var3.hasNext()) {
            IBlockState state = worldIn.getBlockState(pos);

            if (FluidRegistry.lookupFluidForBlock(worldIn.getBlockState(blockpos$mutableblockpos).getBlock()) != null ) {
                if (FluidRegistry.lookupFluidForBlock(worldIn.getBlockState(blockpos$mutableblockpos).getBlock()).getName() == "water") {
                    GrowthcraftLogger.getLogger(Reference.MODID).info("Found a water block!");
                    this.fluidSource = FluidRegistry.lookupFluidForBlock(worldIn.getBlockState(blockpos$mutableblockpos).getBlock());
                    hasFluid = true;
                }
            }
            blockpos$mutableblockpos = (BlockPos.MutableBlockPos) var3.next();
        }
        return hasFluid;
    }

    public boolean hasRadioactiveSource(World worldIn, BlockPos pos) {
        Iterator var3 = BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4), pos.add(4, 1, 4)).iterator();

        BlockPos.MutableBlockPos blockpos$mutableblockpos;
        blockpos$mutableblockpos = (BlockPos.MutableBlockPos)var3.next();

        boolean hasRadioactive = false;

        while (var3.hasNext()) {
            IBlockState state = worldIn.getBlockState(pos);

            if (FluidRegistry.lookupFluidForBlock(worldIn.getBlockState(blockpos$mutableblockpos).getBlock()) != null ) {
                if (FluidRegistry.lookupFluidForBlock(worldIn.getBlockState(blockpos$mutableblockpos).getBlock()).getName() == "yellorium") {
                    GrowthcraftLogger.getLogger(Reference.MODID).info("Found a yellorium block!");
                    this.fluidSource = FluidRegistry.lookupFluidForBlock(worldIn.getBlockState(blockpos$mutableblockpos).getBlock());
                    hasRadioactive = true;
                }
            }
            blockpos$mutableblockpos = (BlockPos.MutableBlockPos) var3.next();
        }
        return hasRadioactive;
    }
    /**
     * Determine what is the fluid that this rice paddy is using as a source.
     *
     * @param worldIn World
     * @param pos BlockPos to check for a fluid source.
     * @return Source FluidStack that is hydrating this rice paddy.
     */
    @Nullable
    public FluidStack getFluidSource(World worldIn, BlockPos pos) {
        if ( hasFluidSource(worldIn, pos) ) {
            return new FluidStack(fluidSource, 1000);
        }
        return null;
    }

}
