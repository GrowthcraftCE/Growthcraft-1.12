package growthcraft.core.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import growthcraft.core.shared.Reference;
import growthcraft.core.shared.block.IBlockRope;
import growthcraft.core.shared.init.GrowthcraftCoreItems;
import growthcraft.core.common.tileentity.TileEntityRopeKnot;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockRopeKnot extends Block implements ITileEntityProvider, IBlockRope {

	private static final AxisAlignedBB FENCE_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 6, 0.0625 * 0, 0.0625 * 6, 0.0625 * 10, 0.0625 * 16, 0.0625 * 10);
	
    private static final AxisAlignedBB KNOT_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 5, 0.0625 * 6, 0.0625 * 5, 0.0625 * 11, 0.0625 * 14, 0.0625 * 11);
    private static final AxisAlignedBB NORTH_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 5, 0.0625 * 6, 0.0625 * 0, 0.0625 * 11, 0.0625 * 14, 0.0625 * 5);
    private static final AxisAlignedBB EAST_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 11, 0.0625 * 6, 0.0625 * 5, 0.0625 * 16, 0.0625 * 14, 0.0625 * 11);
    private static final AxisAlignedBB SOUTH_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 5, 0.0625 * 6, 0.0625 * 11, 0.0625 * 11, 0.0625 * 14, 0.0625 * 16);
    private static final AxisAlignedBB WEST_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 0, 0.0625 * 6, 0.0625 * 5, 0.0625 * 5, 0.0625 * 14, 0.0625 * 11);

    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");

    private boolean wasActivated;

    public BlockRopeKnot(String unlocalizedName) {
        super(Material.WOOD);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));

        this.setHardness(3);
        this.setResistance(20);
        this.setSoundType(SoundType.CLOTH);
        this.setHarvestLevel("axe", 0);

        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(NORTH, Boolean.valueOf(false))
                .withProperty(EAST, Boolean.valueOf(false))
                .withProperty(SOUTH, Boolean.valueOf(false))
                .withProperty(WEST, Boolean.valueOf(false))
                .withProperty(UP, Boolean.valueOf(false))
                .withProperty(DOWN, Boolean.valueOf(false)));

        this.useNeighborBrightness = true;
        this.wasActivated = false;
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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	ArrayList<AxisAlignedBB> collidingBoxes = new ArrayList<>(5);
    	
    	state = state.getActualState(source, pos);
    	populateCollisionBoxes(state, pos, FULL_BLOCK_AABB.offset(pos), collidingBoxes);
    	if( collidingBoxes.isEmpty() )
    		return FULL_BLOCK_AABB;
    	
    	AxisAlignedBB box = collidingBoxes.get(0);	// NOTE: Assuming that collidingBoxes is non empty!
    	for( int i = 1; i < collidingBoxes.size(); i ++ )
    		box = box.union(collidingBoxes.get(i));
    	box = box.offset(new BlockPos(-pos.getX(), -pos.getY(), -pos.getZ()));
    	
    	return box;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        if( !isActualState )
        	state = state.getActualState(worldIn, pos);
        
        populateCollisionBoxes(state, pos, entityBox, collidingBoxes);
    }
    
    private void populateCollisionBoxes(IBlockState actualState, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes) {
    	addCollisionBoxToList(pos, entityBox, collidingBoxes, FENCE_BOUNDING_BOX);
    	addCollisionBoxToList(pos, entityBox, collidingBoxes, KNOT_BOUNDING_BOX);
    	
    	if( actualState.getValue(NORTH) )
    		addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_BOUNDING_BOX);
    	if( actualState.getValue(EAST) )
    		addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_BOUNDING_BOX);
    	if( actualState.getValue(SOUTH) )
    		addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_BOUNDING_BOX);
    	if( actualState.getValue(WEST) )
    		addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_BOUNDING_BOX);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(playerIn.isSneaking()) {
            this.wasActivated = true;
            // Put the Fence back.
            IItemHandler inventoryHandler = this.getInventoryHandler(worldIn, pos);
            ItemStack stack = inventoryHandler.getStackInSlot(0);
            IBlockState fenceBlockState = Block.getBlockFromItem(stack.getItem()).getDefaultState();
            worldIn.setBlockState(pos, fenceBlockState);
        } else {
            this.wasActivated = false;
        }
        return false;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        // Always return a rope when broken
        ItemStack rope = GrowthcraftCoreItems.rope.asStack(1);
        InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), rope);

        if(!this.wasActivated) {
            // Returned the stored ItemFence
            IItemHandler inventoryHandler = this.getInventoryHandler(worldIn, pos);
            ItemStack stack = inventoryHandler.getStackInSlot(0);
            InventoryHelper.spawnItemStack(worldIn, pos.getX() + 0.5F, pos.getY() + 1, pos.getZ() + 0.5F, stack);
        }

        this.wasActivated = false;
    }

    @Override
    public int quantityDropped(Random random) {
        // Always return 0 as this is not a normal block.
        return 0;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityRopeKnot();
    }

    private IItemHandler getInventoryHandler(World worldIn, BlockPos pos) {
        TileEntityRopeKnot te = (TileEntityRopeKnot) worldIn.getTileEntity(pos);
        IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        return handler;
    }

    /**
     * Determines if this RopeKnot block can connect to a neighbor block on the given face.
     * @param world world
     * @param pos RopeKnot Position
     * @param facing Requesting Side
     * @return
     */
    @Override
    public boolean canConnectRopeTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        Block block = world.getBlockState(pos.offset(facing)).getBlock();
        // return block instanceof BlockRopeFence || block instanceof BlockRopeKnot || block instanceof BlockGrapeVineBush || block instanceof BlockHopsBush;
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
