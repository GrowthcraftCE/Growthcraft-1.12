package growthcraft.milk.shared.definition;

import growthcraft.core.shared.definition.IFluidStackFactory;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public interface ICheeseFluidStackFactory extends IFluidStackFactory {
    @Override
    default public FluidStack asFluidStack() {
        return asFluidStack(1);
    }

    default Fluid getFluid() {
        return asFluidStack().getFluid();
    }
}
