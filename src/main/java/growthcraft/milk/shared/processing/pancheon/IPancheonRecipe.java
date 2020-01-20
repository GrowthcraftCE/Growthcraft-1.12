package growthcraft.milk.shared.processing.pancheon;

import growthcraft.cellar.shared.processing.common.IProcessingRecipeBase;
import net.minecraftforge.fluids.FluidStack;

public interface IPancheonRecipe extends IProcessingRecipeBase {

    boolean isValidForRecipe(FluidStack stack);

    FluidStack getInputFluid();

    FluidStack getTopOutputFluid();

    FluidStack getBottomOutputFluid();

    int getTime();
}
