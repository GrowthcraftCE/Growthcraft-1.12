package growthcraft.bees.common;

import growthcraft.bees.GrowthcraftBees;
import growthcraft.bees.common.block.BlockBeeBox;
import growthcraft.bees.common.fluids.FluidHoney;
import growthcraft.bees.common.items.*;
import growthcraft.bees.common.lib.config.BeesFluidTag;
import growthcraft.bees.common.lib.config.BeesRegistry;
import growthcraft.bees.common.tileentity.TileEntityBeeBox;
import growthcraft.bees.shared.Reference;
import growthcraft.bees.shared.config.GrowthcraftBeesConfig;
import growthcraft.bees.shared.init.GrowthcraftBeesBlocks;
import growthcraft.bees.shared.init.GrowthcraftBeesFluids;
import growthcraft.bees.shared.init.GrowthcraftBeesItems;
import growthcraft.bees.shared.init.GrowthcraftBeesItems.BeesWaxTypes;
import growthcraft.bees.shared.init.GrowthcraftBeesItems.MeadTypes;
import growthcraft.core.shared.CoreRegistry;
import growthcraft.core.shared.GrowthcraftCoreApis;
import growthcraft.core.shared.client.render.utils.ItemRenderUtils;
import growthcraft.core.shared.config.GrowthcraftCoreConfig;
import growthcraft.core.shared.definition.BlockDefinition;
import growthcraft.core.shared.definition.ItemDefinition;
import growthcraft.cellar.shared.GrowthcraftCellarApis;
import growthcraft.cellar.shared.booze.BoozeRegistryHelper;
import growthcraft.cellar.shared.booze.BoozeTag;
import growthcraft.cellar.shared.booze.BoozeUtils;
import growthcraft.cellar.shared.config.GrowthcraftCellarConfig;
import growthcraft.cellar.shared.definition.BlockBoozeDefinition;
import growthcraft.cellar.shared.definition.BoozeDefinition;
import growthcraft.cellar.shared.item.ItemBoozeBottle;
import growthcraft.core.shared.fluids.FluidFactory;
import growthcraft.core.shared.fluids.FluidFactory.FluidDetailsBuilder;
import growthcraft.core.shared.fluids.TaggedFluidStacks;
import growthcraft.core.shared.item.ItemFoodBottleFluid;
import growthcraft.core.shared.item.OreItemStacks;
import growthcraft.core.shared.item.recipes.ShapelessMultiRecipe;
import growthcraft.core.shared.legacy.FluidContainerRegistry;
import growthcraft.core.shared.utils.LootUtils;
import growthcraft.core.shared.utils.TickUtils;
import growthcraft.core.shared.definition.ItemTypeDefinition;
import growthcraft.core.shared.effect.EffectAddPotionEffect;
import growthcraft.core.shared.effect.EffectWeightedRandomList;
import growthcraft.core.shared.effect.SimplePotionEffectFactory;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

import static growthcraft.core.shared.GrowthcraftCoreApis.tabGrowthcraft;
import static growthcraft.bees.shared.init.GrowthcraftBeesFluids.meadBooze;
import static growthcraft.bees.shared.init.GrowthcraftBeesBlocks.meadBoozeFluidBlocks;

public class Init {
	private Init() {}

	////////
	// Blocks
	////////
	
	public static void preInitBlocks() {
		GrowthcraftBeesBlocks.beeBox = new BlockDefinition(new BlockBeeBox("beebox"));
	}
	
