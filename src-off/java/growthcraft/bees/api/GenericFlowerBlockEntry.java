package growthcraft.bees.api;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GenericFlowerBlockEntry extends AbstractFlowerBlockEntry
{
	public GenericFlowerBlockEntry(IBlockState blockState) {
		super(blockState);
	}
	
	public GenericFlowerBlockEntry(Block block, int meta)
	{
		super(block, meta);
	}

	@Override
	public boolean canPlaceAt(World world, BlockPos pos)
	{
		return getBlock().canPlaceBlockAt(world, pos);
	}
}
