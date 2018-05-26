package growthcraft.grapes.common;

import static growthcraft.core.shared.GrowthcraftCoreApis.tabGrowthcraft;
import static growthcraft.grapes.shared.init.GrowthcraftGrapesFluids.grapeWineBooze;

import growthcraft.cellar.shared.GrowthcraftCellarApis;
import growthcraft.cellar.shared.booze.BoozeRegistryHelper;
import growthcraft.cellar.shared.booze.BoozeTag;
import growthcraft.cellar.shared.booze.BoozeUtils;
import growthcraft.cellar.shared.config.GrowthcraftCellarConfig;
import growthcraft.cellar.shared.definition.BlockBoozeDefinition;
import growthcraft.cellar.shared.definition.BoozeDefinition;
import growthcraft.cellar.shared.item.ItemBoozeBottle;
import growthcraft.cellar.shared.processing.common.Residue;
import growthcraft.core.shared.client.render.utils.ItemRenderUtils;
import growthcraft.core.shared.config.GrowthcraftCoreConfig;
import growthcraft.core.shared.definition.BlockDefinition;
import growthcraft.core.shared.definition.ItemDefinition;
import growthcraft.core.shared.definition.ItemTypeDefinition;
import growthcraft.core.shared.effect.EffectAddPotionEffect;
import growthcraft.core.shared.effect.EffectWeightedRandomList;
import growthcraft.core.shared.effect.SimplePotionEffectFactory;
import growthcraft.core.shared.item.OreItemStacks;
import growthcraft.core.shared.utils.LootUtils;
import growthcraft.core.shared.utils.TickUtils;
import growthcraft.grapes.common.blocks.BlockGrapeFruit;
import growthcraft.grapes.common.blocks.BlockGrapeLeaves;
import growthcraft.grapes.common.blocks.BlockGrapeVine0;
import growthcraft.grapes.common.blocks.BlockGrapeVine1;
import growthcraft.grapes.common.items.ItemGrape;
import growthcraft.grapes.common.items.ItemGrapeSeed;
import growthcraft.grapes.shared.Reference;
import growthcraft.grapes.shared.config.GrowthcraftGrapesConfig;
import growthcraft.grapes.shared.init.GrowthcraftGrapesBlocks;
import growthcraft.grapes.shared.init.GrowthcraftGrapesItems;
import growthcraft.grapes.shared.init.GrowthcraftGrapesItems.GrapeTypes;
import growthcraft.grapes.shared.init.GrowthcraftGrapesItems.WineTypes;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class Init {
	private Init() {}
	
	////////////
	// Blocks
	////////////
	
    public static void preInitBlocks() {
    	
		GrowthcraftGrapesBlocks.grapeFruit  = new BlockDefinition(new BlockGrapeFruit(GrapeTypes.values()));
		GrowthcraftGrapesBlocks.grapeLeaves = new BlockDefinition(new BlockGrapeLeaves(GrapeTypes.values(), (BlockGrapeFruit)GrowthcraftGrapesBlocks.grapeFruit.getBlock()));
		GrowthcraftGrapesBlocks.grapeVine1  = new BlockDefinition(new BlockGrapeVine1((BlockGrapeLeaves)GrowthcraftGrapesBlocks.grapeLeaves.getBlock()));
		GrowthcraftGrapesBlocks.grapeVine0  = new BlockDefinition(new BlockGrapeVine0(GrapeTypes.values(), (BlockGrapeVine1)GrowthcraftGrapesBlocks.grapeVine1.getBlock()));
    	
		GrowthcraftGrapesBlocks.grapeWineFluidBlocks = null; // Is initialized in GrowthcraftGrapesFluids
    }

    public static void registerBlocks(IForgeRegistry<Block> registry) {
    	GrowthcraftGrapesBlocks.grapeVine0.registerBlock(registry, new ResourceLocation(Reference.MODID, "native_grape_vine0"));
    	GrowthcraftGrapesBlocks.grapeVine1.registerBlock(registry, new ResourceLocation(Reference.MODID, "native_grape_vine1"));
    	GrowthcraftGrapesBlocks.grapeLeaves.registerBlock(registry, new ResourceLocation(Reference.MODID, "native_grape_vine_leaves"));
    	GrowthcraftGrapesBlocks.grapeFruit.registerBlock(registry, new ResourceLocation(Reference.MODID, "native_grape_fruit"));
    }

    @SideOnly(Side.CLIENT)
    public static void registerBlockRenders() {

    }
    
	////////////
    // Items
	////////////
    
    public static void preInitItems() {
    	GrowthcraftGrapesItems.grape = new ItemDefinition( new ItemGrape("grape", 1, 1, false) );
    	GrowthcraftGrapesItems.grape_seed = new ItemDefinition( new ItemGrapeSeed("grape_seed") );
    	GrowthcraftGrapesItems.grapeWine = new ItemTypeDefinition<ItemBoozeBottle>( new ItemBoozeBottle() );
    }

	public static void registerItemOres()
	{
		OreDictionary.registerOre("foodFruit", GrapeTypes.GREEN.asStack());
		OreDictionary.registerOre("foodFruit", GrapeTypes.PURPLE.asStack());
		OreDictionary.registerOre("foodFruit", GrapeTypes.RED.asStack());
		OreDictionary.registerOre("foodGrapesPurple", GrapeTypes.PURPLE.asStack());
		OreDictionary.registerOre("foodGrapesGreen", GrapeTypes.GREEN.asStack());
		OreDictionary.registerOre("foodGrapesRed", GrapeTypes.RED.asStack());
		OreDictionary.registerOre("foodGrapejuice", GrowthcraftGrapesItems.grapeWine.asStack(1, 0));
	}
	
    public static void registerItems(IForgeRegistry<Item> registry) {
    	GrowthcraftGrapesItems.grape.getItem().setCreativeTab(tabGrowthcraft);
    	GrowthcraftGrapesItems.grape.registerItem(registry);
    	GrowthcraftGrapesItems.grape_seed.getItem().setCreativeTab(tabGrowthcraft);
    	GrowthcraftGrapesItems.grape_seed.registerItem(registry);
    	
    	GrowthcraftGrapesItems.grapeWine.registerItem(registry, new ResourceLocation(Reference.MODID, "grapewine"));
    	GrowthcraftGrapesItems.grapeWine.getItem().setCreativeTab(tabGrowthcraft);
    	GrowthcraftGrapesItems.grapeWine.getItem().setBoozes(grapeWineBooze);
    }

    @SideOnly(Side.CLIENT)
	public static void registerItemRenders() {
    	GrowthcraftGrapesItems.grape_seed.registerRenders(GrapeTypes.class);
    	GrowthcraftGrapesItems.grape.registerRenders(GrapeTypes.class);
    	GrowthcraftGrapesItems.grapeWine.registerRenders(WineTypes.class);
    }
    
    @SideOnly(Side.CLIENT)
	public static void registerItemColorHandlers() {
		ItemRenderUtils.registerItemColorHandler(GrowthcraftGrapesItems.grapeWine.getItem());
	}

    @SideOnly(Side.CLIENT)
	public static void registerItemVariants() {
    	GrowthcraftGrapesItems.grape_seed.registerModelBakeryVariants(GrapeTypes.class);
    	GrowthcraftGrapesItems.grape.registerModelBakeryVariants(GrapeTypes.class);
    	GrowthcraftGrapesItems.grapeWine.registerModelBakeryVariants(WineTypes.class);
	}

    
	////////////
	// Fluids
	////////////

	public static void preInitFluids() {
		grapeWineBooze = new BoozeDefinition[WineTypes.values().length];
		BoozeRegistryHelper.initializeAndRegisterBoozeFluids(grapeWineBooze, WineTypes.class, "");

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
	}
	
	public static void registerFluidBlocks(IForgeRegistry<Block> registry) {
		GrowthcraftGrapesBlocks.grapeWineFluidBlocks = new BlockBoozeDefinition[grapeWineBooze.length];
		BoozeRegistryHelper.initializeBooze(grapeWineBooze, GrowthcraftGrapesBlocks.grapeWineFluidBlocks);
		BoozeRegistryHelper.setBoozeFoodStats(grapeWineBooze, 1, -0.3f);
		BoozeRegistryHelper.setBoozeFoodStats(grapeWineBooze[WineTypes.PURPLE_JUICE.ordinal()], 1, 0.3f);
		BoozeRegistryHelper.setBoozeFoodStats(grapeWineBooze[WineTypes.RED_JUICE.ordinal()], 1, 0.3f);
		BoozeRegistryHelper.setBoozeFoodStats(grapeWineBooze[WineTypes.GREEN_JUICE.ordinal()], 1, 0.3f);
		
		BoozeRegistryHelper.registerBoozeBlocks(registry, grapeWineBooze, GrowthcraftGrapesBlocks.grapeWineFluidBlocks, Reference.MODID, "grapebooze", WineTypes.class);
	}
	
	public static void initBoozes() {
		BoozeRegistryHelper.initBoozeContainers(grapeWineBooze, GrowthcraftGrapesItems.grapeWine, Reference.MODID, "grapebooze", WineTypes.class);
		registerFermentations();
	}

    @SideOnly(Side.CLIENT)
	public static void registerFluidRenders() {
		BoozeRegistryHelper.registerBoozeRenderers(grapeWineBooze, GrowthcraftGrapesBlocks.grapeWineFluidBlocks);
	}
	
	private static void registerFermentations() {
		final int fermentTime = GrowthcraftCellarConfig.fermentTime;
		final FluidStack[] fs = new FluidStack[grapeWineBooze.length];
		for (int i = 0; i < grapeWineBooze.length; ++i)
		{
			fs[i] = grapeWineBooze[i].asFluidStack();
		}
		
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.PURPLE_JUICE.ordinal()].getFluid())
			.tags(BoozeTag.YOUNG)
			.pressesFrom(
				GrapeTypes.PURPLE.asStack(),
				TickUtils.seconds(2),
				40,
				Residue.newDefault(0.3F));
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.RED_JUICE.ordinal()].getFluid())
			.tags(BoozeTag.YOUNG)
			.pressesFrom(
				GrapeTypes.RED.asStack(),
				TickUtils.seconds(2),
				40,
				Residue.newDefault(0.3F));
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.GREEN_JUICE.ordinal()].getFluid())
			.tags(BoozeTag.YOUNG)
			.pressesFrom(
				GrapeTypes.GREEN.asStack(),
				TickUtils.seconds(2),
				40,
				Residue.newDefault(0.3F));

		// Brewers Yeast, Nether Wart
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.PURPLE_WINE.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED)
			.fermentsFrom(fs[WineTypes.PURPLE_JUICE.ordinal()], new OreItemStacks("yeastBrewers"), fermentTime)
			.fermentsFrom(fs[WineTypes.PURPLE_JUICE.ordinal()], new ItemStack(Items.NETHER_WART), (int)(fermentTime * 0.66))
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.05f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.RED_WINE.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED)
			.fermentsFrom(fs[WineTypes.RED_JUICE.ordinal()], new OreItemStacks("yeastBrewers"), fermentTime)
			.fermentsFrom(fs[WineTypes.RED_JUICE.ordinal()], new ItemStack(Items.NETHER_WART), (int)(fermentTime * 0.66))
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.05f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.GREEN_WINE.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED)
			.fermentsFrom(fs[WineTypes.GREEN_JUICE.ordinal()], new OreItemStacks("yeastBrewers"), fermentTime)
			.fermentsFrom(fs[WineTypes.GREEN_JUICE.ordinal()], new ItemStack(Items.NETHER_WART), (int)(fermentTime * 0.66))
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.05f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);

		// Glowstone Dust
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.PURPLE_WINE_POTENT.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.POTENT)
			.fermentsFrom(fs[WineTypes.PURPLE_WINE.ordinal()], new OreItemStacks("dustGlowstone"), fermentTime)
			.fermentsFrom(fs[WineTypes.PURPLE_WINE_EXTENDED.ordinal()], new OreItemStacks("dustGlowstone"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.07f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.RED_WINE_POTENT.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.POTENT)
			.fermentsFrom(fs[WineTypes.RED_WINE.ordinal()], new OreItemStacks("dustGlowstone"), fermentTime)
			.fermentsFrom(fs[WineTypes.RED_WINE_EXTENDED.ordinal()], new OreItemStacks("dustGlowstone"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.07f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.GREEN_WINE_POTENT.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.POTENT)
			.fermentsFrom(fs[WineTypes.GREEN_WINE.ordinal()], new OreItemStacks("dustGlowstone"), fermentTime)
			.fermentsFrom(fs[WineTypes.GREEN_WINE_EXTENDED.ordinal()], new OreItemStacks("dustGlowstone"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.07f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);

		// Redstone Dust
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.PURPLE_WINE_EXTENDED.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.EXTENDED)
			.fermentsFrom(fs[WineTypes.PURPLE_WINE.ordinal()], new OreItemStacks("dustRedstone"), fermentTime)
			.fermentsFrom(fs[WineTypes.PURPLE_WINE_POTENT.ordinal()], new OreItemStacks("dustRedstone"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.05f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.RED_WINE_EXTENDED.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.EXTENDED)
			.fermentsFrom(fs[WineTypes.RED_WINE.ordinal()], new OreItemStacks("dustRedstone"), fermentTime)
			.fermentsFrom(fs[WineTypes.RED_WINE_POTENT.ordinal()], new OreItemStacks("dustRedstone"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.05f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.GREEN_WINE_EXTENDED.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.EXTENDED)
			.fermentsFrom(fs[WineTypes.GREEN_WINE.ordinal()], new OreItemStacks("dustRedstone"), fermentTime)
			.fermentsFrom(fs[WineTypes.GREEN_WINE_POTENT.ordinal()], new OreItemStacks("dustRedstone"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.05f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);

		// Ambrosia - Ethereal Yeast
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.PURPLE_AMBROSIA.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.HYPER_EXTENDED)
			.fermentsFrom(fs[WineTypes.PURPLE_WINE_POTENT.ordinal()], new OreItemStacks("yeastEthereal"), fermentTime)
			.fermentsFrom(fs[WineTypes.PURPLE_WINE_EXTENDED.ordinal()], new OreItemStacks("yeastEthereal"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.053f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.HEALTH_BOOST, TickUtils.minutes(3), 0)
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.RED_AMBROSIA.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.HYPER_EXTENDED)
			.fermentsFrom(fs[WineTypes.RED_WINE_POTENT.ordinal()], new OreItemStacks("yeastEthereal"), fermentTime)
			.fermentsFrom(fs[WineTypes.RED_WINE_EXTENDED.ordinal()], new OreItemStacks("yeastEthereal"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.053f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.HEALTH_BOOST, TickUtils.minutes(3), 0)
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.GREEN_AMBROSIA.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.HYPER_EXTENDED)
			.fermentsFrom(fs[WineTypes.GREEN_WINE_POTENT.ordinal()], new OreItemStacks("yeastEthereal"), fermentTime)
			.fermentsFrom(fs[WineTypes.GREEN_WINE_EXTENDED.ordinal()], new OreItemStacks("yeastEthereal"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.053f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.HEALTH_BOOST, TickUtils.minutes(3), 0)
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);
		
		// Port Wine - Bayanus Yeast
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.PURPLE_PORTWINE.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.FORTIFIED)
			.brewsFrom(
				new FluidStack(grapeWineBooze[WineTypes.PURPLE_WINE.ordinal()].getFluid(), GrowthcraftGrapesConfig.portWineBrewingYield),
				new OreItemStacks("yeastBayanus"),
				GrowthcraftGrapesConfig.portWineBrewingTime,
				Residue.newDefault(0.3F))
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.20f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 2);
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.RED_PORTWINE.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.FORTIFIED)
			.brewsFrom(
				new FluidStack(grapeWineBooze[WineTypes.RED_WINE.ordinal()].getFluid(), GrowthcraftGrapesConfig.portWineBrewingYield),
				new OreItemStacks("yeastBayanus"),
				GrowthcraftGrapesConfig.portWineBrewingTime,
				Residue.newDefault(0.3F))
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.20f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 2);		
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.GREEN_PORTWINE.ordinal()].getFluid())
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
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.PURPLE_WINE_INTOXICATED.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.INTOXICATED)
			.fermentsFrom(fs[WineTypes.PURPLE_WINE_POTENT.ordinal()], new OreItemStacks("yeastOrigin"), fermentTime)
			.fermentsFrom(fs[WineTypes.PURPLE_WINE_EXTENDED.ordinal()], new OreItemStacks("yeastOrigin"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.15f), TickUtils.seconds(90))
				.addEffect(new EffectWeightedRandomList()
					.add(8, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.RESISTANCE, TickUtils.minutes(3), 2)))
					.add(2, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.WEAKNESS, TickUtils.minutes(3), 2))));
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.RED_WINE_INTOXICATED.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.INTOXICATED)
			.fermentsFrom(fs[WineTypes.RED_WINE_POTENT.ordinal()], new OreItemStacks("yeastOrigin"), fermentTime)
			.fermentsFrom(fs[WineTypes.RED_WINE_EXTENDED.ordinal()], new OreItemStacks("yeastOrigin"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.15f), TickUtils.seconds(90))
				.addEffect(new EffectWeightedRandomList()
					.add(8, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.RESISTANCE, TickUtils.minutes(3), 2)))
					.add(2, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.WEAKNESS, TickUtils.minutes(3), 2))));
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.GREEN_WINE_INTOXICATED.ordinal()].getFluid())
			.tags(BoozeTag.WINE, BoozeTag.FERMENTED, BoozeTag.INTOXICATED)
			.fermentsFrom(fs[WineTypes.GREEN_WINE_POTENT.ordinal()], new OreItemStacks("yeastOrigin"), fermentTime)
			.fermentsFrom(fs[WineTypes.GREEN_WINE_EXTENDED.ordinal()], new OreItemStacks("yeastOrigin"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.15f), TickUtils.seconds(90))
				.addEffect(new EffectWeightedRandomList()
					.add(8, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.RESISTANCE, TickUtils.minutes(3), 2)))
					.add(2, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.WEAKNESS, TickUtils.minutes(3), 2))));
		
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.PURPLE_WINE_POISONED.ordinal()].getFluid())
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
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.RED_WINE_POISONED.ordinal()].getFluid())
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
		GrowthcraftCellarApis.boozeBuilderFactory.create(grapeWineBooze[WineTypes.GREEN_WINE_POISONED.ordinal()].getFluid())
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

	////////////
	// Recipes
	////////////
	
	public static void initRecipes() {
		
	}

	public static void registerRecipes() {
		registerCraftingRecipes();
	}
	
	private static void registerCraftingRecipes() {
		// TODO: RECIPE_REGISTER!
		
/*		GameRegistry.addShapelessRecipe(GrapeTypes.PURPLE.asSeedsStack(1), GrapeTypes.PURPLE.asStack(1) );
		GameRegistry.addShapelessRecipe(GrapeTypes.GREEN.asSeedsStack(1), GrapeTypes.GREEN.asStack(1) );
		GameRegistry.addShapelessRecipe(GrapeTypes.RED.asSeedsStack(1), GrapeTypes.RED.asStack(1) );
		*/
	}
	
	// Loot
	////////

	public static void lootLoad(LootTableLoadEvent evt) {
		boolean isSimpleDungeon = evt.getName().toString().equals("minecraft:chests/simple_dungeon");
		boolean isMineshaft = evt.getName().toString().equals("minecraft:chests/abandoned_mineshaft");
		
	    if (isMineshaft || isSimpleDungeon) {
	    	LootPool pool = LootUtils.getOrCreateLootPool(evt.getTable(), "growthcraft");
	    	LootUtils.addLootEntry(pool, GrowthcraftGrapesItems.GrapeTypes.PURPLE.asSeedsStack(1), 1, 5, isSimpleDungeon ? 3 : 10);
	    	LootUtils.addLootEntry(pool, GrowthcraftGrapesItems.GrapeTypes.RED.asSeedsStack(1), 1, 5, isSimpleDungeon ? 2 : 5);
	    	LootUtils.addLootEntry(pool, GrowthcraftGrapesItems.GrapeTypes.GREEN.asSeedsStack(1), 1, 5, isSimpleDungeon ? 1 : 3);
	    }
	}
}
