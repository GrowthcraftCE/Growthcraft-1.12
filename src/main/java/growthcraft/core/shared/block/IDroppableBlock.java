package growthcraft.core.shared.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Interface for blocks that can be dropped as an item, normally removing
 * itself from the world (replacing itself as an air block)
 */
public interface IDroppableBlock
{
	void fellBlockAsItem(World world, BlockPos pos);
}
