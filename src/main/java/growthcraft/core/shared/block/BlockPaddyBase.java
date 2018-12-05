package growthcraft.core.shared.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;

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

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) { return false; }

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

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(MOISTURE, hasFluidSource(worldIn, pos))
                .withProperty(IS_RADIOACTIVE, hasRadioactiveSource(worldIn, pos))
                .withProperty(NORTH, canConnectPaddyTo(worldIn, pos, EnumFacing.NORTH))
                .withProperty(EAST, canConnectPaddyTo(worldIn, pos, EnumFacing.EAST))
                .withProperty(SOUTH, canConnectPaddyTo(worldIn, pos, EnumFacing.SOUTH))
                .withProperty(WEST, canConnectPaddyTo(worldIn,pos,EnumFacing.WEST));
    }

    public boolean canConnectPaddyTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        Block block = world.getBlockState(pos.offset(facing)).getBlock();
        return BlockCheck.isBlockPaddy(block);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

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

    public boolean hasFluidSource(IBlockAccess worldIn, BlockPos pos) {
        int maxFieldRange = 4;
        Iterator var3 = BlockPos.getAllInBoxMutable(pos.add(-(maxFieldRange), 0, -(maxFieldRange)), pos.add(maxFieldRange, 1, maxFieldRange)).iterator();

        BlockPos.MutableBlockPos blockpos$mutableblockpos;
        blockpos$mutableblockpos = (BlockPos.MutableBlockPos)var3.next();

        while (var3.hasNext()) {
            if (FluidRegistry.lookupFluidForBlock(worldIn.getBlockState(blockpos$mutableblockpos).getBlock()) != null
                    && FluidRegistry.lookupFluidForBlock(worldIn.getBlockState(blockpos$mutableblockpos).getBlock()).getName() == "water")
                return true;
            blockpos$mutableblockpos = (BlockPos.MutableBlockPos) var3.next();
        }
        return false;
    }

    public boolean hasRadioactiveSource(IBlockAccess worldIn, BlockPos pos) {
        // TODO: Check for config, if null then return false, else convert to List<String>

        int maxFieldRange = 4;
        Iterator var3 = BlockPos.getAllInBoxMutable(pos.add(-(maxFieldRange), 0, -(maxFieldRange)), pos.add(maxFieldRange, 1, maxFieldRange)).iterator();

        BlockPos.MutableBlockPos blockpos$mutableblockpos;
        blockpos$mutableblockpos = (BlockPos.MutableBlockPos)var3.next();

        while (var3.hasNext()) {
            // TODO: Implement CONFIG for radioactive fluid names.
            if (FluidRegistry.lookupFluidForBlock(worldIn.getBlockState(blockpos$mutableblockpos).getBlock()) != null
                && FluidRegistry.lookupFluidForBlock(worldIn.getBlockState(blockpos$mutableblockpos).getBlock()).getName() == "yellorium")
                return true;
            blockpos$mutableblockpos = (BlockPos.MutableBlockPos) var3.next();
        }

        return false;
    }

    public boolean isRadioactive(World worldIn, BlockPos pos) {
        return hasRadioactiveSource((IBlockAccess)worldIn, pos);
    }

    public boolean isMoisture(World worldIn, BlockPos pos) {
        return hasFluidSource((IBlockAccess)worldIn, pos);
    }
}
