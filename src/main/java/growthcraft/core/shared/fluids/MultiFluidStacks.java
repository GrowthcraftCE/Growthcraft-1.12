package growthcraft.core.shared.fluids;

import growthcraft.core.shared.definition.IMultiFluidStacks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultiFluidStacks implements IMultiFluidStacks {
    private List<FluidStack> fluidStacks;
    private transient List<ItemStack> fluidContainers;

    public MultiFluidStacks(@Nonnull List<FluidStack> stacks) {
        this.fluidStacks = stacks;
    }

    public MultiFluidStacks(@Nonnull FluidStack... stacks) {
        this(Arrays.asList(stacks));
    }

    public List<String> getNames() {
        final List<String> result = new ArrayList<String>();
        for (FluidStack stack : fluidStacks) {
            final Fluid fluid = stack.getFluid();
            if (fluid != null) {
                result.add(fluid.getName());
            }
        }
        return result;
    }

    @Override
    public int getAmount() {
        // Resturn the amount in the first fluid stack.
        return fluidStacks.get(0).amount > 0 ? fluidStacks.get(0).amount : 0;
    }

    @Override
    public List<FluidStack> getFluidStacks() {
        return fluidStacks;
    }

    @Override
    public boolean containsFluid(@Nullable Fluid expectedFluid) {
        if (FluidTest.isValid(expectedFluid)) {
            for (FluidStack content : getFluidStacks()) {
                if (content.getFluid() == expectedFluid) return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsFluidStack(@Nullable FluidStack stack) {
        if (!FluidTest.isValid(stack)) return false;
        for (FluidStack content : getFluidStacks()) {
            if (content.isFluidEqual(stack)) return true;
        }
        return false;
    }

    @Override
    public List<ItemStack> getItemStacks() {
        if (fluidContainers == null) {
            fluidContainers = GrowthcraftFluidUtils.getFluidContainers(getFluidStacks());
        }

        return fluidContainers;
    }
}
