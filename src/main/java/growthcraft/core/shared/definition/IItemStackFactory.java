package growthcraft.core.shared.definition;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

public interface IItemStackFactory
{
	/**
	 * Returns an ItemStack of the specified size.
	 *
	 * @param size - stack size
	 * @return item stack
	 */
	@Nullable ItemStack asStack(int size);

	/**
	 * Returns the ItemStack with its default size (normally 1).
	 *
	 * @return item stack
	 */
	@Nullable ItemStack asStack();
}
