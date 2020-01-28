package growthcraft.cellar.shared.processing.common;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public interface IProcessingRecipe extends IProcessingRecipeBase{
    Residue getResidue();

    boolean hasResidue();

    Fluid getFluid();

    FluidStack getFluidStack();

    int getAmount();

    FluidStack asFluidStack(int size);

    FluidStack asFluidStack();
}
