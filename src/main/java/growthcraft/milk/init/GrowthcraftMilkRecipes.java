package growthcraft.milk.init;

import growthcraft.core.api.fluids.TaggedFluidStacks;
import growthcraft.core.api.item.OreItemStacks;
import growthcraft.milk.api.utils.CheeseVatRecipeBuilder;
import growthcraft.milk.handlers.EnumHandler.AgedCheeseTypes;
import growthcraft.milk.handlers.EnumHandler.WaxedCheeseTypes;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

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
	
	public static void init() {
		registerCheeseVatRecipes();
	}
	
}
