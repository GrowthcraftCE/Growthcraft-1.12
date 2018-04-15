package growthcraft.bees.init;

import growthcraft.bees.api.BeesFluidTag;
import growthcraft.bees.handlers.EnumHandler.BeesWaxTypes;
import growthcraft.core.api.fluids.TaggedFluidStacks;
import growthcraft.core.api.item.recipes.ShapelessMultiRecipe;
import growthcraft.core.lib.legacy.FluidContainerRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class GrowthcraftBeesRecipes {
	private GrowthcraftBeesRecipes() {}
	
	public static void registerCraftingRecipes() {
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
	
	public static void init() {
		registerCraftingRecipes();
	}
}
