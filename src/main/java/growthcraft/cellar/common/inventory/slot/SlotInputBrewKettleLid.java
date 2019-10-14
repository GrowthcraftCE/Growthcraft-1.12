package growthcraft.cellar.common.inventory.slot;

import growthcraft.cellar.shared.init.GrowthcraftCellarItems;
import growthcraft.core.shared.inventory.slot.GrowthcraftSlot;
import growthcraft.core.shared.inventory.slot.SlotInput;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotInputBrewKettleLid extends SlotInput {

    public SlotInputBrewKettleLid(IInventory inv, int x, int y, int z) {
        super(inv, x, y, z);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return GrowthcraftCellarItems.brewKettleLid.getItem().equals(stack.getItem());
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
