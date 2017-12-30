package growthcraft.fishtrap.handlers;

import growthcraft.fishtrap.init.GrowthcraftFishtrapBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipeHandler {

    public static void registerCraftingRecipes() {
        GameRegistry.addRecipe(new ItemStack(GrowthcraftFishtrapBlocks.fishtrap, 1),
                "ACA", "CBC", "ACA",
                'A', Blocks.PLANKS,
                'B', Items.LEAD,
                'C', Items.STRING
        );
    }

    public static void registerSmeltingRecipes() {

    }

}
