package growthcraft.grapes.init;

import growthcraft.grapes.handlers.EnumHandler.GrapeTypes;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GrowthcraftGrapesRecipes {
	private static void registerCraftingRecipes() {
		GameRegistry.addShapelessRecipe(GrapeTypes.PURPLE.asSeedsStack(1), GrapeTypes.PURPLE.asStack(1) );
		GameRegistry.addShapelessRecipe(GrapeTypes.GREEN.asSeedsStack(1), GrapeTypes.GREEN.asStack(1) );
		GameRegistry.addShapelessRecipe(GrapeTypes.RED.asSeedsStack(1), GrapeTypes.RED.asStack(1) );
	}
	
	public static void init() {
		registerCraftingRecipes();
	}
}
