package growthcraft.milk.api.definition;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public interface ICheeseType {
	ResourceLocation getRegistryName();
	
	boolean canWax(ItemStack stack);
	
	int getColor();
	
	ICheeseItemStackFactory getCheeseItems();

	ICheeseBlockStackFactory getCheeseBlocks();

	ICheeseCurdStackFactory getCurdBlocks();
	
	ICheeseFluidStackFactory getFluids();
}
