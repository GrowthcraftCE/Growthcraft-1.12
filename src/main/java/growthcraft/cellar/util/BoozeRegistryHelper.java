package growthcraft.cellar.util;

import java.util.ArrayList;
import java.util.List;

import growthcraft.cellar.api.CellarRegistry;
import growthcraft.cellar.api.booze.Booze;
import growthcraft.cellar.api.booze.BoozeEffect;
import growthcraft.cellar.api.booze.BoozeEntry;
import growthcraft.cellar.api.booze.BoozeRegistry;
import growthcraft.cellar.api.booze.BoozeTag;
import growthcraft.cellar.common.block.BlockFluidBooze;
import growthcraft.core.api.CoreRegistry;
import growthcraft.core.api.fluids.FluidDictionary;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class BoozeRegistryHelper {
	// OPEN_ADHOC
	
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

	public static void initializeBooze(Booze[] boozes, BlockFluidBooze[] fluidBlocks) {
		// OPEN
		for (int i = 0; i < boozes.length; ++i)
		{
			fluidBlocks[i] = new BlockFluidBooze(boozes[i]);
			
			// TODO: Init, but not register, universal bucket!
		}		
	}
	
	public static void setBoozeFoodStats(Fluid booze, int heal, float saturation)
	{
		final BoozeEntry entry = CellarRegistry.instance().booze().getBoozeEntry(booze);
		if (entry != null)
		{
			entry.setFoodStats(heal, saturation);
		}
	}
	
	public static void setBoozeFoodStats(Fluid[] boozes, int heal, float saturation)
	{
		for (Fluid booze : boozes)
		{
			setBoozeFoodStats(booze, heal, saturation);
		}
	}
	
	public static List<BoozeEffect> getBoozeEffects(Fluid[] boozes)
	{
		final BoozeRegistry reg = CellarRegistry.instance().booze();
		final FluidDictionary dict = CoreRegistry.instance().fluidDictionary();
		final List<BoozeEffect> effects = new ArrayList<BoozeEffect>();
		for (int i = 0; i < boozes.length; ++i)
		{
			if (dict.hasFluidTags(boozes[i], BoozeTag.FERMENTED))
			{
				effects.add(reg.getEffect(boozes[i]));
			}
		}
		return effects;
	}
}
