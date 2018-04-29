package growthcraft.milk.common.tileentity;

import growthcraft.core.shared.tileentity.feature.IFluidTankOperable;

/**
 * Implement this interface in any TileEntity you wish to treat as a Pancheon
 */
public interface IPancheonTile
{
	IFluidTankOperable getPancheonFluidHandler();
}
