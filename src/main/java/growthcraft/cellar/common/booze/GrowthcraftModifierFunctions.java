package growthcraft.cellar.common.booze;

import growthcraft.cellar.api.CellarRegistry;
import growthcraft.cellar.api.booze.BoozeTag;

public class GrowthcraftModifierFunctions {
	// REVISE_ME 0
	
	public static void registerBoozeModifierFunctions()
	{
		CellarRegistry.instance().booze().setModifierFunction(BoozeTag.POTENT, new ModifierFunctionPotent());
		CellarRegistry.instance().booze().setModifierFunction(BoozeTag.EXTENDED, new ModifierFunctionExtended());
		CellarRegistry.instance().booze().setModifierFunction(BoozeTag.HYPER_EXTENDED, new ModifierFunctionHyperExtended());
	}
}
