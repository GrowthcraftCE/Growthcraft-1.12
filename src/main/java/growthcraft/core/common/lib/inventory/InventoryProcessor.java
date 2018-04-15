package growthcraft.core.common.lib.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.common.lib.definition.IMultiItemStacks;
import growthcraft.core.common.lib.item.ItemTest;
import growthcraft.core.common.lib.utils.ItemUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

/**
 * Utility class for handling some `painful` inventory operations
 */
public class InventoryProcessor
{
	private static final InventoryProcessor	inst = new InventoryProcessor();

	private InventoryProcessor() {}

	/**
	 * Retrieves the Inventory as a List of ItemStacks
	 *
	 * @param inv - inventory to get items from
	 * @return list of item stacks
	 */
	public List<ItemStack> getInventoryList(@Nonnull IInventory inv)
	{
		final List<ItemStack> result = new ArrayList<ItemStack>();
		for (int i = 0; i < inv.getSizeInventory(); ++i)
		{
			result.add(inv.getStackInSlot(i));
		}
		return result;
	}

	/**
	 * Shuffles all items in the inventory
	 *
	 * @param inv - inventory to shuffle
	 */
	public void shuffleInventory(@Nonnull IInventory inv, @Nonnull Random random)
	{
		final List<ItemStack> list = getInventoryList(inv);
		Collections.shuffle(list, random);
		for (int i = 0; i < inv.getSizeInventory(); ++i)
		{
			inv.setInventorySlotContents(i, list.get(i));
		}
	}

	/**
	 * Is the slot empty?
	 *
	 * @param inv - inventory to check
	 * @param slot - inventory slot to check
	 * @return true, the slot is empty, false otherwise
	 */
	public boolean slotIsEmpty(@Nonnull IInventory inv, int slot)
	{
		return ItemUtils.isEmpty(inv.getStackInSlot(slot));
	}

	/**
	 * Are the provided slots empty?
	 *
	 * @param inv - inventory to check
	 * @param slots - slots to check
	 * @return true, the slots are empty, false otherwise
	 */
	public boolean slotsAreEmpty(@Nonnull IInventory inv, @Nonnull int[] slots)
	{
		for (int slot : slots)
		{
			if (!ItemUtils.isEmpty(inv.getStackInSlot(slot)))
				return false;
		}
		return true;
	}

	/**
	 * Are the inventory's slots empty?
	 *
	 * @param inv - inventory to check
	 * @return true, the slots are empty, false otherwise
	 */
	public boolean slotsAreEmpty(@Nonnull IInventory inv)
	{
		for (int slot = 0; slot < inv.getSizeInventory(); ++slot)
		{
			if (!ItemUtils.isEmpty(inv.getStackInSlot(slot)))
				return false;
		}
		return true;
	}

