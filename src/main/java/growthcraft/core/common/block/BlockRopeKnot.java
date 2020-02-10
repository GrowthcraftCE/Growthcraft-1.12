package growthcraft.core.common.block;

import growthcraft.core.shared.Reference;
import growthcraft.core.shared.block.FenceUtils;
import growthcraft.core.shared.block.IBlockRope;
import growthcraft.core.shared.init.GrowthcraftCoreItems;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockRopeKnot extends BlockRopeBase {

    private static final AxisAlignedBB FENCE_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 6, 0.0625 * 0, 0.0625 * 6, 0.0625 * 10, 0.0625 * 16, 0.0625 * 10);
    private static final AxisAlignedBB FENCE_COLLISION_EXTRA_BOX = new AxisAlignedBB(0.0625 * 5, 0.0625 * 0, 0.0625 * 5, 0.0625 * 11, 0.0625 * 24, 0.0625 * 11);

    private static final AxisAlignedBB KNOT_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 5, 0.0625 * 6, 0.0625 * 5, 0.0625 * 11, 0.0625 * 14, 0.0625 * 11);
    private static final AxisAlignedBB NORTH_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 7, 0.0625 * 7, 0.0625 * 0, 0.0625 * 9, 0.0625 * 9, 0.0625 * 5);
    private static final AxisAlignedBB EAST_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 11, 0.0625 * 7, 0.0625 * 7, 0.0625 * 16, 0.0625 * 9, 0.0625 * 9);
    private static final AxisAlignedBB SOUTH_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 7, 0.0625 * 7, 0.0625 * 11, 0.0625 * 9, 0.0625 * 9, 0.0625 * 16);
    private static final AxisAlignedBB WEST_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 0, 0.0625 * 7, 0.0625 * 7, 0.0625 * 5, 0.0625 * 9, 0.0625 * 9);

    private static final AxisAlignedBB FENCE_NORTH_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 7, 0.0625 * 6, 0.0625 * 0, 0.0625 * 9, 0.0625 * 15, 0.0625 * 5);
    private static final AxisAlignedBB FENCE_EAST_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 11, 0.0625 * 6, 0.0625 * 7, 0.0625 * 16, 0.0625 * 15, 0.0625 * 9);
    private static final AxisAlignedBB FENCE_SOUTH_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 7, 0.0625 * 6, 0.0625 * 11, 0.0625 * 9, 0.0625 * 15, 0.0625 * 16);
    private static final AxisAlignedBB FENCE_WEST_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 0, 0.0625 * 6, 0.0625 * 7, 0.0625 * 5, 0.0625 * 15, 0.0625 * 9);

    public static final PropertyInteger NORTH = PropertyInteger.create("north", 0, 2);
    public static final PropertyInteger EAST = PropertyInteger.create("east", 0, 2);
    public static final PropertyInteger SOUTH = PropertyInteger.create("south", 0, 2);
    public static final PropertyInteger WEST = PropertyInteger.create("west", 0, 2);
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");

    private Block baseFence;

    /**
     * Construct a default RopeKnot on an oak fence.
     *
     * @param unlocalizedName The unlocalized name for the rope knot fence.
     */
    public BlockRopeKnot(String unlocalizedName) {
        this(unlocalizedName, Blocks.OAK_FENCE);
    }

    /**
     * Construct a dynamic RopeKnot on a given fence block.
     *
     * @param unlocalizedName The unlocalized name for the rope knot fence.
     * @param baseFence       The block of the given fence type that this rope knot will attach to.
     */
    public BlockRopeKnot(String unlocalizedName, Block baseFence) {
        this(unlocalizedName, baseFence, Reference.MODID);
    }

    public BlockRopeKnot(String unlocalizedName, Block baseFence, String modid) {
        super(Material.WOOD);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(modid, unlocalizedName));

        this.setHardness(3);
        this.setResistance(20);
        this.setSoundType(SoundType.WOOD);
        this.setHarvestLevel("axe", 0);

        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(NORTH, Integer.valueOf(0))
                .withProperty(EAST, Integer.valueOf(0))
                .withProperty(SOUTH, Integer.valueOf(0))
                .withProperty(WEST, Integer.valueOf(0))
                .withProperty(UP, Boolean.valueOf(false))
                .withProperty(DOWN, Boolean.valueOf(false)));

        this.useNeighborBrightness = true;

        this.baseFence = baseFence;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public Block getFenceBlock() {
        return baseFence;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, FENCE_COLLISION_EXTRA_BOX);
        super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
    }


    @Override
    protected void populateCollisionBoxes(IBlockState actualState, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, FENCE_BOUNDING_BOX);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, KNOT_BOUNDING_BOX);

        int vN = actualState.getValue(NORTH);
        int vE = actualState.getValue(EAST);
        int vS = actualState.getValue(SOUTH);
        int vW = actualState.getValue(WEST);

        if (vN != 0)
            addCollisionBoxToList(pos, entityBox, collidingBoxes, (vN == 1) ? NORTH_BOUNDING_BOX : FENCE_NORTH_BOUNDING_BOX);

        if (vE != 0)
            addCollisionBoxToList(pos, entityBox, collidingBoxes, (vE == 1) ? EAST_BOUNDING_BOX : FENCE_EAST_BOUNDING_BOX);

        if (vS != 0)
            addCollisionBoxToList(pos, entityBox, collidingBoxes, (vS == 1) ? SOUTH_BOUNDING_BOX : FENCE_SOUTH_BOUNDING_BOX);

        if (vW != 0)
            addCollisionBoxToList(pos, entityBox, collidingBoxes, (vW == 1) ? WEST_BOUNDING_BOX : FENCE_WEST_BOUNDING_BOX);

        // Up and down not necessary, because they are covered by FENCE_BOUNDING_BOX
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (playerIn.isSneaking()) {
            // Put the Fence back.
            worldIn.setBlockState(pos, Blocks.OAK_FENCE.getDefaultState());
        } else {
            /* Do nothing special, we might need to do something special later. */
        }
        return false;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        // Always return a rope when broken
        ItemStack rope = GrowthcraftCoreItems.rope.asStack(1);
        InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), rope);
        InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(baseFence));
    }

    @Override
    public int quantityDropped(Random random) {
        // Always return 0 as this is not a normal block.
        return 0;
    }

    /**
     * Determines if this RopeKnot block can connect to a neighbor block on the given face.
     *
     * @param world  world
     * @param pos    RopeKnot Position
     * @param facing Requesting Side
     * @return
     */
    @Override
    public boolean canConnectRopeTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        Block block = world.getBlockState(pos.offset(facing)).getBlock();
        return block instanceof IBlockRope;
    }

    @Override
    public boolean canRopeBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        Block block = world.getBlockState(pos.offset(facing)).getBlock();
        return block instanceof BlockRopeFence || block instanceof BlockRopeKnot;
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        int vN = getConnectionValue(worldIn, pos, EnumFacing.NORTH);
        int vE = getConnectionValue(worldIn, pos, EnumFacing.EAST);
        int vS = getConnectionValue(worldIn, pos, EnumFacing.SOUTH);
        int vW = getConnectionValue(worldIn, pos, EnumFacing.WEST);

        return state.withProperty(NORTH, vN)
                .withProperty(EAST, vE)
                .withProperty(SOUTH, vS)
                .withProperty(WEST, vW)
                .withProperty(UP, canConnectRopeTo(worldIn, pos, EnumFacing.UP))
                .withProperty(DOWN, canConnectRopeTo(worldIn, pos, EnumFacing.DOWN));
    }

    private int getConnectionValue(IBlockAccess worldIn, BlockPos pos, EnumFacing facing) {
        if (canConnectRopeTo(worldIn, pos, facing))
            return 1;
        else if (FenceUtils.canFenceConnectTo(worldIn, pos, facing))
            return 2;
        else
            return 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NORTH, EAST, SOUTH, WEST, UP, DOWN);
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

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return face != EnumFacing.UP && face != EnumFacing.DOWN ? BlockFaceShape.MIDDLE_POLE : BlockFaceShape.CENTER;
    }

    /**
     * Determines if an entity can path through this block
     */
    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return false;
    }

}
