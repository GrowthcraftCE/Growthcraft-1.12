package growthcraft.core.shared.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidContainerItem {
    FluidStack getFluidStack(ItemStack stack);
}
