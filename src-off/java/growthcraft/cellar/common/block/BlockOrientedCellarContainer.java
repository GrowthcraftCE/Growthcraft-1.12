package growthcraft.cellar.common.block;

import growthcraft.core.api.utils.BlockFlags;
import growthcraft.core.utils.BlockUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
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
			EnumFacing facing = BlockUtils.getDefaultDirection(world, pos, state);
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
