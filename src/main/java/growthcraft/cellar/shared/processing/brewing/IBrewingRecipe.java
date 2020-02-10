package growthcraft.cellar.shared.processing.brewing;

import growthcraft.cellar.shared.processing.common.IProcessingRecipe;
import growthcraft.core.shared.definition.IMultiItemStacks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public interface IBrewingRecipe extends IProcessingRecipe {
    IMultiItemStacks getInputItemStack();

    FluidStack getInputFluidStack();

    boolean matchesRecipe(@Nullable FluidStack fluidStack, @Nullable ItemStack itemStack, boolean requiresLid);

    boolean matchesIngredient(@Nullable FluidStack fluidStack);

    boolean matchesIngredient(@Nullable ItemStack stack);

    boolean isItemIngredient(@Nullable ItemStack stack);
}
