package growthcraft.grapes.common.compat;

import static growthcraft.grapes.shared.init.GrowthcraftGrapesFluids.grapeWineBooze;

import growthcraft.cellar.shared.GrowthcraftCellarApis;
import growthcraft.cellar.shared.config.GrowthcraftCellarConfig;
import growthcraft.cellar.shared.processing.common.Residue;
import growthcraft.core.shared.definition.FluidDefinition;
import growthcraft.core.shared.definition.ItemDefinition;
import growthcraft.core.shared.item.OreItemStacks;
import growthcraft.core.shared.utils.TickUtils;
import growthcraft.grapes.shared.config.GrowthcraftGrapesConfig;
import growthcraft.grapes.shared.init.GrowthcraftGrapesItems.WineTypes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;
import rustic.common.blocks.fluids.ModFluids;
import rustic.common.items.ModItems;

public class InitRustic {
    public static ItemDefinition rusticGrape;
    public static FluidDefinition rusticGrapeJuice;
    public static FluidDefinition rusticGrapeWine;
	
	// TODO: Read crushing and fermenting recipes
    
	////////////
    // Common
	////////////

    public static boolean isModAvailable() {
    	return Loader.isModLoaded("rustic");
    }
    
	////////////
    // Items
	////////////
    
    public static void preInitItems() {
    	rusticGrape = new ItemDefinition(ModItems.GRAPES);
    }
    
	////////////
	// Fluids
	////////////

	public static void preInitFluids() {
		rusticGrapeJuice = new FluidDefinition(ModFluids.GRAPE_JUICE);
		rusticGrapeWine = new FluidDefinition(ModFluids.WINE);
	}
	
	public static void initBoozes() {
		registerFermentations();
	}	

	private static void registerFermentations() {
		final int fermentTime = GrowthcraftCellarConfig.fermentTime;
		FluidStack rusticGrapeJuiceStack = rusticGrapeJuice.asFluidStack();
		FluidStack rusticGrapeWineStack = rusticGrapeWine.asFluidStack();
		
		GrowthcraftCellarApis.boozeBuilderFactory.create(rusticGrapeJuice/*grapeWineBooze[WineTypes.PURPLE_JUICE.ordinal()]*/.getFluid())
//			.tags(BoozeTag.YOUNG)
			.pressesFrom(
				rusticGrape.asStack(),
				TickUtils.seconds(2),
				40,
				Residue.newDefault(0.3F));
		
		// Brewers Yeast, Nether Wart
		GrowthcraftCellarApis.boozeBuilderFactory.create(rusticGrapeWine/*grapeWineBooze[WineTypes.PURPLE_WINE.ordinal()]*/.getFluid())
//			.tags(BoozeTag.WINE, BoozeTag.FERMENTED)
			.fermentsFrom(rusticGrapeJuiceStack, new OreItemStacks("yeastBrewers"), fermentTime)
			.fermentsFrom(rusticGrapeJuiceStack, new ItemStack(Items.NETHER_WART), (int)(fermentTime * 0.66));
//			.getEffect()
//				.setTipsy(BoozeUtils.alcoholToTipsy(0.05f), TickUtils.seconds(90))
//				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);
		
		// Glowstone Dust
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.PURPLE_WINE_POTENT.ordinal()].getFluid())
//			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.POTENT)
			.fermentsFrom(rusticGrapeWineStack, new OreItemStacks("dustGlowstone"), fermentTime);
//			.fermentsFrom(fs[WineTypes.PURPLE_WINE_EXTENDED.ordinal()], new OreItemStacks("dustGlowstone"), fermentTime)
//			.getEffect()
//				.setTipsy(BoozeUtils.alcoholToTipsy(0.07f), TickUtils.seconds(90))
//				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);
		
		// Redstone Dust
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.PURPLE_WINE_EXTENDED.ordinal()].getFluid())
//			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.EXTENDED)
			.fermentsFrom(rusticGrapeWineStack, new OreItemStacks("dustRedstone"), fermentTime);
//			.fermentsFrom(fs[WineTypes.PURPLE_WINE_POTENT.ordinal()], new OreItemStacks("dustRedstone"), fermentTime)
//			.getEffect()
//				.setTipsy(BoozeUtils.alcoholToTipsy(0.05f), TickUtils.seconds(90))
//				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);
		
		// Port Wine - Bayanus Yeast
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.PURPLE_PORTWINE.ordinal()].getFluid())
//			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.FORTIFIED)
			.brewsFrom(
				new FluidStack(rusticGrapeWine.getFluid(), GrowthcraftGrapesConfig.portWineBrewingYield),
				new OreItemStacks("yeastBayanus"),
				GrowthcraftGrapesConfig.portWineBrewingTime,
				Residue.newDefault(0.3F));
//			.getEffect()
//				.setTipsy(BoozeUtils.alcoholToTipsy(0.20f), TickUtils.seconds(90))
//				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 2);
	}
}
