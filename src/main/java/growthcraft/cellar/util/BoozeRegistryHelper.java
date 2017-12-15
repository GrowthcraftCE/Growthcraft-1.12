package growthcraft.cellar.util;

import growthcraft.cellar.api.CellarRegistry;
import growthcraft.cellar.api.booze.Booze;
import net.minecraftforge.fluids.FluidRegistry;

public class BoozeRegistryHelper {
	private BoozeRegistryHelper() {}

	public static void initializeBoozeFluids(String basename, Booze[] boozes)
	{
		for (int i = 0; i < boozes.length; ++i)
		{
			boozes[i] = new Booze(basename + i);
			FluidRegistry.registerFluid(boozes[i]);
			CellarRegistry.instance().booze().registerBooze(boozes[i]);
		}
	}
}
