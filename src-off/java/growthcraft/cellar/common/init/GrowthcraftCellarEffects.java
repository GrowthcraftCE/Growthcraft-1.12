package growthcraft.cellar.common.init;

import growthcraft.cellar.shared.booze.BoozeEffect;
import growthcraft.cellar.shared.booze.effect.EffectTipsy;
import growthcraft.core.shared.CoreRegistry;
import growthcraft.core.shared.effect.EffectRegistry;

public class GrowthcraftCellarEffects
{
	private GrowthcraftCellarEffects() {}

	public static void preInit()
	{
		final EffectRegistry reg = CoreRegistry.instance().getEffectsRegistry();
		reg.register("booze_effect", BoozeEffect.class);
		reg.register("booze_effect_list", BoozeEffect.BoozeEffectList.class);
		reg.register("tipsy", EffectTipsy.class);
	}
	
}
