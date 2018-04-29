package growthcraft.bees.common;

import static growthcraft.core.shared.GrowthcraftCoreApis.tabGrowthcraft;

import growthcraft.bees.GrowthcraftBees;
import growthcraft.bees.common.block.BlockBeeBox;
import growthcraft.bees.common.fluids.FluidHoney;
import growthcraft.bees.common.items.ItemBee;
import growthcraft.bees.common.items.ItemBeesWax;
import growthcraft.bees.common.items.ItemHoneyCombEmpty;
import growthcraft.bees.common.items.ItemHoneyCombFilled;
import growthcraft.bees.common.items.ItemHoneyJar;
import growthcraft.bees.common.lib.config.BeesFluidTag;
import growthcraft.bees.common.lib.config.BeesRegistry;
import growthcraft.bees.common.tileentity.TileEntityBeeBox;
import growthcraft.bees.shared.Reference;
import growthcraft.bees.shared.config.GrowthcraftBeesConfig;
import growthcraft.bees.shared.init.GrowthcraftBeesBlocks;
import growthcraft.bees.shared.init.GrowthcraftBeesFluids;
import growthcraft.bees.shared.init.GrowthcraftBeesItems;
import growthcraft.bees.shared.init.GrowthcraftBeesItems.BeesWaxTypes;
import growthcraft.core.shared.CoreRegistry;
import growthcraft.core.shared.GrowthcraftCoreApis;
import growthcraft.core.shared.definition.BlockDefinition;
import growthcraft.core.shared.definition.ItemDefinition;
import growthcraft.core.shared.fluids.FluidFactory;
import growthcraft.core.shared.fluids.FluidFactory.FluidDetailsBuilder;
import growthcraft.core.shared.fluids.TaggedFluidStacks;
import growthcraft.core.shared.item.ItemFoodBottleFluid;
import growthcraft.core.shared.item.recipes.ShapelessMultiRecipe;
import growthcraft.core.shared.legacy.FluidContainerRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class Init {
	private Init() {}

	////////
	// Blocks
	////////
	
	public static void preInitBlocks() {
		GrowthcraftBeesBlocks.beeBox = new BlockDefinition(new BlockBeeBox("beebox"));
	}
	
	public static void registerBlocks() {
		GrowthcraftBeesBlocks.beeBox.getBlock().setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft);
		GrowthcraftBeesBlocks.beeBox.register(true);
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
	}
	
	private static void registerItemOres() {
		OreDictionary.registerOre("materialWax", GrowthcraftBeesItems.beesWax.getItem());
		OreDictionary.registerOre("materialPressedwax", GrowthcraftBeesItems.beesWax.getItem());
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

	public static void registerItems() {
		GrowthcraftBeesItems.honeyCombEmpty.getItem().setCreativeTab(tabGrowthcraft);
		GrowthcraftBeesItems.honeyCombEmpty.register();
		GrowthcraftBeesItems.honeyCombFilled.getItem().setCreativeTab(tabGrowthcraft);
		GrowthcraftBeesItems.honeyCombFilled.register();
		GrowthcraftBeesItems.honeyJar.getItem().setCreativeTab(tabGrowthcraft);
		GrowthcraftBeesItems.honeyJar.register();
		GrowthcraftBeesItems.bee.getItem().setCreativeTab(tabGrowthcraft);
		GrowthcraftBeesItems.bee.register();
		GrowthcraftBeesItems.beesWax.getItem().setCreativeTab(tabGrowthcraft);
		GrowthcraftBeesItems.beesWax.register();
		
		registerItemOres();
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerItemRenders() {
		GrowthcraftBeesItems.honeyCombEmpty.registerRender();
		GrowthcraftBeesItems.honeyCombFilled.registerRender();
		GrowthcraftBeesItems.honeyJar.registerRender();
		GrowthcraftBeesItems.bee.registerRender();
		GrowthcraftBeesItems.beesWax.registerRenders( BeesWaxTypes.class );
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerItemVariants() {
		GrowthcraftBeesItems.beesWax.registerModelBakeryVariants( BeesWaxTypes.class );
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
	}
	
	public static void registerFluidOres() {
		if (GrowthcraftBeesFluids.honey != null)
		{
			OreDictionary.registerOre("bottleHoney", GrowthcraftBeesFluids.honey.asBottleItemStack());
//			OreDictionary.registerOre("bucketHoney", honey.asBucketItemStack());
			OreDictionary.registerOre("honeyDrop", GrowthcraftBeesFluids.honey.asBucketItemStack());
			OreDictionary.registerOre("dropHoney", GrowthcraftBeesFluids.honey.asBucketItemStack());
		}
	}
	
	public static void registerFluids() {
		if( GrowthcraftBeesFluids.honey != null ) {
			GrowthcraftBeesFluids.honey.registerObjects(Reference.MODID, "honey");
			CoreRegistry.instance().fluidDictionary().addFluidTags(GrowthcraftBeesFluids.honey.getFluid(), BeesFluidTag.HONEY);
		}
		
		registerFluidOres();
	}

	@SideOnly(Side.CLIENT)
	public static void registerFluidRender() {
		if( GrowthcraftBeesFluids.honey != null ) {
			GrowthcraftBeesFluids.honey.registerRenderer();
		}
	}

	@SideOnly(Side.CLIENT)
	public static void registerFluidColorHandlers() {
		if( GrowthcraftBeesFluids.honey != null ) {
			GrowthcraftBeesFluids.honey.registerColorHandlers();
		}
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
		registerCraftingRecipes();
	}
	
	private static void registerCraftingRecipes() {
		// Devices
		GameRegistry.addRecipe(new ShapedOreRecipe(GrowthcraftBeesBlocks.beeBox.asStack(), " A ", "A A", "AAA", 'A', "plankWood"));

		GameRegistry.addRecipe(new ShapelessMultiRecipe(
				GrowthcraftBeesItems.honeyJar.asStack(),
				new TaggedFluidStacks(1000, BeesFluidTag.HONEY.getName()),
				Items.FLOWER_POT));
		
		// Honey extraction
		final ItemStack honeyStack = GrowthcraftBeesItems.honeyCombFilled.asStack();
		GameRegistry.addShapelessRecipe(GrowthcraftBeesItems.honeyJar.asStack(),
			honeyStack, honeyStack, honeyStack, honeyStack, honeyStack, honeyStack, Items.FLOWER_POT);
		
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

		// Transfer recipes
		/// To Honey Jar from `jarHoney`
		GameRegistry.addRecipe(new ShapelessMultiRecipe(
			GrowthcraftBeesItems.honeyJar.asStack(),
			Blocks.FLOWER_POT,
			new TaggedFluidStacks(FluidContainerRegistry.BUCKET_VOLUME, "honey")
		));

		GameRegistry.addRecipe(new ShapelessOreRecipe(
			GrowthcraftBeesItems.honeyJar.asStack(),
			Blocks.FLOWER_POT,
			"jarHoney"
		));
		
		/// To Honey Bucket from `bucketHoney`
		GameRegistry.addRecipe(new ShapelessMultiRecipe(
			GrowthcraftBeesFluids.honey.asBucketItemStack(),
			Items.BUCKET,
			new TaggedFluidStacks(1000, "honey")
		));

		GameRegistry.addRecipe(new ShapelessOreRecipe(
			GrowthcraftBeesFluids.honey.asBucketItemStack(),
			"jarHoney",
			Items.BUCKET
		));
	}
}
