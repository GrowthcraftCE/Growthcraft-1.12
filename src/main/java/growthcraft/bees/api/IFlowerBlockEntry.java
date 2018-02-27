package growthcraft.bees.api;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IFlowerBlockEntry
{
	default IBlockState getBlockState()
	{
		return getBlock().getStateFromMeta(getBlockMeta());
	}
	
	Block getBlock();
	int getBlockMeta();
	
	boolean canPlaceAt(World world, BlockPos pos);
}
