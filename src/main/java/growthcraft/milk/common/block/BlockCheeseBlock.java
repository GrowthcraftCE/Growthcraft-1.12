package growthcraft.milk.common.block;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.api.utils.BlockFlags;
import growthcraft.core.common.block.GrowthcraftBlockContainer;
import growthcraft.core.utils.BlockUtils;
import growthcraft.core.utils.ItemUtils;
import growthcraft.milk.common.tileentity.TileEntityCheeseBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCheeseBlock extends GrowthcraftBlockContainer {
    
	public final static int MAX_VARIANTS = 10;
	
	public final static PropertyEnum<Orient> TYPE_ORIENT = PropertyEnum.create("orient", Orient.class);
	public final static PropertyInteger TYPE_SLICES_COUNT = PropertyInteger.create("slicescount", 1, 4);
	public final static PropertyInteger TYPE_CHEESE_VARIANT = PropertyInteger.create("type", 0, MAX_VARIANTS-1);

	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(
            0.0625 * 0, 0.0625 * 0, 0.0625 * 0,
            0.0625 * 16, 0.0625 * 8, 0.0625 * 16);
	
	public BlockCheeseBlock() {
		super(Material.CAKE);
		this.setHardness(0.5F);
		this.setSoundType(SoundType.CLOTH);
		this.setDefaultState(this.getBlockState().getBaseState().withProperty(TYPE_SLICES_COUNT, 1));
		setTileEntityType(TileEntityCheeseBlock.class);
	}
	
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

	@Override
	protected boolean shouldRestoreBlockState(World world, BlockPos pos, ItemStack stack)
	{
		return true;
	}

	@Override
	protected boolean shouldDropTileStack(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		return false;
	}
	
	@Override
	public boolean isRotatable(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return true;
	}
	
	protected void setDefaultDirection(World world, BlockPos pos, IBlockState state)
	{
		if (!world.isRemote)
		{
			Orient facing = Orient.fromFacing(BlockUtils.getDefaultDirection(world, pos, state));
			world.setBlockState(pos, state.withProperty(TYPE_ORIENT, facing), BlockFlags.UPDATE_AND_SYNC);
		}
	}
	
	protected Orient setOrientWhenPlacing(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, boolean allDirections) {
		Orient facing = Orient.fromFacing(EnumFacing.fromAngle(placer.rotationYaw));
		worldIn.setBlockState(pos, state.withProperty(TYPE_ORIENT, facing), BlockFlags.UPDATE_AND_SYNC);
		return facing;
	}
	
	protected void setPropertiesFromTileEntity(World worldIn, BlockPos pos, IBlockState state) {
		final TileEntityCheeseBlock te = getTileEntity(worldIn, pos);
		if (te != null)
		{
			int numSlices = te.getCheese().getSlices();
			if( numSlices <= 0 )
				numSlices = 1;	// To avoid a crash
			worldIn.setBlockState(pos, state.withProperty(TYPE_SLICES_COUNT, numSlices), BlockFlags.UPDATE_AND_SYNC);
		}
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		super.onBlockAdded(worldIn, pos, state);
		setDefaultDirection(worldIn, pos, state);
		setPropertiesFromTileEntity(worldIn, pos, state);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		setOrientWhenPlacing(worldIn, pos, state, placer, true);
		setPropertiesFromTileEntity(worldIn, pos, state);
	}

	@Override
	protected ItemStack createHarvestedBlockItemStack(World world, EntityPlayer player, BlockPos pos, IBlockState state)
	{
		final TileEntityCheeseBlock te = getTileEntity(world, pos);
		if (te != null)
		{
			return te.asItemStack();
		}
		
		// TODO: Check if correct
		return new ItemStack(this, 1, damageDropped(state)/*state.getBlock().getMetaFromState(state)*/);
	}

	@Override
	protected void getTileItemStackDrops(List<ItemStack> ret, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		final TileEntityCheeseBlock te = getTileEntity(world, pos);
		if (te != null)
		{
			ret.add(te.asItemStack());
		}
		else
		{
			super.getTileItemStackDrops(ret, world, pos, state, fortune);
		}
	}
	
	@Override
	protected boolean shouldScatterInventoryOnBreak(World world, BlockPos pos)
	{
		return true;
	}
	
	@Override
	protected void scatterInventory(World world, BlockPos pos, Block block)
	{
		final TileEntityCheeseBlock te = getTileEntity(world, pos);
		if (te != null)
		{
			final List<ItemStack> drops = new ArrayList<ItemStack>();
			te.populateDrops(drops);
			for (ItemStack stack : drops)
			{
				ItemUtils.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack, rand);
			}
		}
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		final TileEntityCheeseBlock teCheeseBlock = getTileEntity(world, pos);
		if (teCheeseBlock != null)
		{
			return teCheeseBlock.asItemStack();
		}
		return super.getPickBlock(state, target, world, pos, player);
	}
	
	// 

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		final ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		final TileEntityCheeseBlock te = getTileEntity(world, pos);
		if (te != null)
		{
			te.populateDrops(ret);
		}
		return ret;
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		// TODO: Will create always initial stage block. Maybe find a better approach.
		return state.getBlock().getMetaFromState(state);
	}
	
	
	
	/************
	 * PROPERTIES
	 ************/	
	
	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
//		IProperty<?>[] listedProps = new IProperty<?>[] {TYPE_ORIENT, TYPE_SLICES_COUNT};
//		IProperty<?>[] unlistedProps = new IProperty<?>[] {TYPE_CHEESE_VARIANT};
	    return new BlockStateContainer(this, TYPE_ORIENT, TYPE_SLICES_COUNT, TYPE_CHEESE_VARIANT);
	}

	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta) {
	    return this.getDefaultState()
	    		   .withProperty(TYPE_ORIENT, Orient.values()[meta & 0x2])
	    		   .withProperty(TYPE_SLICES_COUNT, ((meta >> 2) & 0x2) + 1);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
	    return state.getValue(TYPE_ORIENT).ordinal() |
	    	   ((state.getValue(TYPE_SLICES_COUNT)-1) << 2);
	}

	@Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
		int variantID = 0;
		final TileEntityCheeseBlock teCheeseBlock = getTileEntity(worldIn, pos);
		if (teCheeseBlock != null)
		{
			variantID = teCheeseBlock.getCheese().getType().getVariantID();
		}
		
        return state.withProperty(TYPE_CHEESE_VARIANT, variantID);
    }
	
	public static enum Orient implements IStringSerializable {
		NORTH,
		SOUTH,
		WEST,
		EAST;
		
		public static Orient fromFacing(EnumFacing facing) {
			switch( facing ) {
			case NORTH:
				return NORTH;
			case SOUTH:
				return SOUTH;
			case WEST:
				return WEST;
			default:
			case EAST:
				return EAST;				
			}
		}
		
		public EnumFacing toFacing() {
			switch( this ) {
			case NORTH:
				return EnumFacing.NORTH;
			case SOUTH:
				return EnumFacing.SOUTH;
			case WEST:
				return EnumFacing.WEST;
			default:
			case EAST:
				return EnumFacing.EAST;				
			}
		}

		@Override
		public String getName() {
			return toString().toLowerCase();
		}
	}
}
