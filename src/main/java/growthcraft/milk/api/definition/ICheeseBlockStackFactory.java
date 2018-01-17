package growthcraft.milk.api.definition;

import net.minecraft.item.ItemStack;

public interface ICheeseBlockStackFactory {
	ItemStack asStackForStage(int size, int slices, EnumCheeseStage stage);
	default ItemStack asStackForStage(int slices, EnumCheeseStage stage) {
		return asStackForStage(1, slices, stage);
	}
	
/*	ItemStack asStackForInitialStage(int size);
	default ItemStack asStackForInitialStage() {
		return asStackForInitialStage(1);
	} */
	
/*	ItemStack asItemStackForStage(int size, EnumCheeseStage stage);
	default ItemStack asItemStackForStage(EnumCheeseStage stage) {
		return asItemStackForStage(1, stage);
	} */

	EnumCheeseStage getInitialStage();

	
//	EnumCheeseStage getStageFromStack(ItemStack blockItemStack);
}
