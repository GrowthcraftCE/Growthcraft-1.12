package growthcraft.cellar.shared.processing.fermenting;

import growthcraft.cellar.shared.processing.common.IProcessingRecipeBase;
import growthcraft.core.shared.definition.IMultiFluidStacks;
import growthcraft.core.shared.definition.IMultiItemStacks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public interface IFermentationRecipe extends IProcessingRecipeBase {

    IMultiFluidStacks getInputFluidStack();

    FluidStack getOutputFluidStack();

    IMultiItemStacks getFermentingItemStack();

    int getTime();

    boolean matchesRecipe(FluidStack fluidStack, ItemStack itemStack);

    boolean matchesIngredient(FluidStack fluidStack);

    boolean matchesIngredient(ItemStack stack);

    boolean isItemIngredient(@Nullable ItemStack stack);

}
