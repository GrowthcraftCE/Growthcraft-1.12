package growthcraft.core.shared.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class FenceUtils {
	private FenceUtils() {}
	
	public static boolean canFenceConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		BlockFence stdFence = (BlockFence)Blocks.OAK_FENCE;	// NOTE: Using oak fence as standard to test connectivity.
		return canFenceConnectTo(stdFence, world, pos, facing);
	}
	
    public static boolean canFenceConnectTo(BlockFence fenceType, IBlockAccess world, BlockPos pos, EnumFacing facing)
    {
    	// SYNC: Keep consistent with net.minecraft.block.BlockFence.canFenceConnectTo(IBlockAccess, BlockPos, EnumFacing)
        BlockPos other = pos.offset(facing);
        Block block = world.getBlockState(other).getBlock();
        return block.canBeConnectedTo(world, other, facing.getOpposite()) || fenceType.canConnectTo(world, other, facing.getOpposite());
    }
}
