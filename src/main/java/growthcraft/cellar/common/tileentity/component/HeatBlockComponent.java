package growthcraft.cellar.common.tileentity.component;

import growthcraft.cellar.api.CellarRegistry;
import growthcraft.cellar.api.processing.heatsource.IHeatSourceBlock;
import growthcraft.core.shared.block.BlockCheck;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Component for handling heat source blocks for a tile entity
 */
public class HeatBlockComponent
{
	private TileEntity tileEntity;
	private EnumFacing sourceDir = EnumFacing.DOWN;
	// Adjacent heating allows the block to accept heat from blocks on the same y axis and directly
	// adjacent to it
	private float adjacentHeating;

	/**
	 * @param te - parent tile entity
	 * @param adh - adjacent heating rate, set to 0 to disable
	 */
	public HeatBlockComponent(TileEntity te, float adh)
	{
		this.tileEntity = te;
		this.adjacentHeating = adh;
	}

	private World getWorld()
	{
		return tileEntity.getWorld();
	}

	public float getHeatMultiplierFromDir(EnumFacing dir)
	{
		BlockPos pos = tileEntity.getPos().offset(dir);
//		final int x = tileEntity.xCoord + dir.offsetX;
//		final int y = tileEntity.yCoord + dir.offsetY;
//		final int z = tileEntity.zCoord + dir.offsetZ;

		final IBlockState blockState = getWorld().getBlockState(pos);

		final IHeatSourceBlock heatSource = CellarRegistry.instance().heatSource().getHeatSource(blockState);

		if (heatSource != null) return heatSource.getHeat(getWorld(), pos);
		return 0.0f;
	}

	public float getHeatMultiplierForAdjacent()
	{
		if (adjacentHeating > 0)
		{
			float heat = 0.0f;
			for (EnumFacing dir : BlockCheck.DIR4)
			{
				heat += getHeatMultiplierFromDir(dir);
			}
			return heat * adjacentHeating;
		}
		return 0.0f;
	}

	public float getHeatMultiplier()
	{
		return getHeatMultiplierFromDir(sourceDir) + getHeatMultiplierForAdjacent();
	}
}
