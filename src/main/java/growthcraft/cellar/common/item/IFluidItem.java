package growthcraft.cellar.common.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public interface IFluidItem
{
	Fluid getFluid(ItemStack stack);
}
