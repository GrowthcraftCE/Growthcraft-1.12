package growthcraft.core.shared.block;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface IRotatableBlock
{
	/**
	 * Can this block be rotated?
	 *
	 * @param world - world the block is to be rotated in
	 * @param x - x coord
	 * @param y - y coord
	 * @param z - z coord
	 * @param side - side that block should be rotated from
	 * @return true, if it can be rotated, false otherwise
	 */
	boolean isRotatable(IBlockAccess world, BlockPos pos, EnumFacing side);
}
