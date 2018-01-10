package growthcraft.core.api.definition;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

public interface IMultiItemStacks extends IItemStackListProvider
{
	boolean isEmpty();

	/**
	 * Returns the expected `stackSize`
	 *
	 * @return size
	 */
	int getStackSize();

	/**
	 * Determines if the mulit stack contains the specified item stack.
	 *
	 * @param stack - the item stack being searched for
	 * @return true, the multi stack contains this stack
	 */
	boolean containsItemStack(@Nullable ItemStack stack);
}
