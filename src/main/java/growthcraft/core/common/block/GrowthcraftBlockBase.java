package growthcraft.core.common.block;

import growthcraft.core.api.utils.BlockFlags;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GrowthcraftBlockBase extends Block
{
	public GrowthcraftBlockBase(Material material)
	{
		super(material);
	}

	/**
	 * Drops the block as an item and replaces it with air
	 * @param world - world to drop in
	 * @param pos - Coords
	 */
	public void fellBlockAsItem(World world, BlockPos pos)
	{
		dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
		world.setBlockToAir(pos);
	}
	
	public void markBlockForUpdate(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		worldIn.notifyBlockUpdate(pos, state, state, BlockFlags.UPDATE_AND_SYNC);
	}
}