	public static void registerBlocks(IForgeRegistry<Block> registry) {
		GrowthcraftBeesBlocks.beeBox.getBlock().setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft);
		GrowthcraftBeesBlocks.beeBox.registerBlock(registry);
	}
	
	public static void registerBlockItems(IForgeRegistry<Item> registry) {
		GrowthcraftBeesBlocks.beeBox.registerBlockItem(registry);
	}
	
	public static void registerBlockRender() {
		GrowthcraftBeesBlocks.beeBox.registerItemRender();
	}

	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityBeeBox.class, Reference.MODID + ":bee_box");
	}
	
	////////
	// Items
	////////
	
	public static void preInitItems() {
		GrowthcraftBeesItems.honeyCombEmpty = new ItemDefinition(new ItemHoneyCombEmpty("honey_comb_empty"));
		GrowthcraftBeesItems.honeyCombFilled = new ItemDefinition(new ItemHoneyCombFilled("honey_comb_filled"));
		GrowthcraftBeesItems.honeyJar = new ItemDefinition(new ItemHoneyJar("honey_jar"));
		GrowthcraftBeesItems.bee = new ItemDefinition( new ItemBee("bee") );
		GrowthcraftBeesItems.beesWax = new ItemDefinition( new ItemBeesWax("bees_wax") );
		GrowthcraftBeesItems.honeyMeadBottle = new ItemTypeDefinition<ItemBoozeBottle>( new ItemBoozeBottle() );
	}
	
	public static void registerItemOres() {
		OreDictionary.registerOre("materialWax", GrowthcraftBeesItems.beesWax.getItem());
//		OreDictionary.registerOre("materialPressedwax", GrowthcraftBeesItems.beesWax.getItem());
		OreDictionary.registerOre("materialBeeswax", GrowthcraftBeesItems.beesWax.getItem());
		OreDictionary.registerOre("materialBeeswaxBlack", BeesWaxTypes.BLACK.asStack());
		OreDictionary.registerOre("materialBeeswaxRed", BeesWaxTypes.RED.asStack());
//		OreDictionary.registerOre("beeQueen", GrowthcraftBeesItems.bee.getItem());
		OreDictionary.registerOre("materialWaxcomb", GrowthcraftBeesItems.honeyCombEmpty.getItem());
		OreDictionary.registerOre("beecomb", GrowthcraftBeesItems.honeyCombEmpty.getItem());
		OreDictionary.registerOre("materialHoneycomb", GrowthcraftBeesItems.honeyCombFilled.getItem());
		OreDictionary.registerOre("beecomb", GrowthcraftBeesItems.honeyCombFilled.getItem());
		OreDictionary.registerOre("honeyDrop", GrowthcraftBeesItems.honeyJar.getItem());
		OreDictionary.registerOre("dropHoney", GrowthcraftBeesItems.honeyJar.getItem());
		OreDictionary.registerOre("jarHoney", GrowthcraftBeesItems.honeyJar.getItem());
//		OreDictionary.registerOre("bucketHoney", GrowthcraftBeesItems.honeyJar.getItem());
	}

	public static void registerItems(IForgeRegistry<Item> registry) {
		GrowthcraftBeesItems.honeyCombEmpty.getItem().setCreativeTab(tabGrowthcraft);
		GrowthcraftBeesItems.honeyCombEmpty.registerItem(registry);
		GrowthcraftBeesItems.honeyCombFilled.getItem().setCreativeTab(tabGrowthcraft);
		GrowthcraftBeesItems.honeyCombFilled.registerItem(registry);
		GrowthcraftBeesItems.honeyJar.getItem().setCreativeTab(tabGrowthcraft);
		GrowthcraftBeesItems.honeyJar.registerItem(registry);
		GrowthcraftBeesItems.bee.getItem().setCreativeTab(tabGrowthcraft);
		GrowthcraftBeesItems.bee.registerItem(registry);
		GrowthcraftBeesItems.beesWax.getItem().setCreativeTab(tabGrowthcraft);
		GrowthcraftBeesItems.beesWax.registerItem(registry);
		GrowthcraftBeesItems.honeyMeadBottle.registerItem(registry, new ResourceLocation(Reference.MODID, "mead"));
		GrowthcraftBeesItems.honeyMeadBottle.getItem().setCreativeTab(tabGrowthcraft);
		GrowthcraftBeesItems.honeyMeadBottle.getItem().setBoozes(meadBooze);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerItemRenders() {
		GrowthcraftBeesItems.honeyCombEmpty.registerRender();
		GrowthcraftBeesItems.honeyCombFilled.registerRender();
		GrowthcraftBeesItems.honeyJar.registerRender();
		GrowthcraftBeesItems.bee.registerRender();
		GrowthcraftBeesItems.beesWax.registerRenders( BeesWaxTypes.class );
		GrowthcraftBeesItems.honeyMeadBottle.registerRenders( MeadTypes.class );
	}
	
    @SideOnly(Side.CLIENT)
	public static void registerItemColorHandlers() {
    	ItemRenderUtils.registerItemColorHandler(GrowthcraftBeesItems.honeyMeadBottle.getItem());
    }
	
	@SideOnly(Side.CLIENT)
	public static void registerItemVariants() {
		GrowthcraftBeesItems.beesWax.registerModelBakeryVariants( BeesWaxTypes.class );
		GrowthcraftBeesItems.honeyMeadBottle.registerModelBakeryVariants( MeadTypes.class );
	}
	
	////////
	// Fluids
	////////
	
	public static void preInitFluids() {
		if (GrowthcraftBeesConfig.honeyEnabled) {
			FluidHoney fluidHoney = new FluidHoney("fluid_honey");
			ItemFoodBottleFluid foodBottleHoney = new ItemFoodBottleFluid(fluidHoney, 2, 0.2f, false);
			GrowthcraftBeesFluids.honey = new FluidDetailsBuilder(fluidHoney, FluidFactory.FEATURE_ALL_EDIBLE)
					.setFoodBottle(foodBottleHoney).build()
						.setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft); //.setItemColor(0xFFAC01);
			GrowthcraftBeesFluids.honey.refreshItemColor();
		}
		
		meadBooze = new BoozeDefinition[MeadTypes.values().length];
		BoozeRegistryHelper.initializeAndRegisterBoozeFluids(GrowthcraftBeesFluids.meadBooze, MeadTypes.class, "");
		
		// 0 = young
		// 1 = fermented
		// 2 = fermented, potent
		// 3 = fermented, extended
		// 4 = ethereal
		// 5 = intoxicated
		// 6 = poisoned
		meadBooze[MeadTypes.MEAD_YOUNG.ordinal()].getFluid().setColor(GrowthcraftBeesConfig.honeyMeadColor).setDensity(1000).setViscosity(1200);
		meadBooze[MeadTypes.MEAD_FERMENTED.ordinal()].getFluid().setColor(GrowthcraftBeesConfig.honeyMeadColor).setDensity(1000).setViscosity(1200);
		meadBooze[MeadTypes.MEAD_POTENT.ordinal()].getFluid().setColor(GrowthcraftBeesConfig.honeyMeadColor).setDensity(1000).setViscosity(1200);
		meadBooze[MeadTypes.MEAD_EXTENDED.ordinal()].getFluid().setColor(GrowthcraftBeesConfig.honeyMeadColor).setDensity(1000).setViscosity(1200);
		meadBooze[MeadTypes.MEAD_ETHEREAL.ordinal()].getFluid().setColor(GrowthcraftBeesConfig.honeyMeadColor).setDensity(1000).setViscosity(1200);
		meadBooze[MeadTypes.MEAD_INTOXICATED.ordinal()].getFluid().setColor(GrowthcraftBeesConfig.honeyMeadColor).setDensity(1000).setViscosity(1200);
		meadBooze[MeadTypes.MEAD_POISONED.ordinal()].getFluid().setColor(GrowthcraftBeesConfig.honeyMeadColor).setDensity(1000).setViscosity(1200);
	}
	
	public static void initBoozes() {
		BoozeRegistryHelper.initBoozeContainers(meadBooze,
												GrowthcraftBeesItems.honeyMeadBottle,
												Reference.MODID,
												"mead",
												MeadTypes.class);
		registerFermentations();
	}
	
	public static void registerFluidOres() {
		if (GrowthcraftBeesFluids.honey != null)
		{
			OreDictionary.registerOre("bottleHoney", GrowthcraftBeesFluids.honey.asBottleItemStack());
			OreDictionary.registerOre("honeyDrop", GrowthcraftBeesFluids.honey.asBucketItemStack());
		}
	}
	
	public static void registerFluidBlocks(IForgeRegistry<Block> registry) {
		if( GrowthcraftBeesFluids.honey != null ) {
			GrowthcraftBeesFluids.honey.registerBlocks(registry, Reference.MODID, "honey");
			CoreRegistry.instance().fluidDictionary().addFluidTags(GrowthcraftBeesFluids.honey.getFluid(), BeesFluidTag.HONEY);
		}	

		GrowthcraftBeesBlocks.meadBoozeFluidBlocks = new BlockBoozeDefinition[meadBooze.length];
		BoozeRegistryHelper.initializeBooze(meadBooze, meadBoozeFluidBlocks);
		// TODO: Refactor me: Food stats need to be initialized in preInitFluids.
		BoozeRegistryHelper.setBoozeFoodStats(meadBooze, 1, -0.45f);
		BoozeRegistryHelper.setBoozeFoodStats(meadBooze[0], 1, 0.45f);
		
		BoozeRegistryHelper.registerBoozeBlocks(registry, meadBooze, meadBoozeFluidBlocks, Reference.MODID, "mead", MeadTypes.class);
	}
	
	public static void registerFluidItems(IForgeRegistry<Item> registry) {
		if( GrowthcraftBeesFluids.honey != null ) {
			GrowthcraftBeesFluids.honey.registerItems(registry, Reference.MODID, "honey");
		}
	}

	@SideOnly(Side.CLIENT)
	public static void registerFluidRender() {
		if( GrowthcraftBeesFluids.honey != null ) {
			GrowthcraftBeesFluids.honey.registerRenderer();
		}
		BoozeRegistryHelper.registerBoozeRenderers(meadBooze, meadBoozeFluidBlocks);
	}

	@SideOnly(Side.CLIENT)
	public static void registerFluidColorHandlers() {
		if( GrowthcraftBeesFluids.honey != null ) {
			GrowthcraftBeesFluids.honey.registerColorHandlers();
		}
	}

	private static void registerFermentations() {
    	final int fermentTime = GrowthcraftCellarConfig.fermentTime;
		final FluidStack[] fs = new FluidStack[meadBooze.length];
		for (int i = 0; i < meadBooze.length; ++i)
		{
			fs[i] = meadBooze[i].asFluidStack();
		}
		
		GrowthcraftCellarApis.boozeBuilderFactory.create(meadBooze[MeadTypes.MEAD_YOUNG.ordinal()].getFluid())
			.tags(BeesFluidTag.MEAD, BoozeTag.YOUNG);
		
		final TaggedFluidStacks youngMead = new TaggedFluidStacks(1, BoozeTag.YOUNG.getName(), BeesFluidTag.MEAD.getName());
		
		GrowthcraftCellarApis.boozeBuilderFactory.create(meadBooze[MeadTypes.MEAD_FERMENTED.ordinal()].getFluid())
			.tags(BoozeTag.FERMENTED, BeesFluidTag.MEAD)
			.fermentsFrom(youngMead, new OreItemStacks("yeastBrewers"), fermentTime)
			.fermentsFrom(youngMead, new ItemStack(Items.NETHER_WART), (int)(fermentTime * 0.66))
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.15f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.REGENERATION, TickUtils.seconds(90), 0);
		
		GrowthcraftCellarApis.boozeBuilderFactory.create(meadBooze[MeadTypes.MEAD_POTENT.ordinal()].getFluid())
			.tags(BoozeTag.FERMENTED, BoozeTag.POTENT, BeesFluidTag.MEAD)
			.fermentsFrom(fs[MeadTypes.MEAD_FERMENTED.ordinal()], new OreItemStacks("dustGlowstone"), fermentTime)
			.fermentsFrom(fs[MeadTypes.MEAD_EXTENDED.ordinal()], new OreItemStacks("dustGlowstone"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.17f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.REGENERATION, TickUtils.seconds(90), 0);

		GrowthcraftCellarApis.boozeBuilderFactory.create(meadBooze[MeadTypes.MEAD_EXTENDED.ordinal()].getFluid())
			.tags(BoozeTag.FERMENTED, BoozeTag.EXTENDED, BeesFluidTag.MEAD)
			.fermentsFrom(fs[MeadTypes.MEAD_FERMENTED.ordinal()], new OreItemStacks("dustRedstone"), fermentTime)
			.fermentsFrom(fs[MeadTypes.MEAD_POTENT.ordinal()], new OreItemStacks("dustRedstone"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.15f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.REGENERATION, TickUtils.seconds(90), 0);
		
		GrowthcraftCellarApis.boozeBuilderFactory.create(meadBooze[MeadTypes.MEAD_ETHEREAL.ordinal()].getFluid())
			.tags(BoozeTag.FERMENTED, BoozeTag.MAGICAL, BeesFluidTag.MEAD)
			.fermentsFrom(fs[MeadTypes.MEAD_EXTENDED.ordinal()], new OreItemStacks("yeastEthereal"), fermentTime)
			.fermentsFrom(fs[MeadTypes.MEAD_POTENT.ordinal()], new OreItemStacks("yeastEthereal"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.15f), TickUtils.seconds(90))
				.addPotionEntry(MobEffects.REGENERATION, TickUtils.seconds(90), 2);

		GrowthcraftCellarApis.boozeBuilderFactory.create(meadBooze[MeadTypes.MEAD_INTOXICATED.ordinal()].getFluid())
			.tags(BoozeTag.FERMENTED, BoozeTag.INTOXICATED, BeesFluidTag.MEAD)
			.fermentsFrom(fs[MeadTypes.MEAD_EXTENDED.ordinal()], new OreItemStacks("yeastOrigin"), fermentTime)
			.fermentsFrom(fs[MeadTypes.MEAD_POTENT.ordinal()], new OreItemStacks("yeastOrigin"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.15f), TickUtils.seconds(90))
				.addEffect(new EffectWeightedRandomList()
						.add(8, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.REGENERATION, TickUtils.seconds(90), 2)))
						.add(2, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.POISON, TickUtils.seconds(90), 2)))
					);
		
		GrowthcraftCellarApis.boozeBuilderFactory.create(meadBooze[MeadTypes.MEAD_POISONED.ordinal()].getFluid())
			.tags(BoozeTag.FERMENTED, BoozeTag.POISONED, BeesFluidTag.MEAD)
			.fermentsFrom(fs[MeadTypes.MEAD_YOUNG.ordinal()], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[MeadTypes.MEAD_FERMENTED.ordinal()], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[MeadTypes.MEAD_EXTENDED.ordinal()], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[MeadTypes.MEAD_POTENT.ordinal()], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[MeadTypes.MEAD_ETHEREAL.ordinal()], new OreItemStacks("yeastPoison"), fermentTime)
			.fermentsFrom(fs[MeadTypes.MEAD_INTOXICATED.ordinal()], new OreItemStacks("yeastPoison"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.15f), TickUtils.seconds(90))
				.createPotionEntry(MobEffects.POISON, TickUtils.seconds(90), 0).toggleDescription(!GrowthcraftCoreConfig.hidePoisonedBooze);
	}
	
	
	////////
	// User Apis
	////////
	
	public static void initUserApisDefaults() {
		GrowthcraftBees.userBeesConfig.addDefault(GrowthcraftBeesItems.bee.asStack()).setComment("Growthcraft's default bee");
		BeesRegistry.instance().addHoneyComb(GrowthcraftBeesItems.honeyCombEmpty.asStack(), GrowthcraftBeesItems.honeyCombFilled.asStack());
		GrowthcraftBees.userFlowersConfig.addDefault(Blocks.RED_FLOWER);
		GrowthcraftBees.userFlowersConfig.addDefault(Blocks.YELLOW_FLOWER);
	}

	////////
	// Recipes
	////////

	public static void registerRecipes() {
	}
	
	public static void registerCraftingRecipes(IForgeRegistry<IRecipe> registry) {
		ResourceLocation group = new ResourceLocation(Reference.MODID, "bees");
		
    	final UniversalBucket universalBucket = ForgeModContainer.getInstance().universalBucket;
    	final ItemStack meadBucket = UniversalBucket.getFilledBucket(universalBucket, meadBooze[0].getFluid());
		final ItemStack meadBottle = GrowthcraftBeesItems.honeyMeadBottle.asStack();
		final ItemStack meadBottle4 = GrowthcraftBeesItems.honeyMeadBottle.asStack(4);
		
		// TODO: Use factory instead for these:
		
		// Create mead booze
/* FIXME and give me a can of whoopass. I'm dump and don't want to work, because I'm trying to be funny and to annoy the devs		
 		registry.register(new ShapelessMultiRecipe(group.toString(),
				meadBucket,
				Items.BUCKET,
				new TaggedFluidStacks(1000, BeesFluidTag.HONEY.getName()),
				new FluidStack(FluidRegistry.WATER, 1000))
					.setRegistryName(toRegName("mead_fluid_to_bucket_1")));
		
		registry.register(new ShapelessMultiRecipe(group.toString(),
				meadBucket,
				Items.BUCKET,
				new OreItemStacks("bucketHoney"),
				new FluidStack(FluidRegistry.WATER, 1000))
					.setRegistryName(toRegName("mead_fluid_to_bucket_2"))); */
		
		registry.register(new ShapelessMultiRecipe(group.toString(),
				meadBottle,
				Items.GLASS_BOTTLE,
				new TaggedFluidStacks(FluidContainerRegistry.BOTTLE_VOLUME, BeesFluidTag.HONEY.getName()),
				new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BOTTLE_VOLUME))
					.setRegistryName(toRegName("mead_fluid_to_bottle_1")));

