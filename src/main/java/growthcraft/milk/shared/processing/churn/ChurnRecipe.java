package growthcraft.milk.shared.processing.churn;

import growthcraft.core.shared.fluids.FluidFormatString;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class ChurnRecipe implements IChurnRecipe {
    private FluidStack inputFluid;
    private FluidStack outputFluid;
    private ItemStack outputItem;
    private int churns;

    public ChurnRecipe(FluidStack inFluid, FluidStack outFluid, ItemStack outItem, int ch) {
        this.inputFluid = inFluid;
        this.outputFluid = outFluid;
        this.outputItem = outItem;
        this.churns = ch;
    }

    @Override
    public boolean isValidForRecipe(FluidStack stack) {
        if (stack == null) return false;
        if (!inputFluid.isFluidEqual(stack)) return false;
        if (stack.amount < inputFluid.amount) return false;
        return true;
    }

    @Override
    public FluidStack getInputFluidStack() {
        return inputFluid;
    }

    @Override
    public FluidStack getOutputFluidStack() {
        return outputFluid;
    }

    @Override
    public ItemStack getOutputItemStack() {
        return outputItem;
    }

    @Override
    public int getChurns() {
        return churns;
    }

    @Override
    public String toString() {
        return String.format("ChurnRecipe(`%s` / %d = `%s` & `%s`)",
                FluidFormatString.format(inputFluid),
                churns,
                FluidFormatString.format(outputFluid),
                outputItem);
    }
}
