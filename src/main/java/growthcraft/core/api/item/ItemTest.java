package growthcraft.core.api.item;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.api.definition.IMultiItemStacks;
import growthcraft.core.utils.ItemUtils;
import net.minecraft.item.ItemStack;

public class ItemTest
{
	// TODO: Code duplications with another classes like ItemUtils and InventoryProcessor existing. Fix it.
	
	private ItemTest() {}

	public static boolean isValid(@Nonnull ItemStack stack)
	{
		if (stack == null) return false;
		if (stack.getItem() == null) return false;
		if (stack.isEmpty()) return false;
		return true;
	}

	public static boolean itemMatches(@Nullable IMultiItemStacks expected, @Nullable ItemStack actual)
	{
		if (ItemUtils.isEmpty(expected))
		{
			return ItemUtils.isEmpty(actual);
		}
		else
		{
			if (!ItemUtils.isEmpty(actual))
			{
				return expected.containsItemStack(actual);
			}
		}
		return false;
	}

	public static boolean itemMatches(@Nullable ItemStack expected, @Nullable ItemStack actual)
	{
		if (ItemUtils.isEmpty(expected))
		{
			return ItemUtils.isEmpty(actual);
		}
		else
		{
			if (!ItemUtils.isEmpty(actual))
			{
				if (expected.getItemDamage() == ItemKey.WILDCARD_VALUE)
				{
					return expected.getItem() == actual.getItem();
				}
				else
				{
					return expected.isItemEqual(actual);
				}
			}
		}
		return false;
	}

	public static boolean areStacksEqual(@Nullable IMultiItemStacks expected, @Nullable ItemStack actual)
	{
		if (!itemMatches(expected, actual)) return false;
		return actual.getCount() == expected.getStackSize();
	}

	public static boolean areStacksEqual(@Nullable ItemStack expected, @Nullable ItemStack actual)
	{
		if (!itemMatches(expected, actual)) return false;
		return actual.getCount() == expected.getCount();
	}

	public static boolean hasEnough(@Nullable IMultiItemStacks expected, @Nullable ItemStack actual)
	{
		if (!itemMatches(expected, actual)) return false;
		if (actual.getCount() < expected.getStackSize()) return false;
		return true;
	}

	public static boolean hasEnough(@Nullable ItemStack expected, @Nullable ItemStack actual)
	{
		if (!itemMatches(expected, actual)) return false;
		if (actual.getCount() < expected.getCount()) return false;
		return true;
	}

	@SuppressWarnings({"rawtypes"})
	public static boolean isValidAndExpected(@Nonnull List expectedItems, @Nonnull List<ItemStack> givenItems)
	{
		if (expectedItems.size() != givenItems.size()) return false;
		for (int i = 0; i < expectedItems.size(); ++i)
		{
			final Object expected = expectedItems.get(i);
			final ItemStack actual = givenItems.get(i);
			if (expected != null)
			{
				if (!isValid(actual)) return false;
				if (expected instanceof ItemStack)
				{
					if (!((ItemStack)expected).isItemEqual(actual)) return false;
				}
				else if (expected instanceof IMultiItemStacks)
				{
					if (!((IMultiItemStacks)expected).containsItemStack(actual)) return false;
				}
				else
				{
					return false;
				}
			}
			else
			{
				if (!ItemUtils.isEmpty(actual)) return false;
			}
		}
		return true;
	}

	@SuppressWarnings({"rawtypes"})
	public static boolean containsExpectedItemsUnordered(@Nonnull List expectedItems, @Nonnull List<ItemStack> givenItems)
	{
		final boolean[] usedSlots = new boolean[givenItems.size()];
		for (Object expectedStack : expectedItems)
		{
			boolean found = false;
			for (int index = 0; index < usedSlots.length; ++index)
			{
				if (usedSlots[index]) continue;
				if (expectedStack instanceof IMultiItemStacks)
				{
					found = itemMatches((IMultiItemStacks)expectedStack, givenItems.get(index));
				}
				else if (expectedStack instanceof ItemStack)
				{
					found = itemMatches((ItemStack)expectedStack, givenItems.get(index));
				}
				else if (expectedStack == null)
				{
					found = givenItems.get(index) == null;
				}

				if (found)
				{
					usedSlots[index] = true;
					break;
				}
			}
			if (!found) return false;
		}
		return true;
	}
}
