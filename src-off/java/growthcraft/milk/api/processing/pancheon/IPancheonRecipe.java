package growthcraft.milk.api.processing.pancheon;

import net.minecraftforge.fluids.FluidStack;

public interface IPancheonRecipe {

	boolean isValidForRecipe(FluidStack stack);

	FluidStack getInputFluid();

	FluidStack getTopOutputFluid();
	
	FluidStack getBottomOutputFluid();
	
	int getTime();
}
