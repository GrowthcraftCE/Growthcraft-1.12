package growthcraft.milk.shared.processing.cheesevat;

import java.util.List;

import growthcraft.core.shared.definition.IMultiFluidStacks;
import growthcraft.core.shared.definition.IMultiItemStacks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public interface ICheeseVatRecipe {

    List<FluidStack> getOutputFluidStacks();

    List<ItemStack> getOutputItemStacks();

    List<IMultiFluidStacks> getInputFluidStacks();

    List<IMultiItemStacks> getInputItemStacks();

    boolean isMatchingRecipe(List<FluidStack> fluids, List<ItemStack> items);

    boolean isFluidIngredient(Fluid fluid);

    boolean isFluidIngredient(FluidStack fluidStack);

    boolean isItemIngredient(ItemStack itemStack);

}
