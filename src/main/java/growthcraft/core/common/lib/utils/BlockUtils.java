package growthcraft.core.common.lib.utils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockUtils {
	public static EnumFacing getDefaultDirection(World world, BlockPos pos, IBlockState state)
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
		
		return facing;
	}
}
