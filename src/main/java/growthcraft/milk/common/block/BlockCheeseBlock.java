package growthcraft.milk.common.block;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.shared.block.BlockFlags;
import growthcraft.core.shared.block.GrowthcraftBlockContainer;
import growthcraft.core.shared.block.BlockUtils;
import growthcraft.core.shared.utils.BoundUtils;
import growthcraft.core.shared.item.ItemUtils;
import growthcraft.milk.common.block.BlockOrientable.Orient;
import growthcraft.milk.common.tileentity.TileEntityCheeseBlock;
import growthcraft.milk.shared.definition.EnumCheeseStage;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
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

public class BlockCheeseBlock extends BlockOrientable {
    
	public final static int MAX_VARIANTS = 10;
	
	public final static PropertyInteger TYPE_SLICES_COUNT = PropertyInteger.create("slicescount", 0, 4);
	public final static PropertyInteger TYPE_CHEESE_VARIANT = PropertyInteger.create("typexstage", 0, (MAX_VARIANTS-1) * 3 );

	private static final AxisAlignedBB BOUNDING_BOX_FULL = new AxisAlignedBB(
            0.0625 * 0, 0.0625 * 0, 0.0625 * 0,
            0.0625 * 16, 0.0625 * 8, 0.0625 * 16);
	private static final AxisAlignedBB BOUNDING_BOX_HALF = new AxisAlignedBB(
            0.0625 * 0, 0.0625 * 0, 0.0625 * 0,
            0.0625 * 16, 0.0625 * 8, 0.0625 * 8);
	private static final AxisAlignedBB BOUNDING_BOX_4TH = new AxisAlignedBB(
            0.0625 * 0, 0.0625 * 0, 0.0625 * 0,
            0.0625 * 8, 0.0625 * 8, 0.0625 * 8);

	
	public BlockCheeseBlock() {
		super(Material.CAKE);
		this.setHardness(0.5F);
		this.setSoundType(SoundType.CLOTH);
		this.setDefaultState(this.getBlockState().getBaseState().withProperty(TYPE_SLICES_COUNT, 1));
		setTileEntityType(TileEntityCheeseBlock.class);
	}

	@SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		final TileEntityCheeseBlock te = getTileEntity(source, pos);
		if (te != null)
		{
			int numSlices = te.getCheese().getSlices();
			Orient orient = state.getValue(TYPE_ORIENT);
			AxisAlignedBB bounds;
			if( numSlices >= 3 )
				return BOUNDING_BOX_FULL;
			else if( numSlices >= 2 ) {
				bounds = BOUNDING_BOX_HALF;
			}
			else
				bounds = BOUNDING_BOX_4TH;
			
			return BoundUtils.rotateBlockBounds(bounds, orient.rotationCW);			
		}
    	
        return BOUNDING_BOX_FULL;
    }

	@SuppressWarnings("deprecation")
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, getBoundingBox(state, worldIn, pos));
    }

	@SuppressWarnings("deprecation")
    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

	@SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

	@SuppressWarnings("deprecation")
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
	    return new BlockStateContainer(this, TYPE_ORIENT, TYPE_SLICES_COUNT, TYPE_CHEESE_VARIANT);
	}

	@SuppressWarnings("deprecation")
	@Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
		int variantID = 0;
		int numSlices = 3;
		final TileEntityCheeseBlock te = getTileEntity(worldIn, pos);
		if (te != null)
		{
			int effectiveStageId = 0;
			switch(te.getCheese().getStage()) {
			case UNWAXED:
				effectiveStageId = 2;
				break;
			case CUT:
			case AGED:
				effectiveStageId = 1;
				break;
			default:
			case UNAGED:
				effectiveStageId = 0;
				break;
			}
			
			numSlices = te.getCheese().getSlices();
			variantID = effectiveStageId*10 + te.getCheese().getType().getVariantID();
		}
		
        return state.withProperty(TYPE_CHEESE_VARIANT, variantID).withProperty(TYPE_SLICES_COUNT, numSlices);
    }
}
