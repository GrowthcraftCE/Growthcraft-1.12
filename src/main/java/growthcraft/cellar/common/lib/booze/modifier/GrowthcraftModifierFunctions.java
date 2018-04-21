package growthcraft.cellar.common.lib.booze.modifier;

import growthcraft.cellar.common.lib.CellarRegistry;
import growthcraft.cellar.common.lib.booze.BoozeTag;

public class GrowthcraftModifierFunctions {
	// REVISE_ME 0
	
	public static void registerBoozeModifierFunctions()
	{
		CellarRegistry.instance().booze().setModifierFunction(BoozeTag.POTENT, new ModifierFunctionPotent());
		CellarRegistry.instance().booze().setModifierFunction(BoozeTag.EXTENDED, new ModifierFunctionExtended());
		CellarRegistry.instance().booze().setModifierFunction(BoozeTag.HYPER_EXTENDED, new ModifierFunctionHyperExtended());
	}
}
