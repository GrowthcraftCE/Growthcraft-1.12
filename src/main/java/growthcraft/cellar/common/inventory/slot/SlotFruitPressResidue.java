package growthcraft.cellar.common.inventory.slot;

import growthcraft.core.shared.inventory.slot.GrowthcraftSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotFruitPressResidue extends GrowthcraftSlot
{
	public SlotFruitPressResidue(IInventory inv, int x, int y, int z)
	{
		super(inv, x, y, z);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return true;
	}
}
