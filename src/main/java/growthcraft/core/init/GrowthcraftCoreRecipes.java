package growthcraft.core.init;

import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GrowthcraftCoreRecipes {


    public static void registerRecipes() {
        registerCraftingRecipes();
    }

    private static void registerCraftingRecipes() {

        // Crowbar Recipes
        Item ironIngot = Items.IRON_INGOT;

        for ( EnumDyeColor dye : EnumDyeColor.values() ) {
            GameRegistry.addShapedRecipe( new ItemStack(GrowthcraftCoreItems.crowbar, 1, dye.getMetadata()),
                    "  I", "DI ", "ID ",
                    'I', ironIngot,
                    'D', new ItemStack(Items.DYE, 1, dye.getDyeDamage())
                    );
        }
    }
}
