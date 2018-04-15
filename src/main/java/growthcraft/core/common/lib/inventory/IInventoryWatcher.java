package growthcraft.core.common.lib.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public interface IInventoryWatcher
{
	void onInventoryChanged(IInventory inv, int index);
	void onItemDiscarded(IInventory inv, ItemStack stack, int index, int discardedAmount);
}
