package growthcraft.cellar.common.block;

import java.util.Random;

import growthcraft.cellar.Reference;
import growthcraft.cellar.common.block.BlockFruitPresser.Orient;
import growthcraft.cellar.common.tileentity.TileEntityFruitPress;
import growthcraft.cellar.init.GrowthcraftCellarBlocks;
import growthcraft.core.api.utils.BlockFlags;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFruitPress extends BlockOrientedCellarContainer {
	// INITIALIZE
	
	public BlockFruitPress(String unlocalizedName) {
		super(Material.WOOD);
		setTileEntityType(TileEntityFruitPress.class);
		setHardness(2.0F);
//TODO:		setStepSound(soundTypeWood);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}
	
	private Block getPresserBlock()
	{
		return GrowthcraftCellarBlocks.fruitPresser.getBlock();
	}
	
	@Override
	public void doRotateBlock(World world, BlockPos pos, EnumFacing side)
	{
		IBlockState statePress = world.getBlockState(pos);
		IBlockState statePresser = world.getBlockState(pos.up());
		
		EnumFacing pressFacing = statePress.getValue(TYPE_ROTATION);
		Orient presserOrient = statePresser.getValue(BlockFruitPresser.TYPE_ORIENT);
		if( pressFacing == EnumFacing.NORTH || pressFacing == EnumFacing.SOUTH )
			pressFacing = EnumFacing.EAST;
		else
			pressFacing = EnumFacing.SOUTH;
		presserOrient = Orient.fromFacing(pressFacing);

		world.setBlockState(pos, statePress.withProperty(TYPE_ROTATION, pressFacing), BlockFlags.SYNC);
		world.setBlockState(pos.up(), statePresser.withProperty(BlockFruitPresser.TYPE_ORIENT, presserOrient), BlockFlags.SYNC);
	}
	
	private void placePresserFor(World world, BlockPos pos, EnumFacing facing) {
		IBlockState statePresser = getPresserBlock().getDefaultState()
                .withProperty(BlockFruitPresser.TYPE_ORIENT, Orient.fromFacing(facing) );
		world.setBlockState(pos.up(), statePresser, BlockFlags.SYNC);
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		super.onBlockAdded(world, pos, state);
		EnumFacing facing = state.getValue(TYPE_ROTATION);
		placePresserFor(world, pos, facing);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		EnumFacing facing = setOrientWhenPlacing(worldIn, pos, state, placer, false);
		placePresserFor(worldIn, pos, facing);
	}
	
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		if (player.capabilities.isCreativeMode && /*(m & 8) != 0 &&*/ presserIsAbove(world, pos))
		{
			// NOTE: A state like BlockSkull.NODROP is not existing, so ignored (m & 8) != 0 condition.  
			
			world.destroyBlock(pos.up(), true);
			world.getTileEntity(pos.up()).invalidate();
		}
		
		super.onBlockHarvested(world, pos, state, player);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if (!this.canBlockStay(worldIn, pos))
		{
			worldIn.destroyBlock(pos, true);
		}
	}

	/************
	 * CONDITIONS
	 ************/
	@Override
	public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		EnumFacing facing = state.getValue(TYPE_ROTATION);
		Orient orient = Orient.fromFacing(facing);

		if (orient == Orient.TOEAST)
		{
			return side == EnumFacing.EAST || side == EnumFacing.WEST;
		}
		else if (orient == Orient.TOSOUTH)
		{
			return side == EnumFacing.NORTH || side == EnumFacing.SOUTH;
		}

		return isNormalCube(state, world, pos);
	}

	
	/************
	 * STUFF
	 ************/

	/**
	 * @param world - world block is in
	 * @param x - x coord
	 * @param y - y coord
	 * @param z - z coord
	 * @return true if the BlockFruitPresser is above this block, false otherwise
	 */
	public boolean presserIsAbove(IBlockAccess world, BlockPos pos)
	{
		return getPresserBlock() == world.getBlockState(pos.up());
	}

	public boolean canBlockStay(IBlockAccess world, BlockPos pos)
	{
		return presserIsAbove(world, pos);
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		if (pos.getY() >= 255) return false;

		return world.getBlockState(pos.down()).isTopSolid() &&
			super.canPlaceBlockAt(world, pos) &&
			super.canPlaceBlockAt(world, pos.up());
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 1;
	}
	
	/************
	 * RENDERS
	 ************/

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
    
	/************
	 * COMPARATOR
	 ************/
	@Override
	public boolean hasComparatorInputOverride(IBlockState state)
	{
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
	{
		final TileEntityFruitPress te = getTileEntity(world, pos);
		if (te != null)
		{
			return te.getFluidAmountScaled(15, 0);
		}
		return 0;
	}
}
