package growthcraft.grapes.init;

import growthcraft.cellar.GrowthcraftCellar;
import growthcraft.cellar.GrowthcraftCellarConfig;
import growthcraft.cellar.api.booze.BoozeTag;
import growthcraft.cellar.api.processing.common.Residue;
import growthcraft.cellar.common.definition.BlockBoozeDefinition;
import growthcraft.cellar.common.definition.BoozeDefinition;
import growthcraft.cellar.common.item.ItemBoozeBottle;
import growthcraft.cellar.util.BoozeRegistryHelper;
import growthcraft.cellar.util.BoozeUtils;
import growthcraft.core.GrowthcraftCoreConfig;
import growthcraft.core.api.effect.EffectAddPotionEffect;
import growthcraft.core.api.effect.EffectWeightedRandomList;
import growthcraft.core.api.effect.SimplePotionEffectFactory;
import growthcraft.core.api.item.OreItemStacks;
import growthcraft.core.api.utils.TickUtils;
import growthcraft.core.common.definition.ItemTypeDefinition;
import growthcraft.grapes.GrowthcraftGrapesConfig;
import growthcraft.grapes.Reference;
import growthcraft.grapes.handlers.EnumHandler.GrapeTypes;
import growthcraft.grapes.handlers.EnumHandler.WineTypes;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class GrowthcraftGrapesFluids
{
	public static BoozeDefinition[] grapeWineBooze;
    
	public static void init() {
		grapeWineBooze = new BoozeDefinition[WineTypes.values().length];
		GrowthcraftGrapesBlocks.grapeWineFluidBlocks = new BlockBoozeDefinition[grapeWineBooze.length];
		BoozeRegistryHelper.initializeAndRegisterBoozeFluids(grapeWineBooze, WineTypes.class, "");
//		for (BoozeDefinition booze : grapeWineBooze)
//		{
//			booze.getFluid().setColor(GrowthcraftGrapesConfig.grapeWinePurpleColor).setDensity(1120);
//		}
		grapeWineBooze[WineTypes.PURPLE_JUICE.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.grapeWinePurpleColor).setDensity(1120);
		grapeWineBooze[WineTypes.PURPLE_WINE.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.grapeWinePurpleColor).setDensity(1120);
		grapeWineBooze[WineTypes.PURPLE_WINE_POTENT.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.grapeWinePurpleColor).setDensity(1120);
		grapeWineBooze[WineTypes.PURPLE_WINE_EXTENDED.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.grapeWinePurpleColor).setDensity(1120);
		grapeWineBooze[WineTypes.PURPLE_AMBROSIA.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.ambrosiaPurpleColor).setDensity(1120);
		grapeWineBooze[WineTypes.PURPLE_PORTWINE.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.portWinePurpleColor).setDensity(1120);
		grapeWineBooze[WineTypes.PURPLE_WINE_INTOXICATED.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.grapeWinePurpleColor).setDensity(1120);
		grapeWineBooze[WineTypes.PURPLE_WINE_POISONED.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.grapeWinePurpleColor).setDensity(1120);

		grapeWineBooze[WineTypes.RED_JUICE.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.grapeWineRedColor).setDensity(1120);
		grapeWineBooze[WineTypes.RED_WINE.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.grapeWineRedColor).setDensity(1120);
		grapeWineBooze[WineTypes.RED_WINE_POTENT.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.grapeWineRedColor).setDensity(1120);
		grapeWineBooze[WineTypes.RED_WINE_EXTENDED.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.grapeWineRedColor).setDensity(1120);
		grapeWineBooze[WineTypes.RED_AMBROSIA.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.ambrosiaRedColor).setDensity(1120);
		grapeWineBooze[WineTypes.RED_PORTWINE.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.portWineRedColor).setDensity(1120);
		grapeWineBooze[WineTypes.RED_WINE_INTOXICATED.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.grapeWineRedColor).setDensity(1120);
		grapeWineBooze[WineTypes.RED_WINE_POISONED.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.grapeWineRedColor).setDensity(1120);

		grapeWineBooze[WineTypes.GREEN_JUICE.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.grapeWineGreenColor).setDensity(1120);
		grapeWineBooze[WineTypes.GREEN_WINE.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.grapeWineGreenColor).setDensity(1120);
		grapeWineBooze[WineTypes.GREEN_WINE_POTENT.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.grapeWineGreenColor).setDensity(1120);
		grapeWineBooze[WineTypes.GREEN_WINE_EXTENDED.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.grapeWineGreenColor).setDensity(1120);
		grapeWineBooze[WineTypes.GREEN_AMBROSIA.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.ambrosiaGreenColor).setDensity(1120);
		grapeWineBooze[WineTypes.GREEN_PORTWINE.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.portWineGreenColor).setDensity(1120);
		grapeWineBooze[WineTypes.GREEN_WINE_INTOXICATED.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.grapeWineGreenColor).setDensity(1120);
		grapeWineBooze[WineTypes.GREEN_WINE_POISONED.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.grapeWineGreenColor).setDensity(1120);
		
		BoozeRegistryHelper.initializeBooze(grapeWineBooze, GrowthcraftGrapesBlocks.grapeWineFluidBlocks);
		BoozeRegistryHelper.setBoozeFoodStats(grapeWineBooze, 1, -0.3f);
		BoozeRegistryHelper.setBoozeFoodStats(grapeWineBooze[WineTypes.PURPLE_JUICE.ordinal()], 1, 0.3f);
		BoozeRegistryHelper.setBoozeFoodStats(grapeWineBooze[WineTypes.RED_JUICE.ordinal()], 1, 0.3f);
		BoozeRegistryHelper.setBoozeFoodStats(grapeWineBooze[WineTypes.GREEN_JUICE.ordinal()], 1, 0.3f);
		
/*		grapeWineBooze[WineTypes.PURPLE_AMBROSIA.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.ambrosiaPurpleColor);
		GrowthcraftGrapesBlocks.grapeWineFluidBlocks[WineTypes.PURPLE_AMBROSIA.ordinal()].getBlock().refreshColor();
		grapeWineBooze[WineTypes.RED_AMBROSIA.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.ambrosiaRedColor);
		GrowthcraftGrapesBlocks.grapeWineFluidBlocks[WineTypes.RED_AMBROSIA.ordinal()].getBlock().refreshColor();
		grapeWineBooze[WineTypes.GREEN_AMBROSIA.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.ambrosiaGreenColor);
		GrowthcraftGrapesBlocks.grapeWineFluidBlocks[WineTypes.GREEN_AMBROSIA.ordinal()].getBlock().refreshColor();
		
		grapeWineBooze[WineTypes.PURPLE_PORTWINE.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.portWinePurpleColor);
		GrowthcraftGrapesBlocks.grapeWineFluidBlocks[WineTypes.PURPLE_PORTWINE.ordinal()].getBlock().refreshColor();
		grapeWineBooze[WineTypes.RED_PORTWINE.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.portWineRedColor);
		GrowthcraftGrapesBlocks.grapeWineFluidBlocks[WineTypes.RED_PORTWINE.ordinal()].getBlock().refreshColor();
		grapeWineBooze[WineTypes.GREEN_PORTWINE.ordinal()].getFluid().setColor(GrowthcraftGrapesConfig.portWineGreenColor);
		GrowthcraftGrapesBlocks.grapeWineFluidBlocks[WineTypes.GREEN_PORTWINE.ordinal()].getBlock().refreshColor(); */
	}
	
	public static void register() {
		GrowthcraftGrapesItems.grapeWine.getItem().setBoozes(grapeWineBooze);
		BoozeRegistryHelper.registerBooze(grapeWineBooze, GrowthcraftGrapesBlocks.grapeWineFluidBlocks, GrowthcraftGrapesItems.grapeWine, Reference.MODID, "grapebooze", WineTypes.class);
		registerFermentations();
	}
	
	private static void registerFermentations() {
		final int fermentTime = GrowthcraftCellarConfig.fermentTime;
		final FluidStack[] fs = new FluidStack[grapeWineBooze.length];
		for (int i = 0; i < grapeWineBooze.length; ++i)
		{
			fs[i] = grapeWineBooze[i].asFluidStack();
		}
		
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[WineTypes.PURPLE_JUICE.ordinal()].getFluid())
			.tags(BoozeTag.YOUNG)
			.pressesFrom(
				GrapeTypes.PURPLE.asStack(),
				TickUtils.seconds(2),
				40,
				Residue.newDefault(0.3F));
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[WineTypes.RED_JUICE.ordinal()].getFluid())
			.tags(BoozeTag.YOUNG)
			.pressesFrom(
				GrapeTypes.RED.asStack(),
				TickUtils.seconds(2),
				40,
				Residue.newDefault(0.3F));
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[WineTypes.GREEN_JUICE.ordinal()].getFluid())
			.tags(BoozeTag.YOUNG)
			.pressesFrom(
				GrapeTypes.GREEN.asStack(),
				TickUtils.seconds(2),
				40,
				Residue.newDefault(0.3F));

		// Brewers Yeast, Nether Wart
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[WineTypes.PURPLE_WINE.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED)
			.fermentsFrom(fs[WineTypes.PURPLE_JUICE.ordinal()], new OreItemStacks("yeastBrewers"), fermentTime)
			.fermentsFrom(fs[WineTypes.PURPLE_JUICE.ordinal()], new ItemStack(Items.NETHER_WART), (int)(fermentTime * 0.66))
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.05f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[WineTypes.RED_WINE.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED)
			.fermentsFrom(fs[WineTypes.RED_JUICE.ordinal()], new OreItemStacks("yeastBrewers"), fermentTime)
			.fermentsFrom(fs[WineTypes.RED_JUICE.ordinal()], new ItemStack(Items.NETHER_WART), (int)(fermentTime * 0.66))
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.05f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[WineTypes.GREEN_WINE.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED)
			.fermentsFrom(fs[WineTypes.GREEN_JUICE.ordinal()], new OreItemStacks("yeastBrewers"), fermentTime)
			.fermentsFrom(fs[WineTypes.GREEN_JUICE.ordinal()], new ItemStack(Items.NETHER_WART), (int)(fermentTime * 0.66))
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.05f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);

		// Glowstone Dust
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[WineTypes.PURPLE_WINE_POTENT.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.POTENT)
			.fermentsFrom(fs[WineTypes.PURPLE_WINE.ordinal()], new OreItemStacks("dustGlowstone"), fermentTime)
			.fermentsFrom(fs[WineTypes.PURPLE_WINE_EXTENDED.ordinal()], new OreItemStacks("dustGlowstone"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.07f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[WineTypes.RED_WINE_POTENT.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.POTENT)
			.fermentsFrom(fs[WineTypes.RED_WINE.ordinal()], new OreItemStacks("dustGlowstone"), fermentTime)
			.fermentsFrom(fs[WineTypes.RED_WINE_EXTENDED.ordinal()], new OreItemStacks("dustGlowstone"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.07f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[WineTypes.GREEN_WINE_POTENT.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.POTENT)
			.fermentsFrom(fs[WineTypes.GREEN_WINE.ordinal()], new OreItemStacks("dustGlowstone"), fermentTime)
			.fermentsFrom(fs[WineTypes.GREEN_WINE_EXTENDED.ordinal()], new OreItemStacks("dustGlowstone"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.07f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);

		// Redstone Dust
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[WineTypes.PURPLE_WINE_EXTENDED.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.EXTENDED)
			.fermentsFrom(fs[WineTypes.PURPLE_WINE.ordinal()], new OreItemStacks("dustRedstone"), fermentTime)
			.fermentsFrom(fs[WineTypes.PURPLE_WINE_POTENT.ordinal()], new OreItemStacks("dustRedstone"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.05f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[WineTypes.RED_WINE_EXTENDED.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.EXTENDED)
			.fermentsFrom(fs[WineTypes.RED_WINE.ordinal()], new OreItemStacks("dustRedstone"), fermentTime)
			.fermentsFrom(fs[WineTypes.RED_WINE_POTENT.ordinal()], new OreItemStacks("dustRedstone"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.05f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[WineTypes.GREEN_WINE_EXTENDED.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.EXTENDED)
			.fermentsFrom(fs[WineTypes.GREEN_WINE.ordinal()], new OreItemStacks("dustRedstone"), fermentTime)
			.fermentsFrom(fs[WineTypes.GREEN_WINE_POTENT.ordinal()], new OreItemStacks("dustRedstone"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.05f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);

		// Ambrosia - Ethereal Yeast
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[WineTypes.PURPLE_AMBROSIA.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.HYPER_EXTENDED)
			.fermentsFrom(fs[WineTypes.PURPLE_WINE_POTENT.ordinal()], new OreItemStacks("yeastEthereal"), fermentTime)
			.fermentsFrom(fs[WineTypes.PURPLE_WINE_EXTENDED.ordinal()], new OreItemStacks("yeastEthereal"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.053f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.HEALTH_BOOST, TickUtils.minutes(3), 0)
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[WineTypes.RED_AMBROSIA.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.HYPER_EXTENDED)
			.fermentsFrom(fs[WineTypes.RED_WINE_POTENT.ordinal()], new OreItemStacks("yeastEthereal"), fermentTime)
			.fermentsFrom(fs[WineTypes.RED_WINE_EXTENDED.ordinal()], new OreItemStacks("yeastEthereal"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.053f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.HEALTH_BOOST, TickUtils.minutes(3), 0)
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[WineTypes.GREEN_AMBROSIA.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.HYPER_EXTENDED)
			.fermentsFrom(fs[WineTypes.GREEN_WINE_POTENT.ordinal()], new OreItemStacks("yeastEthereal"), fermentTime)
			.fermentsFrom(fs[WineTypes.GREEN_WINE_EXTENDED.ordinal()], new OreItemStacks("yeastEthereal"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.053f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.HEALTH_BOOST, TickUtils.minutes(3), 0)
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);
		
		// Port Wine - Bayanus Yeast
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[WineTypes.PURPLE_PORTWINE.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.FORTIFIED)
			.brewsFrom(
				new FluidStack(grapeWineBooze[WineTypes.PURPLE_WINE.ordinal()].getFluid(), GrowthcraftGrapesConfig.portWineBrewingYield),
				new OreItemStacks("yeastBayanus"),
				GrowthcraftGrapesConfig.portWineBrewingTime,
				Residue.newDefault(0.3F))
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.20f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 2);
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[WineTypes.RED_PORTWINE.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.FORTIFIED)
			.brewsFrom(
				new FluidStack(grapeWineBooze[WineTypes.RED_WINE.ordinal()].getFluid(), GrowthcraftGrapesConfig.portWineBrewingYield),
				new OreItemStacks("yeastBayanus"),
				GrowthcraftGrapesConfig.portWineBrewingTime,
				Residue.newDefault(0.3F))
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.20f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 2);		
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[WineTypes.GREEN_PORTWINE.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.FORTIFIED)
			.brewsFrom(
				new FluidStack(grapeWineBooze[WineTypes.GREEN_WINE.ordinal()].getFluid(), GrowthcraftGrapesConfig.portWineBrewingYield),
				new OreItemStacks("yeastBayanus"),
				GrowthcraftGrapesConfig.portWineBrewingTime,
				Residue.newDefault(0.3F))
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.20f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 2);		

		// Intoxicated Wine
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[WineTypes.PURPLE_WINE_INTOXICATED.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.INTOXICATED)
			.fermentsFrom(fs[WineTypes.PURPLE_WINE_POTENT.ordinal()], new OreItemStacks("yeastOrigin"), fermentTime)
			.fermentsFrom(fs[WineTypes.PURPLE_WINE_EXTENDED.ordinal()], new OreItemStacks("yeastOrigin"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.15f), TickUtils.seconds(90))
				.addEffect(new EffectWeightedRandomList()
					.add(8, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.RESISTANCE, TickUtils.minutes(3), 2)))
					.add(2, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.WEAKNESS, TickUtils.minutes(3), 2))));
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[WineTypes.RED_WINE_INTOXICATED.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.INTOXICATED)
			.fermentsFrom(fs[WineTypes.RED_WINE_POTENT.ordinal()], new OreItemStacks("yeastOrigin"), fermentTime)
			.fermentsFrom(fs[WineTypes.RED_WINE_EXTENDED.ordinal()], new OreItemStacks("yeastOrigin"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.15f), TickUtils.seconds(90))
				.addEffect(new EffectWeightedRandomList()
					.add(8, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.RESISTANCE, TickUtils.minutes(3), 2)))
					.add(2, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.WEAKNESS, TickUtils.minutes(3), 2))));
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[WineTypes.GREEN_WINE_INTOXICATED.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.INTOXICATED)
			.fermentsFrom(fs[WineTypes.GREEN_WINE_POTENT.ordinal()], new OreItemStacks("yeastOrigin"), fermentTime)
			.fermentsFrom(fs[WineTypes.GREEN_WINE_EXTENDED.ordinal()], new OreItemStacks("yeastOrigin"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.15f), TickUtils.seconds(90))
				.addEffect(new EffectWeightedRandomList()
					.add(8, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.RESISTANCE, TickUtils.minutes(3), 2)))
					.add(2, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.WEAKNESS, TickUtils.minutes(3), 2))));
		
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[WineTypes.PURPLE_WINE_POISONED.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.POISONED)
			.fermentsFrom(fs[WineTypes.PURPLE_WINE.ordinal()], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[WineTypes.PURPLE_WINE_POTENT.ordinal()], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[WineTypes.PURPLE_WINE_EXTENDED.ordinal()], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[WineTypes.PURPLE_AMBROSIA.ordinal()], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[WineTypes.PURPLE_PORTWINE.ordinal()], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[WineTypes.PURPLE_WINE_INTOXICATED.ordinal()], new OreItemStacks("yeastPoison"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.05f), TickUtils.seconds(90))
				.createPotionEntry(MobEffects.POISON, TickUtils.seconds(90), 0)
				.toggleDescription(!GrowthcraftCoreConfig.hidePoisonedBooze);
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[WineTypes.RED_WINE_POISONED.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.POISONED)
			.fermentsFrom(fs[WineTypes.RED_WINE.ordinal()], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[WineTypes.RED_WINE_POTENT.ordinal()], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[WineTypes.RED_WINE_EXTENDED.ordinal()], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[WineTypes.RED_AMBROSIA.ordinal()], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[WineTypes.RED_PORTWINE.ordinal()], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[WineTypes.RED_WINE_INTOXICATED.ordinal()], new OreItemStacks("yeastPoison"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.05f), TickUtils.seconds(90))
				.createPotionEntry(MobEffects.POISON, TickUtils.seconds(90), 0)
				.toggleDescription(!GrowthcraftCoreConfig.hidePoisonedBooze);
		GrowthcraftCellar.boozeBuilderFactory.create(grapeWineBooze[WineTypes.GREEN_WINE_POISONED.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.POISONED)
			.fermentsFrom(fs[WineTypes.GREEN_WINE.ordinal()], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[WineTypes.GREEN_WINE_POTENT.ordinal()], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[WineTypes.GREEN_WINE_EXTENDED.ordinal()], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[WineTypes.GREEN_AMBROSIA.ordinal()], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[WineTypes.GREEN_PORTWINE.ordinal()], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[WineTypes.GREEN_WINE_INTOXICATED.ordinal()], new OreItemStacks("yeastPoison"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.05f), TickUtils.seconds(90))
				.createPotionEntry(MobEffects.POISON, TickUtils.seconds(90), 0)
				.toggleDescription(!GrowthcraftCoreConfig.hidePoisonedBooze);
	}
	
	public static void registerRenders() {
		BoozeRegistryHelper.registerBoozeRenderers(grapeWineBooze, GrowthcraftGrapesBlocks.grapeWineFluidBlocks);
	}
}
