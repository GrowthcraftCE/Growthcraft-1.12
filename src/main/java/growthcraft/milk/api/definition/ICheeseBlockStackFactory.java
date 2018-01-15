package growthcraft.milk.api.definition;

import net.minecraft.item.ItemStack;

public interface ICheeseBlockStackFactory {
	ItemStack asStackForStage(EnumCheeseStage stage);
	ItemStack asStackForStage(int size, EnumCheeseStage stage);
	EnumCheeseStage getStageFromStack(ItemStack blockItemStack);
}
