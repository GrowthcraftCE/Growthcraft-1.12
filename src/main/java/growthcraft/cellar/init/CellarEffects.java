package growthcraft.cellar.init;

import growthcraft.cellar.api.booze.BoozeEffect;
import growthcraft.cellar.api.booze.effect.EffectTipsy;
import growthcraft.core.api.CoreRegistry;
import growthcraft.core.api.EffectRegistry;

public class CellarEffects
{
	// REVISE_ME 0
	// INITIALIZE
	
	private CellarEffects() {}

	public static void init()
	{
		final EffectRegistry reg = CoreRegistry.instance().getEffectsRegistry();
		reg.register("booze_effect", BoozeEffect.class);
		reg.register("booze_effect_list", BoozeEffect.BoozeEffectList.class);
		reg.register("tipsy", EffectTipsy.class);
	}
}
