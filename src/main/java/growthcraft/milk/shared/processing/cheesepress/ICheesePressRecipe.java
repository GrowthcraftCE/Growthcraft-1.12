package growthcraft.milk.shared.processing.cheesepress;

import growthcraft.cellar.shared.processing.common.IProcessingRecipeBase;
import net.minecraft.item.ItemStack;

public interface ICheesePressRecipe extends IProcessingRecipeBase {

    ItemStack getInputItemStack();

    ItemStack getOutputItemStack();

    boolean isMatchingRecipe(ItemStack stack);

}
