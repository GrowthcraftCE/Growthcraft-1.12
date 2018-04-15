package growthcraft.cellar.api.processing.heatsource;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GenericHeatSourceBlock implements IHeatSourceBlock
{
	private Block block;
	private float heat;

	public GenericHeatSourceBlock(Block blk, float ht)
	{
		this.block = blk;
		this.heat = ht;
	}

	@Override
	public float getHeat(World world, BlockPos pos)
	{
		return heat;
	}
}