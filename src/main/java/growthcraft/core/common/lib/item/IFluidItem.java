package growthcraft.core.common.lib.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidItem
{
	FluidStack getFluidStack(ItemStack stack);
}
