package growthcraft.cellar.init;

import growthcraft.cellar.api.booze.BoozeEffect;
import growthcraft.cellar.api.booze.effect.EffectTipsy;
import growthcraft.cellar.common.potion.PotionCellar;
import growthcraft.cellar.stats.CellarAchievement;
import growthcraft.core.api.CoreRegistry;
import growthcraft.core.api.EffectRegistry;
import net.minecraft.potion.Potion;

public class GrowthcraftCellarEffects
{
	// REVISE_ME 0
	
	private GrowthcraftCellarEffects() {}

	public static void init()
	{
		final EffectRegistry reg = CoreRegistry.instance().getEffectsRegistry();
		reg.register("booze_effect", BoozeEffect.class);
		reg.register("booze_effect_list", BoozeEffect.BoozeEffectList.class);
		reg.register("tipsy", EffectTipsy.class);
	}
	
}
