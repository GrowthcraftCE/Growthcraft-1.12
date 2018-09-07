package growthcraft.cellar.common.inventory.slot;

import growthcraft.cellar.shared.init.GrowthcraftCellarItems;
import growthcraft.core.shared.inventory.slot.GrowthcraftSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotBrewKettleLid extends GrowthcraftSlot {
	
	public SlotBrewKettleLid(IInventory inv, int x, int y, int z)
	{
		super(inv, x, y, z);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return GrowthcraftCellarItems.brewKettleLid.getItem().equals(stack.getItem());
	}
}
