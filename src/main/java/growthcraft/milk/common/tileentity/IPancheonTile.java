package growthcraft.milk.common.tileentity;

import growthcraft.core.common.block.IGrowthcraftTankOperable;

/**
 * Implement this interface in any TileEntity you wish to treat as a Pancheon
 */
public interface IPancheonTile
{
	IGrowthcraftTankOperable getPancheonFluidHandler();
}
