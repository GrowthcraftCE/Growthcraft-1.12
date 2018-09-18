package growthcraft.milk.common.block;

import javax.annotation.Nonnull;

import growthcraft.core.shared.block.BlockFlags;
import growthcraft.core.shared.block.GrowthcraftBlockContainer;
import growthcraft.core.shared.block.GrowthcraftRotatableBlockContainer;
import growthcraft.core.shared.block.IRotatableBlock;
import growthcraft.core.shared.block.BlockUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockOrientable extends GrowthcraftBlockContainer implements IRotatableBlock {

	public final static PropertyEnum<Orient> TYPE_ORIENT = PropertyEnum.create("orient", Orient.class);
	
	public BlockOrientable(Material material) {
		super(material);
		this.setDefaultState(this.getBlockState().getBaseState().withProperty(TYPE_ORIENT, Orient.NORTH));
	}

	@Override
	public boolean isRotatable(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return true;
	}
	
	public void doRotateBlock(World world, BlockPos pos, IBlockState state, EnumFacing side) {
		Orient orient = state.getValue(TYPE_ORIENT);
		switch(orient) {
		case NORTH:
			orient = Orient.WEST;
			break;
		case WEST:
			orient = Orient.SOUTH;
			break;
		case SOUTH:
			orient = Orient.EAST;
			break;
		case EAST:
			orient = Orient.NORTH;
			break;
		}
		
		world.setBlockState(pos, state.withProperty(TYPE_ORIENT, orient), BlockFlags.UPDATE_AND_SYNC);		
	}
	
	protected void setDefaultDirection(World world, BlockPos pos, IBlockState state)
	{
		if (!world.isRemote)
		{
			Orient facing = Orient.fromFacing(BlockUtils.getDefaultDirection(world, pos, state));
			world.setBlockState(pos, state.withProperty(TYPE_ORIENT, facing), BlockFlags.UPDATE_AND_SYNC);
		}
	}
	
	protected Orient setOrientWhenPlacing(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer) {
		Orient facing = Orient.fromFacing(EnumFacing.fromAngle(placer.rotationYaw));
		worldIn.setBlockState(pos, state.withProperty(TYPE_ORIENT, facing), BlockFlags.UPDATE_AND_SYNC);
		return facing;
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		setDefaultDirection(worldIn, pos, state);
		super.onBlockAdded(worldIn, pos, state);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		setOrientWhenPlacing(worldIn, pos, state, placer);
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
	
	/************
	 * PROPERTIES
	 ************/	
	
	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, TYPE_ORIENT);
	}

	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta) {
	    return this.getDefaultState()
	    		   .withProperty(TYPE_ORIENT, Orient.values()[meta & 0x3]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
	    return state.getValue(TYPE_ORIENT).ordinal();
	}

	public static enum Orient implements IStringSerializable {
		NORTH(1),
		SOUTH(3),
		WEST(2),
		EAST(0);
		
		public final int rotationCW;
		
		Orient(int rotationCW) {
			this.rotationCW = rotationCW;
		}
		
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
