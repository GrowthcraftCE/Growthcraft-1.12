package growthcraft.milk.api.definition;

import growthcraft.core.api.definition.IFluidStackFactory;
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
