package growthcraft.core.api.definition;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

public interface IItemStackListProvider
{
	/**
	 * Returns a list of item stacks.
	 *
	 * @return item stacks
	 */
	@Nonnull List<ItemStack> getItemStacks();
}
