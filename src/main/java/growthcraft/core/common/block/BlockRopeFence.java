package growthcraft.core.common.block;

import java.util.List;
import java.util.Random;

import growthcraft.core.shared.Reference;
import growthcraft.core.shared.block.IBlockRope;
import growthcraft.core.shared.init.GrowthcraftCoreItems;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRopeFence extends BlockRopeBase {

    private static final AxisAlignedBB KNOT_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 7, 0.0625 * 7, 0.0625 * 7, 0.0625 * 9, 0.0625 * 9, 0.0625 * 9);
    private static final AxisAlignedBB NORTH_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 7, 0.0625 * 7, 0.0625 * 0, 0.0625 * 9, 0.0625 * 9, 0.0625 * 7);
    private static final AxisAlignedBB EAST_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 9, 0.0625 * 7, 0.0625 * 7, 0.0625 * 16, 0.0625 * 9, 0.0625 * 9);
    private static final AxisAlignedBB SOUTH_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 7, 0.0625 * 7, 0.0625 * 9, 0.0625 * 9, 0.0625 * 9, 0.0625 * 16);
    private static final AxisAlignedBB WEST_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 0, 0.0625 * 7, 0.0625 * 7, 0.0625 * 7, 0.0625 * 9, 0.0625 * 9);
    private static final AxisAlignedBB UP_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 7, 0.0625 * 9, 0.0625 * 7, 0.0625 * 9, 0.0625 * 16, 0.0625 * 9);
    private static final AxisAlignedBB DOWN_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 7, 0.0625 * 0, 0.0625 * 7, 0.0625 * 9, 0.0625 * 7, 0.0625 * 9);
    
    public BlockRopeFence(String unlocalizedName) {
        super(Material.CARPET);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setHardness(0.5F);
        this.setSoundType(SoundType.CLOTH);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(NORTH, Boolean.valueOf(false))
                .withProperty(EAST, Boolean.valueOf(false))
                .withProperty(SOUTH, Boolean.valueOf(false))
                .withProperty(WEST, Boolean.valueOf(false))
                .withProperty(UP, Boolean.valueOf(false))
                .withProperty(DOWN, Boolean.valueOf(false)));
    }

    @Override
    protected void populateCollisionBoxes(IBlockState actualState, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes) {
    	addCollisionBoxToList(pos, entityBox, collidingBoxes, KNOT_BOUNDING_BOX);
    	
    	if( actualState.getValue(NORTH) )
    		addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_BOUNDING_BOX);
    	if( actualState.getValue(EAST) )
    		addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_BOUNDING_BOX);
    	if( actualState.getValue(SOUTH) )
    		addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_BOUNDING_BOX);
    	if( actualState.getValue(WEST) )
    		addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_BOUNDING_BOX);
    	if( actualState.getValue(UP) )
    		addCollisionBoxToList(pos, entityBox, collidingBoxes, UP_BOUNDING_BOX);
    	if( actualState.getValue(DOWN) )
    		addCollisionBoxToList(pos, entityBox, collidingBoxes, DOWN_BOUNDING_BOX);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        // Return nothing as we only want to drop a Rope if it is broken by a player.
    }

    @Override
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
        // Always return a rope when broken
        if ( !worldIn.isRemote) {
            ItemStack rope = GrowthcraftCoreItems.rope.asStack(1);
            InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), rope);
        }
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
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
    
	@SuppressWarnings("deprecation")
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
        return super.canPlaceBlockOnSide(worldIn, pos, side);
    }

    @Override
    public boolean canConnectRopeTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        Block block = world.getBlockState(pos.offset(facing)).getBlock();
//        return block instanceof BlockRopeFence || block instanceof BlockRopeKnot || block instanceof BlockGrapeVineBush || BlockHopsBush.class.isInstance(block);
        return block instanceof IBlockRope;
        
    }

    @Override
    public boolean canRopeBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        Block block = world.getBlockState(pos).getBlock();
        return BlockRopeFence.class.isInstance(block);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(NORTH, canConnectRopeTo(worldIn, pos, EnumFacing.NORTH))
                .withProperty(EAST, canConnectRopeTo(worldIn, pos, EnumFacing.EAST))
                .withProperty(SOUTH, canConnectRopeTo(worldIn, pos, EnumFacing.SOUTH))
                .withProperty(WEST, canConnectRopeTo(worldIn, pos, EnumFacing.WEST))
                .withProperty(UP, canConnectRopeTo(worldIn, pos, EnumFacing.UP))
                .withProperty(DOWN, canConnectRopeTo(worldIn, pos, EnumFacing.DOWN));
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
}
