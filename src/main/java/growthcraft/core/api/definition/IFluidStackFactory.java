package growthcraft.core.api.definition;

import net.minecraftforge.fluids.FluidStack;

public interface IFluidStackFactory
{
	/**
	 * Creates a new FluidStack with the amount specified.
	 *
	 * @return the fluid stack
	 */
	public FluidStack asFluidStack(int amount);

	/**
	 * Creates a new FluidStack with its default amount (normally 1).
	 *
	 * @return the fluid stack
	 */
	public FluidStack asFluidStack();
}
