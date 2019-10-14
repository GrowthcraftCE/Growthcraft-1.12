package growthcraft.cellar.shared.processing.common;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public interface IProcessingRecipe {
    Residue getResidue();

    boolean hasResidue();

    int getTime();

    Fluid getFluid();

    FluidStack getFluidStack();

    int getAmount();

    FluidStack asFluidStack(int size);

    FluidStack asFluidStack();
}
