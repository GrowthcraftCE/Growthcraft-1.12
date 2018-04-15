package growthcraft.cellar.common.lib.processing.pressing;

import growthcraft.cellar.common.lib.processing.common.IProcessingRecipe;
import growthcraft.core.shared.definition.IMultiItemStacks;
import net.minecraft.item.ItemStack;

public interface IPressingRecipe extends IProcessingRecipe {

	IMultiItemStacks getInput();

	boolean matchesRecipe(ItemStack itemStack);

}
