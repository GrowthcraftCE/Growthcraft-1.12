package growthcraft.apples.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GrowthcraftApplesRecipes {
    public static void registerRecipes() {
        registerCraftingRecipes();
    }

    private static void registerCraftingRecipes() {

        GameRegistry.addRecipe(
                GrowthcraftApplesBlocks.blockApplePlanks.asStack(4),
                "W",
                'W', GrowthcraftApplesBlocks.blockAppleLog.getBlock());

        GameRegistry.addShapedRecipe(
        		GrowthcraftApplesBlocks.blockAppleStairs.asStack(4),
                "  P", " PP", "PPP",
                'P', GrowthcraftApplesBlocks.blockApplePlanks.getBlock());

        GameRegistry.addShapedRecipe(
        		GrowthcraftApplesBlocks.blockAppleStairs.asStack(4),
                "P  ", "PP ", "PPP",
                'P', GrowthcraftApplesBlocks.blockApplePlanks.getBlock());

        GameRegistry.addRecipe(
        		GrowthcraftApplesBlocks.blockAppleSlabHalf.asStack(4),
                "PPP",
                'P', GrowthcraftApplesBlocks.blockApplePlanks.getBlock());

        GameRegistry.addShapedRecipe(
                new ItemStack(Blocks.CHEST, 1),
                "PPP", "P P", "PPP",
                'P', GrowthcraftApplesBlocks.blockApplePlanks.getBlock());

        GameRegistry.addShapedRecipe(
        		GrowthcraftApplesBlocks.blockAppleFence.asStack(3),
                "PSP", "PSP",
                'P', GrowthcraftApplesBlocks.blockApplePlanks.getBlock(),
                'S', new ItemStack(Items.STICK, 1));

        GameRegistry.addShapedRecipe(
        		GrowthcraftApplesBlocks.blockAppleFenceGate.asStack(1),
                "SPS", "SPS",
                'P', GrowthcraftApplesBlocks.blockApplePlanks.getBlock(),
                'S', new ItemStack(Items.STICK, 1));

        GameRegistry.addShapedRecipe(
                new ItemStack(Items.STICK, 4),
                "P", "P",
                'P', GrowthcraftApplesBlocks.blockApplePlanks.getBlock() );

        GameRegistry.addShapedRecipe(
        		GrowthcraftApplesItems.itemAppleDoor.asStack(1),
                "PP ", "PP ", "PP ",
                'P', GrowthcraftApplesBlocks.blockApplePlanks.getBlock()
        );

        GameRegistry.addShapelessRecipe(
        		GrowthcraftApplesBlocks.blockAppleSapling.asStack(1),
                new ItemStack(Items.APPLE, 1),
                new ItemStack(Blocks.SAPLING, 1),
                new ItemStack(Items.WOODEN_SWORD, 1) );

    }
}
