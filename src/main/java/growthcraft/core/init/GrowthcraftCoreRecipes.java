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
            GameRegistry.addShapedRecipe( GrowthcraftCoreItems.crowbar.asStack(1, dye.getMetadata() ),
                    "  I", "DI ", "ID ",
                    'I', ironIngot,
                    'D', new ItemStack(Items.DYE, 1, dye.getDyeDamage())
                    );
        }

        GameRegistry.addRecipe( GrowthcraftCoreBlocks.salt_block.asStack(1),
                "SSS", "SSS", "SSS",
                'S', GrowthcraftCoreItems.salt.getItem()
        );
        GameRegistry.addRecipe( GrowthcraftCoreItems.salt.asStack(9),
                "S",
                'S', GrowthcraftCoreBlocks.salt_block.getBlock()
        );
        
		GameRegistry.addRecipe(GrowthcraftCoreItems.rope.asStack(8), new Object[] {"A", 'A', Items.LEAD});

    }
}
