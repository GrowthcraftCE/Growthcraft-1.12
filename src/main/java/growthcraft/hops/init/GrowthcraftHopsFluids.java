package growthcraft.hops.init;

import growthcraft.cellar.GrowthcraftCellar;
import growthcraft.cellar.GrowthcraftCellarConfig;
import growthcraft.cellar.api.booze.BoozeTag;
import growthcraft.cellar.api.processing.common.Residue;
import growthcraft.cellar.common.definition.BlockBoozeDefinition;
import growthcraft.cellar.common.definition.BoozeDefinition;
import growthcraft.cellar.util.BoozeRegistryHelper;
import growthcraft.cellar.util.BoozeUtils;
import growthcraft.core.GrowthcraftCoreConfig;
import growthcraft.core.api.effect.EffectAddPotionEffect;
import growthcraft.core.api.effect.EffectWeightedRandomList;
import growthcraft.core.api.effect.SimplePotionEffectFactory;
import growthcraft.core.api.item.OreItemStacks;
import growthcraft.core.api.utils.TickUtils;
import growthcraft.hops.GrowthcraftHopsConfig;
import growthcraft.hops.handlers.EnumHandler.HopAleTypes;
import growthcraft.hops.handlers.EnumHandler.LagerTypes;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class GrowthcraftHopsFluids {
	public static BoozeDefinition[] hopAleBooze;
	public static BoozeDefinition[] lagerBooze;
	
	public static void init() {
		lagerBooze = new BoozeDefinition[LagerTypes.values().length];
		GrowthcraftHopsBlocks.lagerFluidBlocks = new BlockBoozeDefinition[lagerBooze.length];
		BoozeRegistryHelper.initializeAndRegisterBoozeFluids(lagerBooze, LagerTypes.class);
		for (BoozeDefinition booze : lagerBooze)
		{
			booze.getFluid().setColor(GrowthcraftHopsConfig.lagerColor).setDensity(1080);
		}
		BoozeRegistryHelper.initializeBooze(hopAleBooze, GrowthcraftHopsBlocks.lagerFluidBlocks);
		BoozeRegistryHelper.setBoozeFoodStats(hopAleBooze, 1, -0.6f);
		BoozeRegistryHelper.setBoozeFoodStats(hopAleBooze[0], 1, 0.3f);
		
		hopAleBooze = new BoozeDefinition[HopAleTypes.values().length];
		GrowthcraftHopsBlocks.hopAleFluidBlocks = new BlockBoozeDefinition[hopAleBooze.length];
		BoozeRegistryHelper.initializeAndRegisterBoozeFluids(hopAleBooze, HopAleTypes.class);
		for (BoozeDefinition booze : hopAleBooze)
		{
			booze.getFluid().setColor(GrowthcraftHopsConfig.hopAleColor).setDensity(1080);
		}
		BoozeRegistryHelper.initializeBooze(hopAleBooze, GrowthcraftHopsBlocks.hopAleFluidBlocks);
		BoozeRegistryHelper.setBoozeFoodStats(hopAleBooze, 1, -0.6f);
		BoozeRegistryHelper.setBoozeFoodStats(hopAleBooze[0], 1, 0.3f);
		BoozeRegistryHelper.setBoozeFoodStats(hopAleBooze[4], 1, 0.3f);
	}

	public static void register() {
		GrowthcraftHopsItems.hopAleBottle.getItem().setBoozes(hopAleBooze);
		BoozeRegistryHelper.registerBooze(hopAleBooze, GrowthcraftHopsBlocks.hopAleFluidBlocks, GrowthcraftHopsItems.hopAleBottle, "hopalebooze", HopAleTypes.class);
		GrowthcraftHopsItems.lagerBottle.getItem().setBoozes(lagerBooze);
		BoozeRegistryHelper.registerBooze(lagerBooze, GrowthcraftHopsBlocks.lagerFluidBlocks, GrowthcraftHopsItems.lagerBottle, "lagerbooze", LagerTypes.class);
		registerFermentations();
	}

	private static void registerLagerFermentations() {
		final int fermentTime = GrowthcraftCellarConfig.fermentTime;
		final FluidStack[] fs = new FluidStack[lagerBooze.length];
		for (int i = 0; i < lagerBooze.length; ++i)
		{
			fs[i] = lagerBooze[i].asFluidStack();
		}
		
		GrowthcraftCellar.boozeBuilderFactory.create(lagerBooze[0].getFluid())
		.tags(BoozeTag.YOUNG, BoozeTag.CHILLED)
		.brewsFrom(
			new FluidStack(hopAleBooze[4].getFluid(), 40),
			new OreItemStacks("yeastLager"),
			TickUtils.minutes(1),
			Residue.newDefault(0.0F));

		GrowthcraftCellar.boozeBuilderFactory.create(lagerBooze[1].getFluid())
			.tags(BoozeTag.FERMENTED, BoozeTag.CHILLED)
			.fermentsFrom(fs[0], new OreItemStacks("yeastBrewers"), fermentTime)
			.fermentsFrom(fs[0], new ItemStack(Items.NETHER_WART), (int)(fermentTime * 0.66))
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.0419f), TickUtils.seconds(45))
				.addPotionEntry(MobEffects.SPEED, TickUtils.seconds(90), 0)
				.addPotionEntry(MobEffects.HASTE, TickUtils.seconds(90), 0);
	
		GrowthcraftCellar.boozeBuilderFactory.create(lagerBooze[2].getFluid())
			.tags(BoozeTag.FERMENTED, BoozeTag.CHILLED, BoozeTag.POTENT)
			.fermentsFrom(fs[1], new OreItemStacks("dustGlowstone"), fermentTime)
			.fermentsFrom(fs[3], new OreItemStacks("dustGlowstone"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.0419f), TickUtils.seconds(45))
				.addPotionEntry(MobEffects.SPEED, TickUtils.seconds(90), 0)
				.addPotionEntry(MobEffects.HASTE, TickUtils.seconds(90), 0);
	
		GrowthcraftCellar.boozeBuilderFactory.create(lagerBooze[3].getFluid())
			.tags(BoozeTag.FERMENTED, BoozeTag.CHILLED, BoozeTag.EXTENDED)
			.fermentsFrom(fs[1], new OreItemStacks("dustRedstone"), fermentTime)
			.fermentsFrom(fs[2], new OreItemStacks("dustRedstone"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.0419f), TickUtils.seconds(45))
				.addPotionEntry(MobEffects.SPEED, TickUtils.seconds(90), 0)
				.addPotionEntry(MobEffects.HASTE, TickUtils.seconds(90), 0);
	
		GrowthcraftCellar.boozeBuilderFactory.create(lagerBooze[4].getFluid())
			.tags(BoozeTag.FERMENTED, BoozeTag.CHILLED, BoozeTag.HYPER_EXTENDED)
			.fermentsFrom(fs[2], new OreItemStacks("yeastEthereal"), fermentTime)
			.fermentsFrom(fs[3], new OreItemStacks("yeastEthereal"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.0419f), TickUtils.seconds(45))
				.addPotionEntry(MobEffects.SPEED, TickUtils.seconds(90), 0)
				.addPotionEntry(MobEffects.HASTE, TickUtils.seconds(90), 0);
	
		GrowthcraftCellar.boozeBuilderFactory.create(lagerBooze[5].getFluid())
			.tags(BoozeTag.FERMENTED, BoozeTag.CHILLED, BoozeTag.INTOXICATED)
			.fermentsFrom(fs[2], new OreItemStacks("yeastOrigin"), fermentTime)
			.fermentsFrom(fs[3], new OreItemStacks("yeastOrigin"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.091f), TickUtils.seconds(45))
				.addEffect(new EffectWeightedRandomList()
					.add(8, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.HASTE, TickUtils.minutes(3), 2)))
					.add(2, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.MINING_FATIGUE, TickUtils.minutes(3), 2))))
				.addEffect(new EffectWeightedRandomList()
					.add(8, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.SPEED, TickUtils.minutes(3), 2)))
					.add(2, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.SLOWNESS, TickUtils.minutes(3), 2))));
	
		GrowthcraftCellar.boozeBuilderFactory.create(lagerBooze[6].getFluid())
			.tags(BoozeTag.FERMENTED, BoozeTag.CHILLED, BoozeTag.POISONED)
			.fermentsFrom(fs[0], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[1], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[2], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[3], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[4], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[5], new OreItemStacks("yeastPoison"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.0419f), TickUtils.seconds(45))
				.createPotionEntry(MobEffects.POISON, TickUtils.seconds(90), 0).toggleDescription(!GrowthcraftCoreConfig.hidePoisonedBooze);
	}

	private static void registerHopAleFermentations() {
		final int fermentTime = GrowthcraftCellarConfig.fermentTime;
		final FluidStack[] fs = new FluidStack[hopAleBooze.length];
		for (int i = 0; i < hopAleBooze.length; ++i)
		{
			fs[i] = hopAleBooze[i].asFluidStack();
		}
		
		// Unhopped
		GrowthcraftCellar.boozeBuilderFactory.create(hopAleBooze[4].getFluid())
			.tags(BoozeTag.YOUNG)
			.brewsFrom(
				new FluidStack(FluidRegistry.WATER, 40),
				new OreItemStacks("cropWheat"),
				TickUtils.minutes(1),
				Residue.newDefault(0.3F));

		GrowthcraftCellar.boozeBuilderFactory.create(hopAleBooze[0].getFluid())
			.tags(BoozeTag.YOUNG, BoozeTag.HOPPED)
			.brewsFrom(
				new FluidStack(hopAleBooze[4].getFluid(), 40),
				new OreItemStacks("cropHops"),
				TickUtils.minutes(1),
				Residue.newDefault(0.0F));

		GrowthcraftCellar.boozeBuilderFactory.create(hopAleBooze[1].getFluid())
			.tags(BoozeTag.FERMENTED)
			.fermentsFrom(fs[0], new OreItemStacks("yeastBrewers"), fermentTime)
			.fermentsFrom(fs[0], new ItemStack(Items.NETHER_WART), (int)(fermentTime * 0.66))
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.10f), TickUtils.seconds(45))
				.addPotionEntry(MobEffects.HASTE, TickUtils.minutes(3), 0);

		// Glowstone
		GrowthcraftCellar.boozeBuilderFactory.create(hopAleBooze[2].getFluid())
			.tags(BoozeTag.HOPPED, BoozeTag.FERMENTED, BoozeTag.POTENT)
			.fermentsFrom(fs[1], new OreItemStacks("dustGlowstone"), fermentTime)
			.fermentsFrom(fs[3], new OreItemStacks("dustGlowstone"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.12f), TickUtils.seconds(45))
				.addPotionEntry(MobEffects.HASTE, TickUtils.minutes(3), 0);

		// Redstone
		GrowthcraftCellar.boozeBuilderFactory.create(hopAleBooze[3].getFluid())
			.tags(BoozeTag.HOPPED, BoozeTag.FERMENTED, BoozeTag.EXTENDED)
			.fermentsFrom(fs[1], new OreItemStacks("dustRedstone"), fermentTime)
			.fermentsFrom(fs[2], new OreItemStacks("dustRedstone"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.10f), TickUtils.seconds(45))
				.addPotionEntry(MobEffects.HASTE, TickUtils.minutes(3), 0);

		// Ethereal Yeast
		GrowthcraftCellar.boozeBuilderFactory.create(hopAleBooze[5].getFluid())
			.tags(BoozeTag.HOPPED, BoozeTag.FERMENTED, BoozeTag.HYPER_EXTENDED)
			.fermentsFrom(fs[2], new OreItemStacks("yeastEthereal"), fermentTime)
			.fermentsFrom(fs[3], new OreItemStacks("yeastEthereal"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.10f), TickUtils.seconds(45))
				.addPotionEntry(MobEffects.HASTE, TickUtils.minutes(3), 0);

		// Lager Yeast
		GrowthcraftCellar.boozeBuilderFactory.create(hopAleBooze[6].getFluid())
			.tags(BoozeTag.HOPPED, BoozeTag.FERMENTED, BoozeTag.CHILLED)
			.fermentsFrom(fs[5], new OreItemStacks("yeastLager"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.071f), TickUtils.seconds(45))
				.addPotionEntry(MobEffects.HASTE, TickUtils.minutes(10), 2);

		// Intoxicated - Origin
		GrowthcraftCellar.boozeBuilderFactory.create(hopAleBooze[7].getFluid())
			.tags(BoozeTag.HOPPED, BoozeTag.FERMENTED, BoozeTag.INTOXICATED)
			.fermentsFrom(fs[2], new OreItemStacks("yeastOrigin"), fermentTime)
			.fermentsFrom(fs[3], new OreItemStacks("yeastOrigin"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.20f), TickUtils.seconds(45))
				.addEffect(new EffectWeightedRandomList()
					.add(8, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.HASTE, TickUtils.minutes(3), 2)))
					.add(2, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.MINING_FATIGUE, TickUtils.minutes(3), 2))));

		// Poisoned - Netherrash
		// Regardless of what you brewed it with, it will kill the hops in the
		// booze and poison it.
		GrowthcraftCellar.boozeBuilderFactory.create(hopAleBooze[8].getFluid())
			.tags(BoozeTag.FERMENTED, BoozeTag.POISONED)
			.fermentsFrom(fs[1], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[2], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[3], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[5], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[6], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[7], new OreItemStacks("yeastPoison"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.10f), TickUtils.seconds(45))
				.createPotionEntry(MobEffects.POISON, TickUtils.seconds(90), 0).toggleDescription(!GrowthcraftCoreConfig.hidePoisonedBooze);
	}
	
	private static void registerFermentations()
	{
		registerHopAleFermentations();
		registerLagerFermentations();
	}

	public static void registerRenders() {
		BoozeRegistryHelper.registerBoozeRenderers(lagerBooze, GrowthcraftHopsBlocks.lagerFluidBlocks);
		BoozeRegistryHelper.registerBoozeRenderers(hopAleBooze, GrowthcraftHopsBlocks.hopAleFluidBlocks);
	}	
}
