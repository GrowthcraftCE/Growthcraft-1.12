package growthcraft.core.shared.definition;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;

public interface IMultiFluidStacks extends IItemStackListProvider {
    /**
     * Returns the expected FluidStack amount
     *
     * @return expected fluid amount
     */
    int getAmount();

    /**
     * Returns a list of fluid stacks.
     *
     * @return the list of fluid stacks
     */
    List<FluidStack> getFluidStacks();

    /**
     * Determines if the mulit stack contains the specified fluid.
     *
     * @param fluid - the fluid to search for.
     * @return true, the multi stack contains the fluid, false otherwise
     */
    boolean containsFluid(@Nullable Fluid fluid);

    /**
     * Determines if the mulit stack contains the specified fluid stack.
     *
     * @param stack - the fluid stack being searched for.
     * @return true, the multi stack contains the stack, false otherwise
     */
    boolean containsFluidStack(@Nullable FluidStack stack);
}