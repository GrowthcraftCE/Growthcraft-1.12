package growthcraft.apples.common.compat.rustic;

import static growthcraft.apples.shared.init.GrowthcraftApplesFluids.appleCiderBooze;

import growthcraft.apples.shared.init.GrowthcraftApplesItems.AppleCiderTypes;
import growthcraft.cellar.shared.GrowthcraftCellarApis;
import growthcraft.cellar.shared.booze.BoozeTag;
import growthcraft.cellar.shared.config.GrowthcraftCellarConfig;
import growthcraft.core.shared.compat.rustic.RusticModFluids;
import growthcraft.core.shared.item.OreItemStacks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class RusticApplesRecipes {
	private RusticApplesRecipes() {}
	
	////////////
	// Fermentations
	////////////
	
	public static void initBoozes() {
		registerFermentations();
	}	

	private static void registerFermentations() {
		final int costRatio = 6;	// TODO: Balance recipe outputs!  
		final int fermentTime = GrowthcraftCellarConfig.fermentTime;
		FluidStack rusticAppleJuiceStack = RusticModFluids.rusticAppleJuice.asFluidStack();
		FluidStack rusticAppleCiderStack = RusticModFluids.rusticAppleCider.asFluidStack();
		
		GrowthcraftCellarApis.boozeBuilderFactory.create(RusticModFluids.rusticGrapeWine.getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED);
		
		// Brewers Yeast, Nether Wart
		GrowthcraftCellarApis.boozeBuilderFactory.create(appleCiderBooze[AppleCiderTypes.APPLE_CIDER_FERMENTED.ordinal()].getFluid())
			.fermentsFrom(rusticAppleJuiceStack, new OreItemStacks("yeastBrewers"), fermentTime * costRatio)
			.fermentsFrom(rusticAppleJuiceStack, new ItemStack(Items.NETHER_WART), (int)(fermentTime * costRatio * 0.66));
		
		// Glowstone Dust
		GrowthcraftCellarApis.boozeBuilderFactory.create(appleCiderBooze[AppleCiderTypes.APPLE_CIDER_POTENT.ordinal()].getFluid())
			.fermentsFrom(rusticAppleCiderStack, new OreItemStacks("dustGlowstone"), fermentTime * costRatio);
		
		// Redstone Dust
		GrowthcraftCellarApis.boozeBuilderFactory.create(appleCiderBooze[AppleCiderTypes.APPLE_CIDER_EXTENDED.ordinal()].getFluid())
			.fermentsFrom(rusticAppleCiderStack, new OreItemStacks("dustRedstone"), fermentTime * costRatio);
		
		// Poisoned Cider
		GrowthcraftCellarApis.boozeBuilderFactory.create(appleCiderBooze[AppleCiderTypes.APPLE_CIDER_POISONED.ordinal()].getFluid())
			.fermentsFrom(rusticAppleCiderStack, new OreItemStacks("yeastPoison"), fermentTime * costRatio);
	}
}
