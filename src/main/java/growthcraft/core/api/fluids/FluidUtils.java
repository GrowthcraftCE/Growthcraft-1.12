package growthcraft.core.api.fluids;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidUtils {
	// REVISE_ME 0
	// OPEN_ADHOC
	
	public static boolean doesFluidExist(Fluid fluid)
	{
		return fluid != null && FluidRegistry.isFluidRegistered(fluid);
	}

	public static boolean doesFluidsExist(Fluid[] fluid)
	{
		for (int i = 0; i < fluid.length; ++i)
		{
			if (!doesFluidExist(fluid[i]))
			{
				return false;
			}
		}
		return true;
	}
}
