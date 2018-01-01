package growthcraft.cellar.common.tileentity;

import growthcraft.core.api.fluids.FluidUtils;
import growthcraft.core.api.fluids.InternalFluidIDHandler;
import growthcraft.core.common.inventory.GrowthcraftTileDeviceBase;
import growthcraft.core.common.tileentity.feature.IGuiNetworkSync;
import growthcraft.core.common.tileentity.feature.IInteractionObject;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public abstract class TileEntityCellarDevice extends GrowthcraftTileDeviceBase implements IGuiNetworkSync, IInteractionObject
{
	InternalFluidIDHandler fluidIds = new InternalFluidIDHandler();
	
	@Override
	public void receiveGUINetworkData(int id, int v)
	{
		// 50 is more than generous right?
		if (id >= 50)
		{
			final int tankIndex = (id - 50) / 3;
			switch ((id - 50) % 3)
			{
				// Fluid ID
				case 0:
				{
					final FluidStack result = FluidUtils.replaceFluidStack(fluidIds.getFluidByInternalID(v), getFluidStack(tankIndex));
					if (result != null) getFluidTank(tankIndex).setFluid(result);
				} break;
				// Fluid amounts CAN exceed a 16bit integer, in order to handle the capacity, the value is split across 2 data points, by high and low bytes
				// Fluid Amount (LOW Bytes)
				case 1:
				{
					final int t = getFluidAmount(tankIndex);
					setFluidStack(tankIndex, FluidUtils.updateFluidStackAmount(getFluidStack(tankIndex), (t & 0xFFFF0000) | v));
				} break;
				// Fluid Amount (HIGH Bytes)
				case 2:
				{
					final int t = getFluidAmount(tankIndex);
					getFluidTank(tankIndex).setFluid(FluidUtils.updateFluidStackAmount(getFluidStack(tankIndex), (t & 0xFFFF) | (v << 16)));
				} break;
				default:
					break;
			}
		}
	}

	@Override
	public void sendGUINetworkData(Container container, IContainerListener iCrafting)
	{
		int i = 0;
		for (FluidTank tank : getFluidTanks())
		{
			final int offset = 50 + i * 3;
			final FluidStack fluid = getFluidStack(i);
			iCrafting.sendWindowProperty(container, offset, fluid != null ? fluidIds.getInternalIDByFluid(fluid.getFluid()) : 0);
			iCrafting.sendWindowProperty(container, offset + 1, fluid != null ? (fluid.amount & 0xFFFF) : 0);
			iCrafting.sendWindowProperty(container, offset + 2, fluid != null ? ((fluid.amount >> 16) & 0xFFFF) : 0);
			i++;
		}
	}
}
