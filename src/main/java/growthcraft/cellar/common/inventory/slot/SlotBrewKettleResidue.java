package growthcraft.cellar.common.inventory.slot;

import growthcraft.core.shared.inventory.slot.GrowthcraftSlot;
import growthcraft.milk.common.item.ItemStarterCulture;
import growthcraft.rice.common.item.ItemFoodRice;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.DyeUtils;

public class SlotBrewKettleResidue extends GrowthcraftSlot {
    public SlotBrewKettleResidue(IInventory inv, int x, int y, int z) {
        super(inv, x, y, z);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {

        return DyeUtils.isDye(stack) || stack.getItem() instanceof ItemStarterCulture || stack.getItem() instanceof ItemFoodRice;
    }
}
