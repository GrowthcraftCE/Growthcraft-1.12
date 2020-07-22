package growthcraft.milk.shared.processing.cheesevat;

import growthcraft.cellar.shared.processing.common.IProcessingRecipeBase;
import growthcraft.core.shared.definition.IMultiFluidStacks;
import growthcraft.core.shared.definition.IMultiItemStacks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public interface ICheeseVatRecipe extends IProcessingRecipeBase {

    List<FluidStack> getOutputFluidStacks();

    List<ItemStack> getOutputItemStacks();

    List<IMultiFluidStacks> getInputFluidStacks();

    List<IMultiItemStacks> getInputItemStacks();

    boolean isMatchingRecipe(List<FluidStack> fluids, List<ItemStack> items);

    boolean isFluidIngredient(Fluid fluid);

    boolean isFluidIngredient(FluidStack fluidStack);

    boolean isItemIngredient(ItemStack itemStack);

}
