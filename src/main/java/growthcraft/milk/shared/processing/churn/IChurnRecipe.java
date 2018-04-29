package growthcraft.milk.shared.processing.churn;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IChurnRecipe {

	boolean isValidForRecipe(FluidStack stack);
	FluidStack getInputFluidStack();
	FluidStack getOutputFluidStack();
	ItemStack getOutputItemStack();
	int getChurns();

}
