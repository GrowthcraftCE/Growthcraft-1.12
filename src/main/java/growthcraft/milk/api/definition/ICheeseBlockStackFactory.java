package growthcraft.milk.api.definition;

import net.minecraft.item.ItemStack;

public interface ICheeseBlockStackFactory {
	ItemStack asStackForStage(int size, EnumCheeseStage stage);
	default ItemStack asStackForStage(EnumCheeseStage stage) {
		return asStackForStage(1, stage);
	}
	
	ItemStack asStackForInitialStage(int size);
	default ItemStack asStackForInitialStage() {
		return asStackForInitialStage(1);
	}

	EnumCheeseStage getStageFromStack(ItemStack blockItemStack);
}
