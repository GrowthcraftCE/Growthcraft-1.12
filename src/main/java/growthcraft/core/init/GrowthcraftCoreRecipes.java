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

        Item ironIngot = Items.IRON_INGOT;

        // Crowbar Recipes
        for ( EnumDyeColor dye : EnumDyeColor.values() ) {
            GameRegistry.addShapedRecipe( new ItemStack(GrowthcraftCoreItems.crowbar, 1, dye.getMetadata()),
                    "  I", "DI ", "ID ",
                    'I', ironIngot,
                    'D', new ItemStack(Items.DYE, 1, dye.getDyeDamage())
                    );
        }

        GameRegistry.addRecipe( new ItemStack(GrowthcraftCoreBlocks.salt_block, 1),
                "SSS", "SSS", "SSS",
                'S', GrowthcraftCoreItems.salt
        );
        GameRegistry.addRecipe( new ItemStack(GrowthcraftCoreItems.salt, 9),
                "S",
                'S', GrowthcraftCoreBlocks.salt_block
        );

    }
}
