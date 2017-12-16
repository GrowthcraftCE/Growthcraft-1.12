package growthcraft.grapes.init;

import growthcraft.cellar.GrowthcraftCellar;
import growthcraft.cellar.GrowthcraftCellarConfig;
import growthcraft.cellar.api.booze.Booze;
import growthcraft.cellar.api.booze.BoozeTag;
import growthcraft.cellar.api.processing.common.Residue;
import growthcraft.cellar.common.block.BlockFluidBooze;
import growthcraft.cellar.util.BoozeRegistryHelper;
import growthcraft.cellar.util.BoozeUtils;
import growthcraft.core.GrowthcraftCoreConfig;
import growthcraft.core.api.effect.EffectAddPotionEffect;
import growthcraft.core.api.effect.EffectWeightedRandomList;
import growthcraft.core.api.effect.SimplePotionEffectFactory;
import growthcraft.core.api.item.OreItemStacks;
import growthcraft.core.api.utils.TickUtils;
import growthcraft.grapes.GrowthcraftGrapesConfig;
import growthcraft.grapes.handlers.EnumHandler;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class GrowthcraftGrapesFluids
{
	// REVISE_ME Move blocks to specific class
    // OPEN
	// INITIALIZE
	
	public static Booze[] grapeWineBooze;
	public static BlockFluidBooze[] grapeWineFluids;
    
	public static void init() {
		// OPEN
		grapeWineBooze = new Booze[8];
		BoozeRegistryHelper.initializeBoozeFluids("grc.grapeWine", grapeWineBooze);
		for (Booze booze : grapeWineBooze)
		{
			booze.setColor(GrowthcraftGrapesConfig.grapeWineColor).setDensity(1120);
		}
		BoozeRegistryHelper.initializeBooze(grapeWineBooze, grapeWineFluids);
		BoozeRegistryHelper.setBoozeFoodStats(grapeWineBooze, 1, -0.3f);
		BoozeRegistryHelper.setBoozeFoodStats(grapeWineBooze[0], 1, 0.3f);

		grapeWineBooze[4].setColor(GrowthcraftGrapesConfig.ambrosiaColor);
		grapeWineFluids[4].refreshColor();
		grapeWineBooze[5].setColor(GrowthcraftGrapesConfig.portWineColor);
		grapeWineFluids[5].refreshColor();
		
		//TODO: grapeWine = new ItemDefinition(new ItemBoozeBottle(grapeWineBooze));
	}
	
	public static void register() {
	}
	
	private static void registerFermentations() {
		final int fermentTime = GrowthcraftCellarConfig.fermentTime;
		final FluidStack[] fs = new FluidStack[grapeWineBooze.length];
		for (int i = 0; i < grapeWineBooze.length; ++i)
		{
			fs[i] = new FluidStack(grapeWineBooze[i], 1);
		}
		
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[0])
			.tags(BoozeTag.YOUNG)
			.pressesFrom(
				EnumHandler.GrapeTypes.PURPLE.asStack(),
				TickUtils.seconds(2),
				40,
				Residue.newDefault(0.3F));

		// Brewers Yeast, Nether Wart
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[1])
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED)
			.fermentsFrom(fs[0], new OreItemStacks("yeastBrewers"), fermentTime)
			.fermentsFrom(fs[0], new ItemStack(Items.NETHER_WART), (int)(fermentTime * 0.66))
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.05f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);

		// Glowstone Dust
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[2])
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.POTENT)
			.fermentsFrom(fs[1], new OreItemStacks("dustGlowstone"), fermentTime)
			.fermentsFrom(fs[3], new OreItemStacks("dustGlowstone"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.07f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);

		// Redstone Dust
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[3])
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.EXTENDED)
			.fermentsFrom(fs[1], new OreItemStacks("dustRedstone"), fermentTime)
			.fermentsFrom(fs[2], new OreItemStacks("dustRedstone"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.05f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);

		// Ambrosia - Ethereal Yeast
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[4])
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.HYPER_EXTENDED)
			.fermentsFrom(fs[2], new OreItemStacks("yeastEthereal"), fermentTime)
			.fermentsFrom(fs[3], new OreItemStacks("yeastEthereal"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.053f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.HEALTH_BOOST, TickUtils.minutes(3), 0)
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);
		
		// Port Wine - Bayanus Yeast
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[5])
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.FORTIFIED)
			.brewsFrom(
				new FluidStack(grapeWineBooze[1], GrowthcraftGrapesConfig.portWineBrewingYield),
				new OreItemStacks("yeastBayanus"),
				GrowthcraftGrapesConfig.portWineBrewingTime,
				Residue.newDefault(0.3F))
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.20f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 2);

		// Intoxicated Wine
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[6])
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.INTOXICATED)
			.fermentsFrom(fs[2], new OreItemStacks("yeastOrigin"), fermentTime)
			.fermentsFrom(fs[3], new OreItemStacks("yeastOrigin"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.15f), TickUtils.seconds(90))
				.addEffect(new EffectWeightedRandomList()
					.add(8, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.RESISTANCE, TickUtils.minutes(3), 2)))
					.add(2, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.WEAKNESS, TickUtils.minutes(3), 2))));
		
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[7])
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.POISONED)
			.fermentsTo(fs[1], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsTo(fs[2], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsTo(fs[3], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsTo(fs[4], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsTo(fs[5], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsTo(fs[6], new OreItemStacks("yeastPoison"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.05f), TickUtils.seconds(90))
				.createPotionEntry(MobEffects.POISON, TickUtils.seconds(90), 0).toggleDescription(!GrowthcraftCoreConfig.hidePoisonedBooze);
	}
}
