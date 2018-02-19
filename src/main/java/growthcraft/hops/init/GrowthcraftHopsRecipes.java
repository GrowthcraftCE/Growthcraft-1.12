package growthcraft.hops.init;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class GrowthcraftHopsRecipes {
	private static void registerCraftingRecipes() {
		GameRegistry.addShapelessRecipe(GrowthcraftHopsItems.hop_seeds.asStack(1), GrowthcraftHopsItems.hops.asStack(1) );
	}
	
	public static void init() {
		registerCraftingRecipes();
	}
}
