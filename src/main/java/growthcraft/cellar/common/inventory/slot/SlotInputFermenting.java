package growthcraft.cellar.common.inventory.slot;

import growthcraft.cellar.shared.CellarRegistry;
import growthcraft.core.shared.CoreRegistry;
import growthcraft.core.shared.inventory.slot.SlotInput;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotInputFermenting extends SlotInput
{
	public SlotInputFermenting(IInventory inv, int x, int y, int z)
	{
		super(inv, x, y, z);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		if( SlotInputBarrelTap.isValidBarrelTap(stack) )
			return false;
		return CellarRegistry.instance().fermenting().isItemFermentationIngredient(stack);
	}
}
