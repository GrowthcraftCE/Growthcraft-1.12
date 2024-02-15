package growthcraft.grapes.common.blocks;

import growthcraft.core.shared.block.BlockCheck;
import growthcraft.core.shared.block.BlockFlags;
import growthcraft.core.shared.block.IBlockRope;
import growthcraft.core.shared.init.GrowthcraftCoreBlocks;
import growthcraft.core.shared.init.GrowthcraftCoreItems;
import growthcraft.grapes.common.utils.GrapeBlockCheck;
import growthcraft.grapes.common.utils.GrapeTypeUtils;
import growthcraft.grapes.shared.config.GrowthcraftGrapesConfig;
import growthcraft.grapes.shared.definition.IGrapeType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockGrapeLeaves extends BlockBush implements IGrowable, IBlockRope {

    // region Properties
    //=================================================================================================================
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625 * 0, 0.0625 * 0, 0.0625 * 0, 0.0625 * 16, 0.0625 * 16, 0.0625 * 16);

    private final int grapeLeavesGrowthRate = GrowthcraftGrapesConfig.grapeLeavesGrowthRate;
    private final int grapeSpawnRate = GrowthcraftGrapesConfig.grapeSpawnRate;
    // how far can a grape leaf grow before it requires support from a trunk
    private final int grapeVineSupportedLength = GrowthcraftGrapesConfig.grapeVineSupportedLength;

    public static final PropertyInteger SUBTYPE = BlockGrapeVineBase.SUBTYPE;
    public static final PropertyBool OPAQUEBELOW = PropertyBool.create("opaquebelow");
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");

    private final BlockGrapeFruit blockFruit;
    private final IGrapeType[] grapeTypes;

    // endregion

    public BlockGrapeLeaves(IGrapeType[] grapeTypes, BlockGrapeFruit blockFruit) {
        super();
        setDefaultState(this.getBlockState().getBaseState().withProperty(SUBTYPE, 0));
        setTickRandomly(true);
        setHardness(0.2F);
        setLightOpacity(1);
        setSoundType(SoundType.PLANT);

        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(OPAQUEBELOW, Boolean.valueOf(true))
                .withProperty(NORTH, Boolean.valueOf(false))
                .withProperty(EAST, Boolean.valueOf(false))
                .withProperty(SOUTH, Boolean.valueOf(false))
                .withProperty(WEST, Boolean.valueOf(false))
                .withProperty(UP, Boolean.valueOf(false))
                .withProperty(DOWN, Boolean.valueOf(false)));

        this.blockFruit = blockFruit;
        this.grapeTypes = grapeTypes;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, getBoundingBox(state, worldIn, pos));
    }

    /**
     * Use this method to check if the block can grow outwards on a rope
     *
     * @param world - the world
     * @param pos   - block pos offset to check
     * @return true if the block can grow here, false otherwise
     */
    public boolean canGrowOutwardsOnRope(World world, BlockPos pos) {
        if (BlockCheck.isRope(world.getBlockState(pos.add(1, 0, 0)).getBlock())) return true;
        if (BlockCheck.isRope(world.getBlockState(pos.add(-1, 0, 0)).getBlock())) return true;
        if (BlockCheck.isRope(world.getBlockState(pos.add(0, 0, 1)).getBlock())) return true;
        if (BlockCheck.isRope(world.getBlockState(pos.add(0, 0, -1)).getBlock())) return true;
        return false;
    }

    /**
     * Check the surrounding blocks to identify if the given block at pos is connected to a vine trunk.
     *
     * @param world - the world
     * @param pos   - block pos to base the check from
     * @return true if the given block at pos can grow outwards
     */
    public boolean canGrowOutwards(World world, BlockPos pos) {

        final boolean leavesToTheSouth = world.getBlockState(pos.add(0, 0, 1)).getBlock() == this;
        final boolean leavesToTheNorth = world.getBlockState(pos.add(0, 0, -1)).getBlock() == this;
        final boolean leavesToTheEast = world.getBlockState(pos.add(1, 0, 0)).getBlock() == this;
        final boolean leavesToTheWest = world.getBlockState(pos.add(-1, 0, 0)).getBlock() == this;

        /*
         * If the neighbor block is not a grape leaves block, then the given block at pos cannot grow outward.
         */
        if (!leavesToTheSouth && !leavesToTheNorth && !leavesToTheEast && !leavesToTheWest) return false;

        return this.isSupported(world, pos);
    }

    /**
     * Variation of canGrowOutwards, use this method to check rope blocks
     *
     * @param world - the world
     * @param pos   - blockpos to check
     * @return true if the block can grow here, false otherwise
     */
    public boolean canGrowHere(World world, BlockPos pos) {
        if (BlockCheck.isRope(world.getBlockState(pos).getBlock())) {
            return canGrowOutwards(world, pos);
        }
        return false;
    }

    private void setGrapeBlock(World world, BlockPos pos, IBlockState state) {
        int type = state.getValue(SUBTYPE);
        world.setBlockState(pos, blockFruit.getDefaultState().withProperty(SUBTYPE, type), BlockFlags.UPDATE_AND_SYNC);
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        BlockPos posDown = pos.down();
        if (worldIn.isAirBlock(posDown) && (rand.nextInt(this.grapeSpawnRate) == 0)) {
            setGrapeBlock(worldIn, posDown, state);
        }

        if (worldIn.rand.nextInt(this.grapeLeavesGrowthRate) == 0) {
            if (canGrowOutwardsOnRope(worldIn, pos)) {
                final EnumFacing dir = BlockCheck.DIR4[rand.nextInt(4)];

                BlockPos posDir = pos.add(dir.getXOffset(), 0, dir.getZOffset());
                if (canGrowHere(worldIn, posDir)) {
                    int type = state.getValue(SUBTYPE);
                    worldIn.setBlockState(posDir, getDefaultState().withProperty(SUBTYPE, type), BlockFlags.UPDATE_AND_SYNC);

                    BlockPos below = posDir.down();
                    IBlockState stateBelow = worldIn.getBlockState(below);
                    if ((stateBelow.getBlock() instanceof BlockGrapeVine1)) {
                        BlockGrapeVine1 blockBelow = (BlockGrapeVine1) stateBelow.getBlock();
                        if (stateBelow.getValue(BlockGrapeVineBase.AGE) < blockBelow.getMaxAge()) {
                            worldIn.setBlockState(below, stateBelow.withProperty(BlockGrapeVineBase.AGE, blockBelow.getMaxAge()), BlockFlags.UPDATE_AND_SYNC);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        BlockPos posDown = pos.down();
        if (worldIn.isAirBlock(posDown))
            return true;

        for (int i = 0; i < 4; i++) {
            final EnumFacing dir = BlockCheck.DIR4[i];
            BlockPos posDir = pos.add(dir.getXOffset(), 0, dir.getZOffset());

            if (canGrowHere(worldIn, posDir)) {
                return true;
            }
        }

        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @SuppressWarnings("deprecation")
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        IBlockState other = blockAccess.getBlockState(pos.offset(side));
        Block block = other.getBlock();
        return block != this || blockState.getValue(SUBTYPE) != other.getValue(SUBTYPE);
    }

    /************
     * TICK
     ************/
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (worldIn.isRemote)
            return;
        if (!this.isSupported(worldIn, pos, state)) {
            worldIn.setBlockState(pos, GrowthcraftCoreBlocks.rope_fence.getDefaultState());
            List<ItemStack> drops = super.getDrops(worldIn, pos, state, 0);
            for (ItemStack drop : drops) {
                spawnAsEntity(worldIn, pos, drop);
            }
        } else {
            // NOTE: Same as in BlockReed.updateTick(World, BlockPos, IBlockState, Random)

            if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
                grow(worldIn, rand, pos, state);
                ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
            }
        }
    }

    // region Conditions

    //=================================================================================================================

    /**
     * Can this block sustain a BlockBush
     *
     * @param state
     * @return always return true
     */
    @Override
    protected boolean canSustainBush(IBlockState state) {
        return true;
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return false;
    }

    /**
     * Is the given block supported by a grape vine trunk
     *
     * @param world - the world
     * @param pos   - the position to base the check on
     * @return true if block is supported by a vine trunk
     */
    public boolean isSupported(World world, BlockPos pos) {
        // Fast check, is there a trunk below this pos? Otherwise we have to go find one.
        if (this.isSupportedByTrunk(world, pos)) {
            return true;
        } else {
            for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(
                    pos.add(-(grapeVineSupportedLength), 0, -(grapeVineSupportedLength)),
                    pos.add(grapeVineSupportedLength, 0, grapeVineSupportedLength)
            )) {
                if (isSupportedByTrunk(world, blockpos$mutableblockpos)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Is the given block supported by a grape vine trunk
     *
     * @param world - the world
     * @param pos   - the position to base the check on
     * @param state - block state, not used at this time
     * @return true if block is supported by a vine trunk
     */
    public boolean isSupported(World world, BlockPos pos, IBlockState state) {
        return isSupported(world, pos);
    }

    public boolean isSupportedByTrunk(World world, BlockPos pos) {
        return isTrunk(world, pos.down());
    }

    private boolean isTrunk(World world, BlockPos pos) {
        return GrapeBlockCheck.isGrapeVineTrunk(world.getBlockState(pos).getBlock());
    }


    @Override
    public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public boolean canConnectRopeTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        Block block = world.getBlockState(pos.offset(facing)).getBlock();
        return block instanceof IBlockRope;
    }

    @Override
    public boolean canRopeBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        // TODO: Check if this method is correct! Remove explicit dependencies of BlockRopeFence and BlockRopeKnot!
        Block block = world.getBlockState(pos.offset(facing)).getBlock();
        return block == GrowthcraftCoreBlocks.rope_fence.getBlock() || block == GrowthcraftCoreBlocks.rope_knot.getBlock();
    }
    // endregion

    // region Extras
    //=================================================================================================================

    @SuppressWarnings("deprecation")
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    // endregion

    // region Drops
    //=================================================================================================================
    @SuppressWarnings("deprecation")
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        List<ItemStack> drops = super.getDrops(world, pos, state, fortune);
        drops.add(GrowthcraftCoreItems.rope.asStack(1));
        return drops;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        int typeID = state.getValue(SUBTYPE);
        IGrapeType type = GrapeTypeUtils.getTypeBySubID(grapeTypes, typeID);
        return type.asSeedsStack().getItem();
    }

    @Override
    public int damageDropped(IBlockState state) {
        int typeID = state.getValue(SUBTYPE);
        IGrapeType type = GrapeTypeUtils.getTypeBySubID(grapeTypes, typeID);
        return type.asSeedsStack().getItemDamage();
    }

    @Override
    public int quantityDropped(Random random) {
        return random.nextInt(3) == 0 ? 1 : 0;
    }

    // endregion

    /************
     * RENDERS
     ************/

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return Blocks.LEAVES.isOpaqueCube(state);
    }

    /************
     * STATES
     ************/

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, SUBTYPE, NORTH, EAST, SOUTH, WEST, UP, DOWN, OPAQUEBELOW);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(SUBTYPE, meta & 0x7);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = 0;
        meta |= state.getValue(SUBTYPE) & 0x7;
        return meta;
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        IBlockState stateBelow = worldIn.getBlockState(pos.down());

        return state
                .withProperty(OPAQUEBELOW, stateBelow.isOpaqueCube())
                .withProperty(NORTH, canConnectRopeTo(worldIn, pos, EnumFacing.NORTH))
                .withProperty(EAST, canConnectRopeTo(worldIn, pos, EnumFacing.EAST))
                .withProperty(SOUTH, canConnectRopeTo(worldIn, pos, EnumFacing.SOUTH))
                .withProperty(WEST, canConnectRopeTo(worldIn, pos, EnumFacing.WEST))
                .withProperty(UP, canConnectRopeTo(worldIn, pos, EnumFacing.UP))
                .withProperty(DOWN, canConnectRopeTo(worldIn, pos, EnumFacing.DOWN));
    }
}
