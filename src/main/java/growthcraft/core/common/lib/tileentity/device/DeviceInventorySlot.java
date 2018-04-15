package growthcraft.core.common.lib.tileentity.device;

import growthcraft.core.common.lib.definition.IMultiItemStacks;
import growthcraft.core.common.lib.inventory.InventoryProcessor;
import growthcraft.core.common.lib.item.ItemUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class DeviceInventorySlot
{
	public final int index;
	private IInventory inventory;

	public DeviceInventorySlot(IInventory inv, int idx)
	{
		this.inventory = inv;
		this.index = idx;
	}

	/**
	 * Returns the current item in the slot, if any
	 *
	 * @return stack
	 */
	public ItemStack get()
	{
		return inventory.getStackInSlot(index);
	}

	/**
	 * The size of the ItemStack in the slot, if none is present the size is 0
	 *
	 * @return size
	 */
	public int getSize()
	{
		final ItemStack stack = get();
		if (ItemUtils.isEmpty(stack)) return 0;
		return stack.getCount();
	}

	/**
	 * Returns the total capacity of the slot.
	 * If an item occupies the slot then the stack's maxStackSize is returned
	 *
	 * @return capacity
	 */
	public int getCapacity()
	{
		final ItemStack stack = get();
		if (!ItemUtils.isEmpty(stack))
		{
			return stack.getMaxStackSize();
		}
		return inventory.getInventoryStackLimit();
	}

	/**
	 * Returns the current available capacity, that is the maxCapactity - current size
	 *
	 * @return available capacity
	 */
	public int getAvailableCapacity()
	{
		return getCapacity() - getSize();
	}

	/**
	 * Set the stack for this slot
	 *
	 * @param newStack the stack to set
	 */
	public void set(ItemStack newStack)
	{
		inventory.setInventorySlotContents(index, newStack);
	}

	/**
	 * Is there any VALID item in this slot, where valid is an item with
	 * a stackSize greater than 0
	 *
	 * @return true there is content, false otherwise
	 */
	public boolean hasContent()
	{
		return getSize() > 0;
	}

	/**
	 * Does the slot not have an item?
	 *
	 * @return true, there is no item, or no valid item in the slot
	 */
	public boolean isEmpty()
	{
		return !hasContent();
	}

	/**
	 * Is this slot full?
	 *
	 * @return true, the slot is full, false otherwise
	 */
	public boolean isFull()
	{
		return getAvailableCapacity() <= 0;
	}

	/**
	 * Does the provided stack match the one in the slot?
	 *
	 * @param stack - item stack to test
	 * @return true, it has the same item, false otherwise
	 */
	public boolean hasMatching(ItemStack stack)
	{
		final ItemStack s = get();
		if (ItemUtils.isEmpty(stack))
		{
			return ItemUtils.isEmpty(s);
		}
		else if (!ItemUtils.isEmpty(stack) && !ItemUtils.isEmpty(s))
		{
			return stack.isItemEqual(s);
		}
		return false;
	}

	/**
	 * Does the provided multi stack match the one in the slot?
	 *
	 * @param stack - multi item stack to test
	 * @return true, it contains the an item from the stack, false otherwise
	 */
	public boolean hasMatching(IMultiItemStacks stack)
	{
		final ItemStack s = get();
		if (ItemUtils.isEmpty(stack))
		{
			return ItemUtils.isEmpty(s);
		}
		else if (!ItemUtils.isEmpty(stack) && !ItemUtils.isEmpty(s))
		{
			return stack.containsItemStack(s);
		}
		return false;
	}

	/**
	 * Does the slot have the same item, and the capacity to hold the stack?
	 *
	 * @param stack - item stack to test
	 * @return true, it has the same item and has capacity, false otherwise
	 */
	public boolean hasMatchingWithCapacity(ItemStack stack)
	{
		if (ItemUtils.isEmpty(stack)) return true;
		if (!isEmpty())
		{
			if (!hasMatching(stack)) return false;
		}
		return getAvailableCapacity() >= stack.getCount();
	}

	/**
	 * This is a variation of hasMatchingWithCapacity, this version will accept
	 * the stack if the internal stack is null unlike the former.
	 *
	 * @param stack - item stack to test
	 * @return true, the slot has capacity for the provided stack
	 */
	public boolean hasCapacityFor(ItemStack stack)
	{
		if (ItemUtils.isEmpty(stack)) return true;
		if (hasContent())
		{
			if (!hasMatching(stack)) return false;
		}
		return getAvailableCapacity() >= stack.getCount();
	}

	/**
	 * @param stack - item stack to test
	 * @return true, the item matches and has enough in slot
	 */
	public boolean hasEnough(ItemStack stack)
	{
		if (hasMatching(stack))
		{
			if (!ItemUtils.isEmpty(stack))
			{
				final ItemStack s = get();
				if (s.getCount() >= stack.getCount())
				{
					return true;
				}
			}
			else
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * @param stack - item stack to test
	 * @return true, the item matches and has enough in slot
	 */
	public boolean hasEnough(IMultiItemStacks stack)
	{
		if (hasMatching(stack))
		{
			if (!ItemUtils.isEmpty(stack))
			{
				final ItemStack s = get();
				if (s.getCount() >= stack.getStackSize())
				{
					return true;
				}
			}
			else
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * @param count - how many items to consume in the slot?
	 * @param result stack
	 */
	public ItemStack consume(int count)
	{
		return inventory.decrStackSize(index, count);
	}

	public ItemStack consume(ItemStack stack)
	{
		if (hasEnough(stack)) return consume(stack.getCount());
		return ItemStack.EMPTY;
	}

	public ItemStack consume(IMultiItemStacks stack)
	{
		if (hasEnough(stack)) return consume(stack.getStackSize());
		return ItemStack.EMPTY;
	}

	public ItemStack increaseStack(ItemStack stack)
	{
		final ItemStack result = ItemUtils.mergeStacks(get(), stack);
		if (!ItemUtils.isEmpty(result))
		{
			set(result);
//			return result;
		}
//		return ItemStack.EMPTY;
		return result;
	}

	/**
	 * Removes and returns the item in the slot
	 *
	 * @return stack if any
	 */
	public ItemStack yank()
	{
		return InventoryProcessor.instance().yankSlot(inventory, index);
	}
}
