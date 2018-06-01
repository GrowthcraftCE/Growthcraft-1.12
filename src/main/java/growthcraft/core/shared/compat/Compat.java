package growthcraft.core.shared.compat;

import java.util.ArrayList;
import java.util.List;

import growthcraft.core.shared.compat.forestry.ForestryModFluids;
import growthcraft.milk.shared.init.GrowthcraftMilkFluids;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;

// Note: no imports from other mods should be added here 

public class Compat {
	private Compat() {} 
	
	////////////
    // Common
	////////////

    public static boolean isModAvailable_Rustic() {
    	return Loader.isModLoaded("rustic");
    }
    
    public static boolean isModAvailable_Forestry() {
    	return Loader.isModLoaded("forestry");
    }
    
    ////////////
    // Fluids
    ////////////
    
	public static List<Fluid> getMilkFluids()
	{
		final List<Fluid> milks = new ArrayList<Fluid>();
		if (GrowthcraftMilkFluids.milk != null)
			milks.add(GrowthcraftMilkFluids.milk.getFluid());
		if (ForestryModFluids.milk != null && !milks.contains(ForestryModFluids.milk.getFluid()) )
			milks.add(ForestryModFluids.milk.getFluid());
		
		// Automagy Milk
		final Fluid fluidmilk = FluidRegistry.getFluid("fluidmilk");
		if (fluidmilk != null && !milks.contains(fluidmilk))
			milks.add(fluidmilk);
		return milks;
	}
}
