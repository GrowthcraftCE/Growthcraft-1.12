package growthcraft.milk.api.definition;

import growthcraft.core.api.definition.IFluidStackFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public interface ICheeseType extends IStringSerializable {
	boolean canWax(ItemStack stack);
	
	int getColor();
	
	ICheeseItemStackFactory getCheeseItems();

	ICheeseBlockStackFactory getCheeseBlocks();

	ICheeseCurdStackFactory getCurdBlocks();
	
	IFluidStackFactory getFluids();
}
