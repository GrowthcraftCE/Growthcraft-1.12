package growthcraft.milk.shared.processing.pancheon;

import net.minecraftforge.fluids.FluidStack;

public interface IPancheonRecipe {

	boolean isValidForRecipe(FluidStack stack);

	FluidStack getInputFluid();

	FluidStack getTopOutputFluid();
	
	FluidStack getBottomOutputFluid();
	
	int getTime();
}
