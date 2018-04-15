package growthcraft.cellar.api.processing.pressing;

import growthcraft.cellar.api.processing.common.IProcessingRecipe;
import growthcraft.core.shared.definition.IMultiItemStacks;
import net.minecraft.item.ItemStack;

public interface IPressingRecipe extends IProcessingRecipe {

	IMultiItemStacks getInput();

	boolean matchesRecipe(ItemStack itemStack);

}
