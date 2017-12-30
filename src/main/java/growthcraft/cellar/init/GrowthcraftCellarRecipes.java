package growthcraft.cellar.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GrowthcraftCellarRecipes {
    public static void registerRecipes() {
        registerCraftingRecipes();
    }

    public static void registerCraftingRecipes() {


        GameRegistry.addRecipe(new ItemStack(GrowthcraftCellarBlocks.blockCultureJar),
                "BAB", "B B", "BBB",
                'A', Blocks.PLANKS,
                'B', Blocks.GLASS_PANE
        );


        /*
        GameRegistry.addRecipe(new ItemStack(GrowthcraftFishtrapBlocks.fishtrap, 1),
                "ACA", "CBC", "ACA",
                'A', Blocks.PLANKS,
                'B', Items.LEAD,
                'C', Items.STRING
        );
        */

        GameRegistry.addShapelessRecipe(
                new ItemStack(GrowthcraftCellarBlocks.blockBrewKettle, 1),
                Items.CAULDRON
        );

    }
}
