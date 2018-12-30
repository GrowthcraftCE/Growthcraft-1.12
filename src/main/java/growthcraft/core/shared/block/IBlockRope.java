package growthcraft.core.shared.block;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface IBlockRope
{
	/**
	 * @param world - world to interact with
	 * @param x - x coord
	 * @param y - y coord
	 * @param z - z coord
	 * @return can the rope be connected at this point?
	 *
	 * <pre>
	 * {@code
	 *	 public boolean canConnectRopeTo(IBlockAccess world, int x, int y, int z)
	 *	 {
	 *	 	 return world.getBlock(x, y, z) instanceof IBlockRope;
	 *	 }
	 * }
	 * </pre>
	 */
	public boolean canConnectRopeTo(IBlockAccess world, BlockPos pos, EnumFacing facing);
	
	public boolean canRopeBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing);
}