/* FIXME and give me a can of whoopass. I'm dump and don't want to work, because I'm trying to be funny and to annoy the devs
 		registry.register(new ShapelessMultiRecipe(group.toString(),
				meadBottle4,
				Items.GLASS_BOTTLE,	// TODO: Make dynamically. Amount of bottles = floor(Bucket volume / Bottle volume)
				Items.GLASS_BOTTLE,
				Items.GLASS_BOTTLE,
				Items.GLASS_BOTTLE,
				new TaggedFluidStacks(FluidContainerRegistry.BUCKET_VOLUME, BeesFluidTag.HONEY.getName()),
				new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME))
					.setRegistryName(toRegName("mead_fluid_to_bottle_2")));*/
		
		// Honey extraction
		final ItemStack honeyStack = GrowthcraftBeesItems.honeyCombFilled.asStack();
		registry.register(new ShapelessRecipes(group.toString(), GrowthcraftBeesItems.honeyJar.asStack(),
				NonNullList.from( Ingredient.fromStacks(honeyStack),
						Ingredient.fromStacks(honeyStack),
						Ingredient.fromStacks(honeyStack),
						Ingredient.fromStacks(honeyStack),
						Ingredient.fromStacks(honeyStack),
						Ingredient.fromStacks(honeyStack),
						Ingredient.fromStacks(honeyStack),
						Ingredient.fromItem(Items.FLOWER_POT)))
					.setRegistryName(toRegName("honey_comb_filled")));

		// Transfer recipes
		/// To Honey Jar from `jarHoney`
		registry.register(new ShapelessMultiRecipe(group.toString(),
				GrowthcraftBeesItems.honeyJar.asStack(),
				new TaggedFluidStacks(1000, BeesFluidTag.HONEY.getName()),
				Items.FLOWER_POT).setRegistryName(toRegName("honey_fluid_to_jar_1")));

		registry.register(new ShapelessMultiRecipe(group.toString(),
			GrowthcraftBeesItems.honeyJar.asStack(),
			Blocks.FLOWER_POT,
			new TaggedFluidStacks(FluidContainerRegistry.BUCKET_VOLUME, "honey")
				).setRegistryName(toRegName("honey_fluid_to_jar_2")));

		registry.register(new ShapelessOreRecipe(group,
			GrowthcraftBeesItems.honeyJar.asStack(),
			Items.FLOWER_POT,
			"jarHoney"
				).setRegistryName(toRegName("honey_ore_to_jar")));
		
		/// To Honey Bucket from `bucketHoney`
		registry.register(new ShapelessMultiRecipe(group.toString(),
			GrowthcraftBeesFluids.honey.asBucketItemStack(),
			Items.BUCKET,
			new TaggedFluidStacks(1000, "honey")
				).setRegistryName(toRegName("honey_fluid_to_bucket")));

		registry.register(new ShapelessOreRecipe(group,
			GrowthcraftBeesFluids.honey.asBucketItemStack(),
			"jarHoney",
			Items.BUCKET
				).setRegistryName(toRegName("honey_ore_to_bucket")));
		
		// TODO: RECIPE_REGISTER!
		
