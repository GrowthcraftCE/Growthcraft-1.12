package growthcraft.core.common.lib.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidContainerItem
{
	FluidStack getFluidStack(ItemStack stack);
}
