package growthcraft.grapes.common.compat.rustic;

import static growthcraft.grapes.shared.init.GrowthcraftGrapesFluids.grapeWineBooze;

import growthcraft.cellar.shared.GrowthcraftCellarApis;
import growthcraft.cellar.shared.booze.BoozeTag;
import growthcraft.cellar.shared.config.GrowthcraftCellarConfig;
import growthcraft.cellar.shared.processing.common.Residue;
import growthcraft.core.shared.compat.rustic.RusticModFluids;
import growthcraft.core.shared.compat.rustic.RusticModItems;
import growthcraft.core.shared.item.OreItemStacks;
import growthcraft.core.shared.utils.TickUtils;
import growthcraft.grapes.shared.config.GrowthcraftGrapesConfig;
import growthcraft.grapes.shared.init.GrowthcraftGrapesItems.WineTypes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class Recipes {
	private Recipes() {}
	
	////////////
	// Fermentations
	////////////
	
	public static void initBoozes() {
		registerFermentations();
	}	

	private static void registerFermentations() {
		final int costRatio = 6;	// paying for higher press output by a slower booze production. Approx (250 / 40)  
		final int fermentTime = GrowthcraftCellarConfig.fermentTime;
		FluidStack rusticGrapeJuiceStack = RusticModFluids.rusticGrapeJuice.asFluidStack();
		FluidStack rusticGrapeWineStack = RusticModFluids.rusticGrapeWine.asFluidStack();
		
		GrowthcraftCellarApis.boozeBuilderFactory.create(RusticModFluids.rusticGrapeJuice.getFluid())
			.tags(BoozeTag.YOUNG)
			.pressesFrom(
					RusticModItems.rusticGrape.asStack(),
				TickUtils.seconds(2),
				250,
				Residue.newDefault(0.3F));
		GrowthcraftCellarApis.boozeBuilderFactory.create(RusticModFluids.rusticGrapeWine.getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED);
		
		// Brewers Yeast, Nether Wart
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.PURPLE_WINE.ordinal()].getFluid())
			.fermentsFrom(rusticGrapeJuiceStack, new OreItemStacks("yeastBrewers"), fermentTime * costRatio)
			.fermentsFrom(rusticGrapeJuiceStack, new ItemStack(Items.NETHER_WART), (int)(fermentTime * costRatio * 0.66));
		
		// Glowstone Dust
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.PURPLE_WINE_POTENT.ordinal()].getFluid())
			.fermentsFrom(rusticGrapeWineStack, new OreItemStacks("dustGlowstone"), fermentTime * costRatio);
		
		// Redstone Dust
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.PURPLE_WINE_EXTENDED.ordinal()].getFluid())
			.fermentsFrom(rusticGrapeWineStack, new OreItemStacks("dustRedstone"), fermentTime * costRatio);
		
		// Port Wine - Bayanus Yeast
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.PURPLE_PORTWINE.ordinal()].getFluid())
			.brewsFrom(
				new FluidStack(RusticModFluids.rusticGrapeWine.getFluid(), GrowthcraftGrapesConfig.portWineBrewingYield),
				new OreItemStacks("yeastBayanus"),
				false,
				GrowthcraftGrapesConfig.portWineBrewingTime * costRatio,
				Residue.newDefault(0.3F));

		// Intoxicated Wine
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.PURPLE_WINE_POISONED.ordinal()].getFluid())
			.fermentsFrom(rusticGrapeWineStack, new OreItemStacks("yeastPoison"), fermentTime * costRatio);
	}
}
