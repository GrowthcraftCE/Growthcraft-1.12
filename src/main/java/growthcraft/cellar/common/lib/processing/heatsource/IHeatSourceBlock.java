package growthcraft.cellar.common.lib.processing.heatsource;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IHeatSourceBlock
{
	float getHeat(World world, BlockPos pos);
}
