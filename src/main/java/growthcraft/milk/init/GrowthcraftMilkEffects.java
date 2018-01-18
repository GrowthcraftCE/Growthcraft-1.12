package growthcraft.milk.init;

import growthcraft.core.api.CoreRegistry;
import growthcraft.core.api.EffectRegistry;
import growthcraft.milk.common.effect.EffectMilk;

public class GrowthcraftMilkEffects {
	private GrowthcraftMilkEffects() {}

	public static void preInit()
	{
		final EffectRegistry reg = CoreRegistry.instance().getEffectsRegistry();
		reg.register("booze_milk", EffectMilk.class);
//TODO:		reg.register("evil_booze_milk", EffectEvilBoozeMilk.class);
	}
}
