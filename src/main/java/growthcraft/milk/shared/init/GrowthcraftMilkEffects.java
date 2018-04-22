package growthcraft.milk.shared.init;

import growthcraft.core.shared.CoreRegistry;
import growthcraft.core.shared.effect.EffectRegistry;
import growthcraft.milk.common.lib.effect.EffectMilk;

public class GrowthcraftMilkEffects {
	private GrowthcraftMilkEffects() {}

	public static void preInit()
	{
		final EffectRegistry reg = CoreRegistry.instance().getEffectsRegistry();
		reg.register("booze_milk", EffectMilk.class);
//TODO:		reg.register("evil_booze_milk", EffectEvilBoozeMilk.class);
	}
}
