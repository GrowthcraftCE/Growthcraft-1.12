package growthcraft.cellar.init;

import growthcraft.cellar.api.booze.effect.EffectTipsy;
import growthcraft.cellar.common.potion.PotionCellar;
import growthcraft.cellar.stats.CellarAchievement;
import net.minecraft.potion.Potion;

public class GrowthcraftCellarPotions {
	// REVISE_ME 0

	public static Potion potionTipsy;
	
	public static void registerPotions() {
		potionTipsy = new PotionCellar(false, 0, 0, 0).setPotionName("potion.tipsy");
		EffectTipsy.potionTipsy = potionTipsy;
		EffectTipsy.achievement = CellarAchievement.GET_DRUNK;
	}
}
