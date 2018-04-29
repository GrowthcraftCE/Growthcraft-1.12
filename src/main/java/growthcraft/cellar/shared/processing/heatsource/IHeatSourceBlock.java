package growthcraft.cellar.shared.processing.heatsource;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IHeatSourceBlock
{
	float getHeat(World world, BlockPos pos);
}
