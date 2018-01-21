package growthcraft.cellar.api.processing.fermenting;

import growthcraft.core.api.definition.IMultiFluidStacks;
import growthcraft.core.api.definition.IMultiItemStacks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IFermentationRecipe {

	IMultiFluidStacks getInputFluidStack();
	FluidStack getOutputFluidStack();
	IMultiItemStacks getFermentingItemStack();
	int getTime();
	boolean matchesRecipe(FluidStack fluidStack, ItemStack itemStack);
	boolean matchesIngredient(FluidStack fluidStack);
	boolean matchesIngredient(ItemStack stack);

}
