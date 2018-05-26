package growthcraft.grapes.common.compat;

import static growthcraft.grapes.shared.init.GrowthcraftGrapesFluids.grapeWineBooze;

import growthcraft.cellar.shared.GrowthcraftCellarApis;
import growthcraft.cellar.shared.booze.BoozeTag;
import growthcraft.cellar.shared.booze.BoozeUtils;
import growthcraft.cellar.shared.config.GrowthcraftCellarConfig;
import growthcraft.cellar.shared.processing.common.Residue;
import growthcraft.core.shared.definition.FluidDefinition;
import growthcraft.core.shared.definition.ItemDefinition;
import growthcraft.core.shared.effect.EffectAddPotionEffect;
import growthcraft.core.shared.effect.EffectWeightedRandomList;
import growthcraft.core.shared.effect.SimplePotionEffectFactory;
import growthcraft.core.shared.item.OreItemStacks;
import growthcraft.core.shared.utils.TickUtils;
import growthcraft.grapes.shared.config.GrowthcraftGrapesConfig;
import growthcraft.grapes.shared.init.GrowthcraftGrapesItems.WineTypes;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
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
		final int costRatio = 6;	// paying for higher press output by a slower booze production. Approx (250 / 40)  
		final int fermentTime = GrowthcraftCellarConfig.fermentTime;
		FluidStack rusticGrapeJuiceStack = rusticGrapeJuice.asFluidStack();
		FluidStack rusticGrapeWineStack = rusticGrapeWine.asFluidStack();
		
		GrowthcraftCellarApis.boozeBuilderFactory.create(rusticGrapeJuice/*grapeWineBooze[WineTypes.PURPLE_JUICE.ordinal()]*/.getFluid())
			.tags(BoozeTag.YOUNG)
			.pressesFrom(
				rusticGrape.asStack(),
				TickUtils.seconds(2),
				250,
				Residue.newDefault(0.3F));
		GrowthcraftCellarApis.boozeBuilderFactory.create(rusticGrapeWine.getFluid())
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
				new FluidStack(rusticGrapeWine.getFluid(), GrowthcraftGrapesConfig.portWineBrewingYield),
				new OreItemStacks("yeastBayanus"),
				GrowthcraftGrapesConfig.portWineBrewingTime * costRatio,
				Residue.newDefault(0.3F));

		// Intoxicated Wine
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.PURPLE_WINE_POISONED.ordinal()].getFluid())
			.fermentsFrom(rusticGrapeWineStack, new OreItemStacks("yeastPoison"), fermentTime * costRatio);
	}
}
