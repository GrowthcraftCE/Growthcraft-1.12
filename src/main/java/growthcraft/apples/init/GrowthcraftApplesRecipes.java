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
                new ItemStack(GrowthcraftApplesBlocks.blockApplePlanks, 4),
                "W",
                'W', new ItemStack(GrowthcraftApplesBlocks.blockAppleLog, 1));

        GameRegistry.addShapedRecipe(
                new ItemStack(GrowthcraftApplesBlocks.blockAppleStairs, 4),
                "  P", " PP", "PPP",
                'P', new ItemStack(GrowthcraftApplesBlocks.blockApplePlanks, 1));

        GameRegistry.addShapedRecipe(
                new ItemStack(GrowthcraftApplesBlocks.blockAppleStairs, 4),
                "P  ", "PP ", "PPP",
                'P', new ItemStack(GrowthcraftApplesBlocks.blockApplePlanks, 1));

        GameRegistry.addRecipe(
                new ItemStack(GrowthcraftApplesBlocks.blockAppleSlabHalf, 4),
                "PPP",
                'P', new ItemStack(GrowthcraftApplesBlocks.blockApplePlanks, 1));

        GameRegistry.addShapedRecipe(
                new ItemStack(Blocks.CHEST, 1),
                "PPP", "P P", "PPP",
                'P', new ItemStack(GrowthcraftApplesBlocks.blockApplePlanks, 1));

        GameRegistry.addShapedRecipe(
                new ItemStack(GrowthcraftApplesBlocks.blockAppleFence, 3),
                "PSP", "PSP",
                'P', new ItemStack(GrowthcraftApplesBlocks.blockApplePlanks, 1),
                'S', new ItemStack(Items.STICK, 1));

        GameRegistry.addShapedRecipe(
                new ItemStack(GrowthcraftApplesBlocks.blockAppleFenceGate, 1),
                "SPS", "SPS",
                'P', new ItemStack(GrowthcraftApplesBlocks.blockApplePlanks, 1),
                'S', new ItemStack(Items.STICK, 1));

        GameRegistry.addShapedRecipe(
                new ItemStack(Items.STICK, 4),
                "P", "P",
                'P', new ItemStack(GrowthcraftApplesBlocks.blockApplePlanks, 1) );

        GameRegistry.addShapedRecipe(
                new ItemStack(GrowthcraftApplesBlocks.blockAppleDoor, 1),
                "PP ", "PP ", "PP ",
                'P', new ItemStack(GrowthcraftApplesBlocks.blockApplePlanks, 1) );

        GameRegistry.addShapelessRecipe(
                new ItemStack(GrowthcraftApplesBlocks.blockAppleSapling, 1),
                 new ItemStack(Items.APPLE, 1),
                new ItemStack(Blocks.SAPLING, 1),
                new ItemStack(Items.WOODEN_SWORD, 1) );

    }
}
