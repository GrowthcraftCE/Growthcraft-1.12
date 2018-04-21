package growthcraft.cellar.common.inventory.slot;

import growthcraft.cellar.common.lib.CellarRegistry;
import growthcraft.core.shared.inventory.slot.SlotInput;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotInputPressing extends SlotInput
{
	public SlotInputPressing(IInventory inv, int x, int y, int z)
	{
		super(inv, x, y, z);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return CellarRegistry.instance().pressing().hasPressingRecipe(stack);
	}
}
