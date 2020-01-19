package growthcraft.milk.shared.processing.churn;

import growthcraft.cellar.shared.processing.common.IProcessingRecipeBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IChurnRecipe extends IProcessingRecipeBase {

    boolean isValidForRecipe(FluidStack stack);

    FluidStack getInputFluidStack();

    FluidStack getOutputFluidStack();

    ItemStack getOutputItemStack();

    int getChurns();

}