/*		// Devices
		GameRegistry.addRecipe(new ShapedOreRecipe(GrowthcraftBeesBlocks.beeBox.asStack(), " A ", "A A", "AAA", 'A', "plankWood"));

		// Bees wax
		final ItemStack emptyComb = GrowthcraftBeesItems.honeyCombEmpty.asStack();
		GameRegistry.addShapelessRecipe(BeesWaxTypes.NORMAL.asStack(),
			emptyComb, emptyComb, emptyComb,
			emptyComb, emptyComb, emptyComb,
			emptyComb, emptyComb, emptyComb);

		GameRegistry.addRecipe(new ShapelessOreRecipe(BeesWaxTypes.BLACK.asStack(),
			BeesWaxTypes.NORMAL.asStack(), "dyeBlack"));

		GameRegistry.addRecipe(new ShapelessOreRecipe(BeesWaxTypes.RED.asStack(),
			BeesWaxTypes.NORMAL.asStack(), "dyeRed"));

*/
	}
	
	private static ResourceLocation toRegName(String name) {
		return new ResourceLocation(Reference.MODID, name);
	}
	
	////////
	// Loot
	////////

	public static void lootLoad(LootTableLoadEvent evt) {
		boolean isVillageBlacksmith = evt.getName().toString().equals("minecraft:chests/village_blacksmith");
		
	    if (isVillageBlacksmith) {
	    	LootPool pool = LootUtils.getOrCreateLootPool(evt.getTable(), "growthcraft");
	    	LootUtils.addLootEntry(pool, GrowthcraftBeesItems.bee.getItem(), 1, 2, 10);
	    }
	}
}
