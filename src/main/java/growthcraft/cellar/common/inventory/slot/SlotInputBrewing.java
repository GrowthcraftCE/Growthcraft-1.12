package growthcraft.cellar.common.inventory.slot;

import growthcraft.cellar.shared.CellarRegistry;
import growthcraft.core.shared.inventory.slot.SlotInput;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotInputBrewing extends SlotInput {
    public SlotInputBrewing(IInventory inv, int x, int y, int z) {
        super(inv, x, y, z);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return CellarRegistry.instance().brewing().isItemBrewingIngredient(stack);
    }
}