	/**
	 * Merges the provided item into the inventory slot
	 *
	 * @param inv - inventory to merge in
	 * @param item - item stack to merge (mutable)
	 * @param slot - slot to merge in
	 * @return true, the item was merged, false otherwise
	 */
	public boolean mergeWithSlot(@Nonnull IInventory inv, @Nullable ItemStack item, int slot)
	{
		if (!ItemTest.isValid(item)) return false;

		final ItemStack existing = inv.getStackInSlot(slot);
		if (ItemUtils.isEmpty(existing))
		{
			inv.setInventorySlotContents(slot, item.copy());
			item.setCount( item.getCount() - MathHelper.clamp(item.getCount(), 0, item.getMaxStackSize()) );
		}
		else
		{
			if (existing.isItemEqual(item))
			{
				//final int maxStackSize = existing.getMaxStackSize();
				final int maxStackSize = inv.getInventoryStackLimit();
				final int newSize = MathHelper.clamp(existing.getCount() + item.getCount(), 0, maxStackSize);
				// nothing was added!
				if (newSize == existing.getCount())
				{
					return false;
				}
				else
				{
					final int consumed = newSize - existing.getCount();
					item.setCount( item.getCount() - consumed );
					existing.setCount( newSize );
					inv.setInventorySlotContents(slot, existing);
				}
			}
			else
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Attempts to merge the given ItemStack into the inventory
	 *
	 * @param inv inventory to merge to
	 * @param stack the stack to merge into the inventory
	 * @param slots the selected slots to merge with
	 * @param true, the stack was merged somehow, false otherwise
	 */
	public boolean mergeWithSlots(@Nonnull IInventory inv, @Nullable ItemStack stack, int[] slots)
	{
		if (ItemUtils.isEmpty(stack)) return false;
		boolean anythingMerged = false;
		for (int slot : slots)
		{
			if (stack.isEmpty()) break;
			anythingMerged |= mergeWithSlot(inv, stack, slot);
		}
		return anythingMerged;
	}

	/**
	 * Attempts to merge the given ItemStack into the inventory
	 *
	 * @param inv inventory to merge to
	 * @param stack the stack to merge into the inventory
	 * @param true, the stack was merged somehow, false otherwise
	 */
	public boolean mergeWithSlots(@Nonnull IInventory inv, @Nullable ItemStack stack)
	{
		if (ItemUtils.isEmpty(stack)) return false;
		boolean anythingMerged = false;
		for (int i = 0; i < inv.getSizeInventory(); ++i)
		{
			if (stack.isEmpty()) break;
			anythingMerged |= mergeWithSlot(inv, stack, i);
		}
		return anythingMerged;
	}

	/**
	 * Attempts to merge the given ItemStack into the inventory
	 *
	 * @param inv - inventory to merge to
	 * @param stack -
	 * @param remaining stack OR empty if the item was completely expeneded
	 */
	public ItemStack mergeWithSlotsStack(@Nonnull IInventory inv, @Nullable ItemStack stack)
	{
		if (ItemUtils.isEmpty(stack)) return ItemStack.EMPTY;

		for (int i = 0; i < inv.getSizeInventory(); ++i)
		{
			if (stack.isEmpty()) break;
			mergeWithSlot(inv, stack, i);
		}
		return stack.isEmpty() ? ItemStack.EMPTY : stack;
	}

	/**
	 * Attempts to clear the inventory slots
	 *
	 * @param inv - inventory to clear from
	 * @param src - slots to clear
	 * @return true, IF anything was remove from the slots, false otherwise
	 */
	public boolean clearSlots(@Nonnull IInventory inv, int[] src)
	{
		boolean clearedAnything = false;
		for (int slot : src)
		{
			clearedAnything |= !ItemUtils.isEmpty(inv.removeStackFromSlot(slot));
		}
		return clearedAnything;
	}

	/**
	 * Attempts to clear ALL inventory slots
	 *
	 * @param inv - inventory to clear from
	 * @return true, IF anything was remove from the slots, false otherwise
	 */
	public boolean clearSlots(@Nonnull IInventory inv)
	{
		boolean clearedAnything = false;
		for (int i = 0; i < inv.getSizeInventory(); ++i)
		{
			clearedAnything |= !ItemUtils.isEmpty(inv.removeStackFromSlot(i));
		}
		return clearedAnything;
	}

	/**
	 * Removes the item from the slot and returns it
	 *
	 * @param inv - inventory to yank from
	 * @param slot - slot to yank
	 * @return stack, ItemStack removed
	 */
	public ItemStack yankSlot(@Nonnull IInventory inv, int slot)
	{
		final ItemStack stack = inv.getStackInSlot(slot);
		if (!ItemUtils.isEmpty(stack))
		{
			return inv.decrStackSize(slot, stack.getCount());
		}
		return ItemStack.EMPTY;
	}

	protected boolean checkItemEquality(ItemStack actual, ItemStack expected)
	{
		if (ItemUtils.isEmpty(actual)) return false;
		if (!expected.isItemEqual(actual)) return false;
		return true;
	}

	/**
	 * Checks the given inventory and slot for an expected ItemStack, this will ignore stack sizes
	 *
	 * @param inv - inventory to check
	 * @param expected - itemstack expected
	 *   If the stack is empty, then the slot is expected to be empty as well
	 *   Otherwise it is required
	 * @param src - slot to check
	 */
	public boolean checkSlot(@Nonnull IInventory inv, @Nullable ItemStack expected, int src)
	{
		final ItemStack actual = inv.getStackInSlot(src);

		if (ItemUtils.isEmpty(expected))
		{
			// if the item is not needed, and is not available
			if (!ItemUtils.isEmpty(actual)) return false;
		}
		else
		{
			if (!checkItemEquality(actual, expected)) return false;
		}
		return true;
	}

	/**
	 * Checks the given inventory and slot for an expected ItemStack
	 *
	 * @param inv - inventory to check
	 * @param expected - itemstack expected
	 *   If the stack is empty, then the slot is expected to be empty as well
	 *   Otherwise it is required
	 * @param src - slot to check
	 */
	public boolean checkSlotAndSize(@Nonnull IInventory inv, @Nullable ItemStack expected, int src)
	{
		final ItemStack actual = inv.getStackInSlot(src);

		if (ItemUtils.isEmpty(expected))
		{
			// if the item is not needed, and is not available
			if (!ItemUtils.isEmpty(actual)) return false;
		}
		else
		{
			if (!checkItemEquality(actual, expected)) return false;
			if (actual.getCount() < expected.getCount()) return false;
		}
		return true;
	}

	/**
	 * Checks the given inventory and slot for an expected ItemStack
	 *
	 * @param inv - inventory to check
	 * @param expected - itemstack expected
	 *   If the stack is empty, then the slot is expected to be empty as well
	 *   Otherwise it is required
	 * @param src - slot to check
	 */
	public boolean checkSlotAndSize(@Nonnull IInventory inv, @Nullable IMultiItemStacks expected, int src)
	{
		final ItemStack actual = inv.getStackInSlot(src);

		if (ItemUtils.isEmpty(expected))
		{
			// if the item is not needed, and is not available
			if (!ItemUtils.isEmpty(actual)) return false;
		}
		else
		{
			if (ItemUtils.isEmpty(actual)) return false;
			if (!expected.containsItemStack(actual)) return false;
			if (actual.getCount() < expected.getStackSize()) return false;
		}
		return true;
	}

	/**
	 * Checks a slice of slots for the given items
	 *
	 * @param inv - inventory to check
	 * @param filter - itemstacks to look for
	 * @param from - a slice of slots to look in
	 * @return true, all the items in the filter are present in the inv, false otherwise
	 */
	public boolean checkSlotsAndSizes(@Nonnull IInventory inv, @Nonnull ItemStack[] filter, int[] from)
	{
		assert filter.length == from.length;

		for (int i = 0; i < filter.length; ++i)
		{
			if (!checkSlotAndSize(inv, filter[i], from[i])) return false;
		}
		return true;
	}

	/**
	 * Checks a slice of slots for the given items
	 *
	 * @param inv - inventory to check
	 * @param filter - itemstacks to look for
	 * @param from - a slice of slots to look in
	 * @return true, all the items in the filter are present in the inv, false otherwise
	 */
	public boolean checkSlotsAndSizes(@Nonnull IInventory inv, @Nonnull IMultiItemStacks[] filter, int[] from)
	{
		assert filter.length == from.length;

		for (int i = 0; i < filter.length; ++i)
		{
			if (!checkSlotAndSize(inv, filter[i], from[i])) return false;
		}
		return true;
	}

	/**
	 * Checks a slice of slots for the given items
	 *
	 * @param inv - inventory to check
	 * @param filter - itemstacks to look for
	 * @param from - a slice of slots to look in
	 * @return true, all the items in the filter are present in the inv, false otherwise
	 */
	@SuppressWarnings({"rawtypes"})
	public boolean checkSlotsAndSizes(@Nonnull IInventory inv, @Nonnull List filter, int[] from)
	{
		assert filter.size() == from.length;

		for (int i = 0; i < filter.size(); ++i)
		{
			final Object filterObject = filter.get(i);
			if (filterObject instanceof IMultiItemStacks)
			{
				if (!checkSlotAndSize(inv, (IMultiItemStacks)filterObject, from[i])) return false;
			}
			else if (filterObject instanceof ItemStack || filterObject == null)
			{
				if (!checkSlotAndSize(inv, (ItemStack)filterObject, from[i])) return false;
			}
			else
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * @return true if moved, false otherwise
	 */
	public boolean moveToSlots(@Nonnull IInventory inv, @Nonnull ItemStack[] filter, int[] from, int[] to)
	{
		assert filter.length == from.length;
		assert filter.length == to.length;

		// first ensure that each stack in the input has the item and enough of them
		if (!checkSlotsAndSizes(inv, filter, from)) return false;

		for (int i = 0; i < filter.length; ++i)
		{
			if (!ItemUtils.isEmpty(filter[i]))
			{
				final ItemStack stack = inv.decrStackSize(from[i], filter[i].getCount());
				inv.setInventorySlotContents(to[i], stack);
			}
		}
		return true;
	}

	/**
	 * @return true if moved, false otherwise
	 */
	public boolean moveToSlots(@Nonnull IInventory inv, @Nonnull IMultiItemStacks[] filter, int[] from, int[] to)
	{
		assert filter.length == from.length;
		assert filter.length == to.length;

		// first ensure that each stack in the input has the item and enough of them
		if (!checkSlotsAndSizes(inv, filter, from)) return false;

		for (int i = 0; i < filter.length; ++i)
		{
			if (!ItemUtils.isEmpty(filter[i]))
			{
				final ItemStack stack = inv.decrStackSize(from[i], filter[i].getStackSize());
				inv.setInventorySlotContents(to[i], stack);
			}
		}
		return true;
	}

	/**
	 * Moves item from slot `from` to slot `to`
	 *
	 * @param inv - inventory to move items in
	 * @param filter - item to check for
	 * @param from - slot to move from
	 * @param to - slot to move to
	 * @return true, the item was moved, false otherwise
	 */
	public boolean moveToSlot(@Nonnull IInventory inv, @Nonnull ItemStack filter, int from, int to)
	{
		// first ensure that each stack in the input has the item and enough of them
		if (!checkSlotAndSize(inv, filter, from)) return false;

		final ItemStack stack = inv.decrStackSize(from, filter.getCount());
		inv.setInventorySlotContents(to, stack);

		return true;
	}

	/**
	 * @param inv - source inventory to search
	 * @param query - item to search for
	 * @return -1 no slot was found, slot index otherwise
	 */
	public int findItemSlot(@Nonnull IInventory inv, @Nonnull Item query)
	{
		for (int i = 0; i < inv.getSizeInventory(); ++i)
		{
			final ItemStack stack = inv.getStackInSlot(i);
			if (!ItemUtils.isEmpty(stack))
			{
				if (stack.getItem() == query) return i;
			}
		}
		return -1;
	}

	/**
	 * @param inv - source inventory to search
	 * @param query - itemstack to search for
	 * @return -1 no slot was found, slot index otherwise
	 */
	public int findItemSlot(@Nonnull IInventory inv, @Nonnull ItemStack query)
	{
		for (int i = 0; i < inv.getSizeInventory(); ++i)
		{
			final ItemStack stack = inv.getStackInSlot(i);
			if (ItemUtils.isEmpty(query) && ItemUtils.isEmpty(stack) )
			{
				return i;
			}
			else if (!ItemUtils.isEmpty(query) && !ItemUtils.isEmpty(stack))
			{
				if (query.isItemEqual(stack)) return i;
			}
		}
		return -1;
	}

	/**
	 * Search the inventory for all the items given, this method will skip
	 * slots that have been used before.
	 *
	 * @param inv - inventory to search
	 * @param expected - item stacks to search for
	 * @param slotsSlice - slots to check in, if null, the entire inventory is searched
	 * @return slot ids
	 */
	@SuppressWarnings({"rawtypes"})
	public int[] findItemSlots(@Nonnull IInventory inv, @Nonnull List expected, @Nonnull int[] slotsSlice)
	{
		final boolean[] usedSlots = new boolean[inv.getSizeInventory()];
		final int[] slots = new int[expected.size()];
		int i = 0;
		final int invSize = slotsSlice != null ? slotsSlice.length : inv.getSizeInventory();
		for (Object expectedStack : expected)
		{
			slots[i] = -1;
			for (int index = 0; index < invSize; ++index)
			{
				final int slotIndex = slotsSlice != null ? slotsSlice[index] : index;
				boolean foundItem = false;
				if (expectedStack instanceof IMultiItemStacks)
				{
					foundItem = checkSlotAndSize(inv, (IMultiItemStacks)expectedStack, slotIndex);
				}
				else if (expectedStack instanceof ItemStack)
				{
					foundItem = checkSlotAndSize(inv, (ItemStack)expectedStack, slotIndex);
				}

				if (foundItem)
				{
					if (usedSlots[slotIndex]) continue;
					usedSlots[slotIndex] = true;
					slots[i] = slotIndex;
					break;
				}
			}
			i++;
		}
		return slots;
	}

	/**
	 * Search the inventory for all the items given, this method will skip
	 * slots that have been used before.
	 *
	 * @param inv - inventory to search
	 * @param expected - item stacks to search for
	 * @return slot ids
	 */
	@SuppressWarnings({"rawtypes"})
	public int[] findItemSlots(@Nonnull IInventory inv, @Nonnull List expected)
	{
		return findItemSlots(inv, expected, null);
	}

	/**
	 * Finds the next empty slot starting from the beginning
	 *
	 * @param inv - source inventory to search
	 * @return -1 no slot was found, slot index otherwise
	 */
	public int findNextEmpty(@Nonnull IInventory inv)
	{
		for (int i = 0; i < inv.getSizeInventory(); ++i)
		{
			if (ItemUtils.isEmpty(inv.getStackInSlot(i))) return i;
		}
		return -1;
	}

	/**
	 * Finds the next empty slot starting from the end of the inventory
	 *
	 * @param inv - source inventory to search
	 * @return -1 no slot was found, slot index otherwise
	 */
	public int findNextEmptyFromEnd(@Nonnull IInventory inv)
	{
		for (int i = inv.getSizeInventory() - 1; i >= 0; --i)
		{
			if (ItemUtils.isEmpty(inv.getStackInSlot(i))) return i;
		}
		return -1;
	}

	/**
	 * Finds the next slot with an item present, starting from the beginning
	 *
	 * @param inv - source inventory to search
	 * @return -1 no slot was found, slot index otherwise
	 */
	public int findNextPresent(@Nonnull IInventory inv)
	{
		for (int i = 0; i < inv.getSizeInventory(); ++i)
		{
			if (!ItemUtils.isEmpty(inv.getStackInSlot(i))) return i;
		}
		return -1;
	}

	/**
	 * Finds the next slot with an item present starting from the end of the inventory
	 *
	 * @param inv - source inventory to search
	 * @return -1 no slot was found, slot index otherwise
	 */
	public int findNextPresentFromEnd(@Nonnull IInventory inv)
	{
		for (int i = inv.getSizeInventory() - 1; i >= 0; --i)
		{
			if (!ItemUtils.isEmpty(inv.getStackInSlot(i))) return i;
		}
		return -1;
	}

	/**
	 * Checks a slice of slots for the given items, unordered
	 *
	 * @param inv - inventory to check
	 * @param filter - itemstacks to look for
	 * @param from - a slice of slots to look in
	 * @return true, all the items in the filter are present in the inv, false otherwise
	 */
	@SuppressWarnings({"rawtypes"})
	public boolean checkSlotsAndSizesUnordered(@Nonnull IInventory inv, @Nonnull List filter, int[] from)
	{
		assert filter.size() == from.length;
		final int[] slots = findItemSlots(inv, filter, from);
		for (int i : slots)
		{
			if (i < 0) return false;
		}
		return true;
	}

	/**
	 * Checks a slice of slots for the given items, unordered
	 *
	 * @param inv - inventory to check
	 * @param filter - itemstacks to look for
	 * @param from - a slice of slots to look in
	 * @return true, all the items in the filter are present in the inv, false otherwise
	 */
	public boolean checkSlotsAndSizesUnordered(@Nonnull IInventory inv, @Nonnull ItemStack[] filter, int[] from)
	{
		return checkSlotsAndSizesUnordered(inv, Arrays.asList(filter), from);
	}

	/**
	 * Checks a slice of slots for the given items, unordered
	 *
	 * @param inv - inventory to check
	 * @param filter - itemstacks to look for
	 * @param from - a slice of slots to look in
	 * @return true, all the items in the filter are present in the inv, false otherwise
	 */
	public boolean checkSlotsAndSizesUnordered(@Nonnull IInventory inv, @Nonnull IMultiItemStacks[] filter, int[] from)
	{
		return checkSlotsAndSizesUnordered(inv, Arrays.asList(filter), from);
	}

	/**
	 * @param inv - the inventory to check against
	 * @param slots - slots to check
	 * @return true, all slots are valid (0 and above), false otherwise
	 */
	public boolean slotsAreValid(@Nonnull IInventory inv, @Nonnull int[] slots)
	{
		for (int slot : slots)
		{
			if (slot < 0) return false;
			if (slot >= inv.getSizeInventory()) return false;
		}
		return true;
	}

	/**
	 * Consume items in the inventory.
	 *
	 * @param inv - source inventory to consume items from
	 * @param expected - items to consume in inventory
	 * @param slots - slots to consume from
	 * @return true, items where consumed, false otherwise
	 */
	@SuppressWarnings({"rawtypes"})
	public boolean consumeItemsInSlots(@Nonnull IInventory inv, @Nonnull List expected, @Nonnull int[] slots)
	{
		for (int i = 0; i < slots.length; ++i)
		{
			final int slot = slots[i];
			final Object expectedStack = expected.get(i);
			if (expectedStack != null)
			{
				if (expectedStack instanceof IMultiItemStacks)
				{
					inv.decrStackSize(slot, ((IMultiItemStacks)expectedStack).getStackSize());
				}
				else if (expectedStack instanceof ItemStack)
				{
					inv.decrStackSize(slot, ((ItemStack)expectedStack).getCount());
				}
			}
		}
		return true;
	}

	/**
	 * Consume items in the inventory, this method will search for the slots.
	 *
	 * @param inv - source inventory to consume items from
	 * @param expected - items to consume in inventory
	 * @return true, items where consumed, false otherwise
	 */
	@SuppressWarnings({"rawtypes"})
	public boolean consumeItems(@Nonnull IInventory inv, @Nonnull List expected)
	{
		final int[] slots = findItemSlots(inv, expected);
		if (!checkSlotsAndSizes(inv, expected, slots)) return false;
		return consumeItemsInSlots(inv, expected, slots);
	}

	/**
	 * A simple implementation of canInsertItem, this will check that the slot is
	 * either empty, or has the SAME item to insert.
	 *
	 * @return true, the item can be inserted, false otherwise
	 */
	public boolean canInsertItem(@Nonnull IInventory inv, @Nullable ItemStack stack, int slot)
	{
		final ItemStack expected = inv.getStackInSlot(slot);
		if (!ItemUtils.isEmpty(expected))
		{
			if (!checkSlot(inv, stack, slot)) return false;
			if (expected.getCount() >= inv.getInventoryStackLimit()) return false;
			final int estSize = expected.getCount() + stack.getCount();
			return estSize <= inv.getInventoryStackLimit();
		}
		return true;
	}

	/**
	 * A simple implementation of canExtractItem, this will check that the slot
	 * contains the expected item.
	 *
	 * @return true, the item can be extracted, false otherwise
	 */
	public boolean canExtractItem(@Nonnull IInventory inv, @Nullable ItemStack stack, int slot)
	{
		final ItemStack expected = inv.getStackInSlot(slot);
		if (ItemUtils.isEmpty(expected)) return false;
		return checkSlotAndSize(inv, stack, slot);
	}

	public static InventoryProcessor instance()
	{
		return inst;
	}
}
