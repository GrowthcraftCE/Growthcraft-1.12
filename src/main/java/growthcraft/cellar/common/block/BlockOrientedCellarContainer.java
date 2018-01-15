package growthcraft.cellar.common.block;

import growthcraft.core.api.utils.BlockFlags;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockOrientedCellarContainer extends BlockCellarContainer {

	public BlockOrientedCellarContainer(Material material) {
		super(material);
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
			final IBlockState southBlockState = world.getBlockState(pos.north()); //(x, y, z - 1);
			final IBlockState northBlockState = world.getBlockState(pos.south()); //(x, y, z + 1);
			final IBlockState westBlockState = world.getBlockState(pos.west());//(x - 1, y, z);
			final IBlockState eastBlockState = world.getBlockState(pos.east());//(x + 1, y, z);
			EnumFacing facing = EnumFacing.SOUTH; //  byte meta = 3;

			if (southBlockState.isFullBlock() && !northBlockState.isFullBlock())
			{
				facing = EnumFacing.SOUTH; //meta = 3;
			}

			if (northBlockState.isFullBlock() && !southBlockState.isFullBlock())
			{
				facing = EnumFacing.NORTH; //meta = 2;
			}

			if (westBlockState.isFullBlock() && !eastBlockState.isFullBlock())
			{
				facing = EnumFacing.EAST; //meta = 5;
			}

			if (eastBlockState.isFullBlock() && !westBlockState.isFullBlock())
			{
				facing = EnumFacing.WEST; //meta = 4;
			}

			world.setBlockState(pos, state.withProperty(TYPE_ROTATION, facing), BlockFlags.UPDATE_AND_SYNC);
		}
	}
	
	protected EnumFacing setOrientWhenPlacing(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, boolean allDirections) {
		EnumFacing facing;
		if( allDirections ) {
			facing = EnumFacing.getDirectionFromEntityLiving(pos, placer);	
		}
		else {
			facing = EnumFacing.fromAngle(placer.rotationYaw);
		}
		worldIn.setBlockState(pos, state.withProperty(TYPE_ROTATION, facing), BlockFlags.UPDATE_AND_SYNC);
		return facing;
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		super.onBlockAdded(worldIn, pos, state);
		setDefaultDirection(worldIn, pos, state);
	}

}
