package growthcraft.milk.init;

import java.util.ArrayList;
import java.util.List;

import growthcraft.cellar.init.GrowthcraftCellarBlocks;
import growthcraft.core.api.fluids.TaggedFluidStacks;
import growthcraft.core.api.item.CommonItemStackComparator;
import growthcraft.core.api.item.EnumDye;
import growthcraft.core.api.item.IItemStackComparator;
import growthcraft.core.api.item.OreItemStacks;
import growthcraft.core.common.item.crafting.ShapelessItemComparableRecipe;
import growthcraft.milk.GrowthcraftMilkConfig;
import growthcraft.milk.api.MilkRegistry;
import growthcraft.milk.api.definition.ICheeseBlockStackFactory;
import growthcraft.milk.api.definition.ICheeseCurdStackFactory;
import growthcraft.milk.api.definition.ICheeseType;
import growthcraft.milk.api.utils.CheeseVatRecipeBuilder;
import growthcraft.milk.common.item.ItemBlockHangingCurds;
import growthcraft.milk.common.processing.DriedCurdsCheesePressRecipe;
import growthcraft.milk.handlers.EnumHandler;
import growthcraft.milk.handlers.EnumHandler.AgedCheeseTypes;
import growthcraft.milk.handlers.EnumHandler.ButterTypes;
import growthcraft.milk.handlers.EnumHandler.IceCreamTypes;
import growthcraft.milk.handlers.EnumHandler.SimpleCheeseTypes;
import growthcraft.milk.handlers.EnumHandler.WaxedCheeseTypes;
import growthcraft.milk.handlers.EnumHandler.YogurtTypes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class GrowthcraftMilkRecipes {
	private static void registerCheeseVatRecipes()
	{
		final String[] saltOres = { "foodSalt", "materialSalt", "dustSalt" };

		for (String saltOre : saltOres)
		{
			CheeseVatRecipeBuilder.buildRecipe("CHEDDAR Orange Dye Recipe")
				.outputFluids(WaxedCheeseTypes.CHEDDAR.getFluids().asFluidStack(5000))
				.inputFluids(new TaggedFluidStacks(5000, "milk_curds"))
				.inputItems(new OreItemStacks(saltOre, 1), new OreItemStacks("dyeOrange", 1))
				.register();

			CheeseVatRecipeBuilder.buildRecipe("CHEDDAR Pumpkin Recipe")
				.outputFluids(WaxedCheeseTypes.CHEDDAR.getFluids().asFluidStack(5000))
				.inputFluids(new TaggedFluidStacks(5000, "milk_curds"))
				.inputItems(new OreItemStacks(saltOre, 1), new ItemStack(Blocks.PUMPKIN))
				.register();

			CheeseVatRecipeBuilder.buildRecipe("GORGONZOLA Recipe")
				.outputFluids(AgedCheeseTypes.GORGONZOLA.getFluids().asFluidStack(5000))
				.inputFluids(new TaggedFluidStacks(5000, "milk_curds"))
				.inputItems(new OreItemStacks(saltOre, 1), new OreItemStacks("foodFruit", 1))
				.register();

			CheeseVatRecipeBuilder.buildRecipe("EMMENTALER Recipe")
				.outputFluids(AgedCheeseTypes.EMMENTALER.getFluids().asFluidStack(5000))
				.inputFluids(new TaggedFluidStacks(5000, "milk_curds"))
				.inputItems(new OreItemStacks(saltOre, 1), new OreItemStacks("cropWheat", 1))
				.register();

			CheeseVatRecipeBuilder.buildRecipe("APPENZELLER Wine Recipe")
				.outputFluids(AgedCheeseTypes.APPENZELLER.getFluids().asFluidStack(5000))
				.inputFluids(new TaggedFluidStacks(5000, "milk_curds"), new TaggedFluidStacks(1000, "wine"))
				.inputItems(new OreItemStacks(saltOre, 1))
				.register();

			CheeseVatRecipeBuilder.buildRecipe("APPENZELLER Cider Recipe")
				.outputFluids(AgedCheeseTypes.APPENZELLER.getFluids().asFluidStack(5000))
				.inputFluids(new TaggedFluidStacks(5000, "milk_curds"), new TaggedFluidStacks(1000, "cider"))
				.inputItems(new OreItemStacks(saltOre, 1))
				.register();

			CheeseVatRecipeBuilder.buildRecipe("ASIAGO Recipe")
				.outputFluids(AgedCheeseTypes.ASIAGO.getFluids().asFluidStack(5000))
				.inputFluids(new TaggedFluidStacks(5000, "milk_curds"))
				.inputItems(new OreItemStacks(saltOre, 1), new OreItemStacks(saltOre, 1), new OreItemStacks("dyeYellow", 1))
				.register();

			CheeseVatRecipeBuilder.buildRecipe("PARMESAN Recipe")
				.outputFluids(AgedCheeseTypes.PARMESAN.getFluids().asFluidStack(5000))
				.inputFluids(new TaggedFluidStacks(5000, "milk_curds"))
				.inputItems(new OreItemStacks(saltOre, 1), new OreItemStacks("dyeWhite", 1))
				.register();

			CheeseVatRecipeBuilder.buildRecipe("MONTEREY Recipe")
				.outputFluids(WaxedCheeseTypes.MONTEREY.getFluids().asFluidStack(5000))
				.inputFluids(new TaggedFluidStacks(5000, "milk_curds"))
				.inputItems(new OreItemStacks(saltOre, 1), new OreItemStacks("dyeRed", 1))
				.register();
		}
	}
	
	private static void registerCraftingRecipes() {
		final int ricottaBowlCount = GrowthcraftMilkConfig.ricottaBowlCount;
		final List<ItemStack> ricottaBowlRecipe = new ArrayList<ItemStack>();
		ricottaBowlRecipe.add(SimpleCheeseTypes.RICOTTA.getCurdBlocks().asStack());
		for (int i = 0; i < ricottaBowlCount; ++i)
		{
			ricottaBowlRecipe.add(new ItemStack(Items.BOWL, 1));
		}
		GameRegistry.addRecipe(new ShapelessItemComparableRecipe(new DriedCurdComparator(),
			SimpleCheeseTypes.RICOTTA.getCheeseItems().asStack(ricottaBowlCount), ricottaBowlRecipe
		));

		GameRegistry.addRecipe(new ShapelessOreRecipe(GrowthcraftMilkBlocks.cheeseVat.asStack(),
			GrowthcraftCellarBlocks.brewKettle.asStack()
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(GrowthcraftMilkBlocks.churn.asStack(),
			" S ",
			"P P",
			"PPP",
			'S', "stickWood",
			'P', "plankWood"
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(GrowthcraftMilkBlocks.cheesePress.asStack(),
			"iii",
			"iCi",
			"ppp",
			'i', "ingotIron",
			'C', Blocks.CHEST,
			'p', "slabWood"
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(GrowthcraftMilkBlocks.pancheon.asStack(),
			"c c",
			"ccc",
			'c', Items.CLAY_BALL
		));
		
		// Salted Butter
		GameRegistry.addRecipe(new ShapelessOreRecipe(ButterTypes.SALTED.asStack(), ButterTypes.UNSALTED.asStack(), "foodSalt"));

		// Yogurt - Plain
		GameRegistry.addRecipe(new ShapelessOreRecipe(YogurtTypes.PLAIN.asStack(),
			Items.MILK_BUCKET,
			GrowthcraftMilkItems.starterCulture.asStack(),
			Items.BOWL
		));

		// Yogurt - Chocolate
		GameRegistry.addRecipe(new ShapelessOreRecipe(YogurtTypes.CHOCOLATE.asStack(),
			Items.MILK_BUCKET,
			GrowthcraftMilkItems.starterCulture.asStack(),
			Items.BOWL,
			EnumDye.COCOA_BEANS.asStack()
		));

		// Yogurt - Grape
		GameRegistry.addRecipe(new ShapelessOreRecipe(YogurtTypes.GRAPEPURPLE.asStack(),
			Items.MILK_BUCKET,
			GrowthcraftMilkItems.starterCulture.asStack(),
			Items.BOWL,
			"foodGrapesPurple"
		));

		// Yogurt - Apple
		GameRegistry.addRecipe(new ShapelessOreRecipe(YogurtTypes.APPLE.asStack(),
			Items.MILK_BUCKET,
			GrowthcraftMilkItems.starterCulture.asStack(),
			Items.BOWL,
			"foodApple"
		));

		// Yogurt - Honey
		GameRegistry.addRecipe(new ShapelessOreRecipe(YogurtTypes.HONEY.asStack(),
			Items.MILK_BUCKET,
			GrowthcraftMilkItems.starterCulture.asStack(),
			Items.BOWL,
			"honeyDrop"
		));

		// Yogurt - Melon
		GameRegistry.addRecipe(new ShapelessOreRecipe(YogurtTypes.WATERMELON.asStack(),
			Items.MILK_BUCKET,
			GrowthcraftMilkItems.starterCulture.asStack(),
			Items.BOWL,
			"foodMelon"
		));

		// Ice Cream - Plain
		GameRegistry.addRecipe(new ShapelessOreRecipe(IceCreamTypes.PLAIN.asStack(),
			GrowthcraftMilkFluids.cream.asBucketItemStack(),
			Items.MILK_BUCKET,
			Items.BOWL,
			Items.SUGAR
		));

		// Ice Cream - Chocolate
		GameRegistry.addRecipe(new ShapelessOreRecipe(IceCreamTypes.CHOCOLATE.asStack(),
			GrowthcraftMilkFluids.cream.asBucketItemStack(),
			Items.MILK_BUCKET,
			Items.BOWL,
			Items.SUGAR,
			EnumDye.COCOA_BEANS.asStack()
		));

		// Ice Cream - Grape
		GameRegistry.addRecipe(new ShapelessOreRecipe(IceCreamTypes.GRAPEPURPLE.asStack(),
			GrowthcraftMilkFluids.cream.asBucketItemStack(),
			Items.MILK_BUCKET,
			Items.BOWL,
			Items.SUGAR,
			"foodGrapesPurple"
		));

		// Ice Cream - Apple
		GameRegistry.addRecipe(new ShapelessOreRecipe(IceCreamTypes.APPLE.asStack(),
			GrowthcraftMilkFluids.cream.asBucketItemStack(),
			Items.MILK_BUCKET,
			Items.BOWL,
			Items.SUGAR,
			"foodApple"
		));

		// Ice Cream - Honey
		GameRegistry.addRecipe(new ShapelessOreRecipe(IceCreamTypes.HONEY.asStack(),
			GrowthcraftMilkFluids.cream.asBucketItemStack(),
			Items.MILK_BUCKET,
			Items.BOWL,
			Items.SUGAR,
			"honeyDrop"
		));

		// Ice Cream - Melon
		GameRegistry.addRecipe(new ShapelessOreRecipe(IceCreamTypes.WATERMELON.asStack(),
			GrowthcraftMilkFluids.cream.asBucketItemStack(),
			Items.MILK_BUCKET,
			Items.BOWL,
			Items.SUGAR,
			"foodMelon"
		));

		// Cheese Cloth
		GameRegistry.addRecipe(new ShapedOreRecipe(GrowthcraftMilkItems.cheeseCloth.asStack(4),
			"sss",
			"s s",
			"sss",
			's', Items.STRING
		));
		
		if (GrowthcraftMilkItems.thistleSeed != null && GrowthcraftMilkBlocks.thistle != null)
		{
			GameRegistry.addShapelessRecipe(GrowthcraftMilkItems.thistleSeed.asStack(2), GrowthcraftMilkItems.thistle.getItem());
		}
	}
	
	private static <E extends Enum<?> & ICheeseType & IStringSerializable> void registerCheesePressRecipesFor(Class<E> enumType) {
		E[] values = enumType.getEnumConstants();
		for( ICheeseType type : values ) {
			ICheeseBlockStackFactory blockFactory = type.getCheeseBlocks();
			ICheeseCurdStackFactory curdsFactory = type.getCurdBlocks();
			if(blockFactory != null && curdsFactory != null) {
				MilkRegistry.instance().cheesePress().addRecipe(new DriedCurdsCheesePressRecipe(curdsFactory.asStack(), blockFactory.asStackForStage(4, blockFactory.getInitialStage()), 200));
			}
		}
	}
	
	private static void registerCheesePressRecipes()
	{
		registerCheesePressRecipesFor(AgedCheeseTypes.class);
		registerCheesePressRecipesFor(WaxedCheeseTypes.class);
	}

	
	public static void init() {
		registerCheeseVatRecipes();
		registerCheesePressRecipes();
		registerCraftingRecipes();
	}
	
	
	//
	
	public static class DriedCurdComparator implements IItemStackComparator
	{
		private CommonItemStackComparator common = new CommonItemStackComparator();

		public boolean equals(ItemStack expected, ItemStack actual)
		{
			if (expected.getItem() instanceof ItemBlockHangingCurds)
			{
				if (actual.getItem() instanceof ItemBlockHangingCurds)
				{
					final ItemBlockHangingCurds<?> actualCurd = (ItemBlockHangingCurds<?>)actual.getItem();
					final ItemBlockHangingCurds<?> expectedCurd = (ItemBlockHangingCurds<?>)expected.getItem();
					if (expectedCurd.getCheeseType(expected) == actualCurd.getCheeseType(actual))
					{
						if (actualCurd.isDried(actual)) return true;
					}
				}
				return false;
			}
			else
			{
				return common.equals(expected, actual);
			}
		}
	}
}
