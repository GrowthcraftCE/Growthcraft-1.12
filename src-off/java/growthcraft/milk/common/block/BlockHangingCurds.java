package growthcraft.milk.common.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.shared.block.GrowthcraftBlockContainer;
import growthcraft.core.shared.block.BlockCheck;
import growthcraft.milk.common.tileentity.TileEntityCheeseBlock;
import growthcraft.milk.common.tileentity.TileEntityHangingCurds;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHangingCurds extends GrowthcraftBlockContainer {
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(
            0.0625 * 4, 0.0625 * 0, 0.0625 * 4,
            0.0625 * 12, 0.0625 * 16, 0.0625 * 12);
	
	public final static PropertyInteger TYPE_CHEESE_VARIANT = PropertyInteger.create("type", 0, BlockCheeseBlock.MAX_VARIANTS-1 );
	
	public BlockHangingCurds() {
		super(Material.CAKE);
		// make it god awful difficult to break by hand.
		setHardness(6.0F);
		setTickRandomly(true);
		this.setSoundType(SoundType.CLOTH);
		setTileEntityType(TileEntityHangingCurds.class);
		this.setDefaultState(this.getBlockState().getBaseState().withProperty(TYPE_CHEESE_VARIANT, 0));
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
		return true;
	}
	
	@Override
	protected ItemStack createHarvestedBlockItemStack(World world, EntityPlayer player, BlockPos pos, IBlockState state)
	{
		final TileEntityHangingCurds te = getTileEntity(world, pos);
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
		final TileEntityHangingCurds te = getTileEntity(world, pos);
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
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ)) return true;
		if (!playerIn.isSneaking())
		{
			final TileEntityHangingCurds hangingCurd = getTileEntity(worldIn, pos);
			if (hangingCurd != null)
			{
				if (hangingCurd.isDried())
				{
					fellBlockAsItem(worldIn, pos);
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		final TileEntityHangingCurds teHangingCurds = getTileEntity(world, pos);
		if (teHangingCurds != null)
		{
			return teHangingCurds.asItemStack();
		}
		return super.getPickBlock(state, target, world, pos, player);
	}
	
	public boolean canBlockStay(World world, BlockPos pos)
	{
		return !world.isAirBlock(pos.up()) &&
			BlockCheck.isBlockPlacableOnSide(world, pos.up(), EnumFacing.DOWN);
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		return super.canPlaceBlockAt(world, pos) && canBlockStay(world, pos);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!this.canBlockStay(worldIn, pos))
		{
			fellBlockAsItem(worldIn, pos);
		}
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
	{
		super.updateTick(world, pos, state, random);
		if (!world.isRemote)
		{
			if (!canBlockStay(world, pos))
			{
				dropBlockAsItem(world, pos, state, 0);
				world.setBlockToAir(pos);
			}
		}
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
	    return new BlockStateContainer(this, TYPE_CHEESE_VARIANT);
	}
	
	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta) {
	    return this.getDefaultState();
	}

	@Override
	public int getMetaFromState(IBlockState state) {
	    return 0;
	}

	@Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
		final TileEntityHangingCurds teHangingCurds = getTileEntity(worldIn, pos);
		if (teHangingCurds != null)
			return state.withProperty(TYPE_CHEESE_VARIANT, teHangingCurds.getCheeseType().getVariantID());
		return state.withProperty(TYPE_CHEESE_VARIANT, 0);
    }

}
