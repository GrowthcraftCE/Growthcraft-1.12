package growthcraft.cellar.common.init;

import growthcraft.cellar.Reference;
import growthcraft.cellar.common.lib.booze.effect.EffectTipsy;
import growthcraft.cellar.common.potion.PotionCellar;
import growthcraft.cellar.common.stats.CellarAchievement;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GrowthcraftCellarPotions {
	// REVISE_ME 0

	public static Potion potionTipsy;
	
	public static void registerPotions() {
		// TODO: Add some class PotionDefinition like BlockDefinition or ItemDefinition
		potionTipsy = new PotionCellar(false, 0, 1, 0).setPotionName("potion.tipsy");
		registerPotion(potionTipsy);
		
		EffectTipsy.potionTipsy = potionTipsy;
		EffectTipsy.achievement = CellarAchievement.GET_DRUNK;
	}
	
	protected static void registerPotion(Potion potion) {
		GameRegistry.register(potion, new ResourceLocation(Reference.MODID, potion.getName()));
	}
}
