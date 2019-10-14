package growthcraft.milk.shared.processing.cheesevat;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.shared.definition.IMultiFluidStacks;
import growthcraft.core.shared.definition.IMultiItemStacks;
import growthcraft.core.shared.fluids.FluidTest;
import growthcraft.core.shared.item.ItemTest;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class CheeseVatRecipe implements ICheeseVatRecipe {
    private List<FluidStack> outputFluids;
    private List<ItemStack> outputItems;
    private List<IMultiFluidStacks> inputFluids;
    private List<IMultiItemStacks> inputItems;

    public CheeseVatRecipe(List<FluidStack> pOutputFluids, List<ItemStack> pOutputItems, List<IMultiFluidStacks> pInputFluids, List<IMultiItemStacks> pInputItems) {
        this.outputFluids = pOutputFluids;
        this.outputItems = pOutputItems;
        this.inputFluids = pInputFluids;
        this.inputItems = pInputItems;
    }

    @Override
    public List<FluidStack> getOutputFluidStacks() {
        return outputFluids;
    }

    @Override
    public List<ItemStack> getOutputItemStacks() {
        return outputItems;
    }

    @Override
    public List<IMultiFluidStacks> getInputFluidStacks() {
        return inputFluids;
    }

    @Override
    public List<IMultiItemStacks> getInputItemStacks() {
        return inputItems;
    }

    @Override
    public boolean isMatchingRecipe(@Nonnull List<FluidStack> fluids, @Nonnull List<ItemStack> items) {
        if (!FluidTest.isValidAndExpected(inputFluids, fluids)) return false;
        if (!ItemTest.containsExpectedItemsUnordered(inputItems, items)) return false;
        return true;
    }

    @Override
    public boolean isFluidIngredient(@Nullable Fluid fluid) {
        for (IMultiFluidStacks stack : inputFluids) {
            if (stack.containsFluid(fluid)) return true;
        }
        return false;
    }

    @Override
    public boolean isFluidIngredient(@Nullable FluidStack fluidStack) {
        for (IMultiFluidStacks stack : inputFluids) {
            if (stack.containsFluidStack(fluidStack)) return true;
        }
        return false;
    }

    @Override
    public boolean isItemIngredient(@Nullable ItemStack itemStack) {
        for (IMultiItemStacks item : inputItems) {
            if (item.containsItemStack(itemStack)) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("CheeseVatRecipe(output_fluids: %s, output_items: %s, input_fluids: %s, input_items: %s)", outputFluids, outputItems, inputFluids, inputItems);
    }
}
