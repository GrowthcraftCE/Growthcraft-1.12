package growthcraft.cellar.common.inventory.slot;

import growthcraft.cellar.shared.init.GrowthcraftCellarItems;
import growthcraft.core.shared.inventory.slot.SlotInput;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotInputBarrelTap extends SlotInput {

	public SlotInputBarrelTap(IInventory inv, int index, int x, int y) {
		super(inv, index, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return isValidBarrelTap(stack);
	}
	
	@Override
    public int getSlotStackLimit()
    {
        return 1;
    }
	
	public static boolean isValidBarrelTap(ItemStack stack) {
		return GrowthcraftCellarItems.barrelTap.getItem().equals(stack.getItem());
	}
}
