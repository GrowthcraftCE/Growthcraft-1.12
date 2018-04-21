package growthcraft.cellar.shared.processing.pressing;

import growthcraft.cellar.shared.processing.common.IProcessingRecipe;
import growthcraft.core.shared.definition.IMultiItemStacks;
import net.minecraft.item.ItemStack;

public interface IPressingRecipe extends IProcessingRecipe {

	IMultiItemStacks getInput();

	boolean matchesRecipe(ItemStack itemStack);

}
