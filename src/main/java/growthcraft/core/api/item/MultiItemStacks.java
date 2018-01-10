package growthcraft.core.api.item;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.api.definition.IMultiItemStacks;
import net.minecraft.item.ItemStack;

public class MultiItemStacks implements IMultiItemStacks
{
	private List<ItemStack> itemStacks;

	public MultiItemStacks(@Nonnull List<ItemStack> stacks)
	{
		this.itemStacks = stacks;
	}

	public MultiItemStacks(@Nonnull ItemStack... stacks)
	{
		this(Arrays.asList(stacks));
	}

	public MultiItemStacks copy()
	{
		return new MultiItemStacks(itemStacks);
	}

	@Override
	public boolean isEmpty()
	{
		return itemStacks.isEmpty();
	}

	@Override
	public int getStackSize()
	{
		for (ItemStack stack : itemStacks)
		{
			return stack.getCount();
		}
		return 0;
	}

	@Override
	public List<ItemStack> getItemStacks()
	{
		return itemStacks;
	}

	@Override
	public boolean containsItemStack(@Nullable ItemStack stack)
	{
		if (!ItemTest.isValid(stack)) return false;
		for (ItemStack content : getItemStacks())
		{
			if (content.isItemEqual(stack)) return true;
		}
		return false;
	}
}
