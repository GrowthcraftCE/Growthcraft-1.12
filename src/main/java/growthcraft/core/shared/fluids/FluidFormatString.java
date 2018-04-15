package growthcraft.core.shared.fluids;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluidFormatString
{
	private FluidFormatString() {}

	public static String format(FluidStack stack)
	{
		if (stack != null)
		{
			final Fluid fluid = stack.getFluid();
			String fluidName = "NULL";
			if (fluid != null) fluidName = fluid.getName();
			return String.format("FluidStack(fluid: %s, amount: %d)", fluidName, stack.amount);
		}
		return "FluidStack(NULL)";
	}
}
