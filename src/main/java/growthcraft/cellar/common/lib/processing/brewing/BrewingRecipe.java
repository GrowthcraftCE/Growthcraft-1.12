package growthcraft.cellar.common.lib.processing.brewing;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.cellar.common.lib.processing.common.ProcessingRecipe;
import growthcraft.cellar.common.lib.processing.common.Residue;
import growthcraft.core.shared.definition.IMultiItemStacks;
import growthcraft.core.shared.fluids.FluidTest;
import growthcraft.core.shared.item.ItemTest;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class BrewingRecipe extends ProcessingRecipe implements IBrewingRecipe {
	private IMultiItemStacks inputItemStack;
	private FluidStack inputFluidStack;

	public BrewingRecipe(@Nonnull FluidStack pInputFluid, @Nonnull IMultiItemStacks pInputItem,
			@Nonnull FluidStack pOutputFluid, int pTime, @Nullable Residue pResidue) {
		super(pOutputFluid, pTime, pResidue);
		this.inputItemStack = pInputItem;
		this.inputFluidStack = pInputFluid;
	}

	@Override
	public IMultiItemStacks getInputItemStack() {
		return inputItemStack;
	}

	@Override
	public FluidStack getInputFluidStack() {
		return inputFluidStack;
	}

	@Override
	public boolean matchesRecipe(@Nullable FluidStack fluidStack, @Nullable ItemStack itemStack) {
		if (fluidStack != null && itemStack != null) {
			if (!FluidTest.hasEnough(inputFluidStack, fluidStack))
				return false;
			if (!ItemTest.hasEnough(inputItemStack, itemStack))
				return false;
			return true;
		}
		return false;
	}

	@Override
	public boolean matchesIngredient(@Nullable FluidStack fluidStack) {
		return FluidTest.fluidMatches(inputFluidStack, fluidStack);
	}

	@Override
	public boolean matchesIngredient(@Nullable ItemStack stack) {
		return ItemTest.itemMatches(inputItemStack, stack);
	}

	@Override
	public boolean isItemIngredient(@Nullable ItemStack stack) {
		if (stack != null) {
			if (inputItemStack.containsItemStack(stack))
				return true;
		}
		return false;
	}
}
