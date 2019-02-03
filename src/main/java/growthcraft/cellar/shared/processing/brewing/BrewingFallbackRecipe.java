package growthcraft.cellar.shared.processing.brewing;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.cellar.shared.processing.common.ProcessingRecipe;
import growthcraft.cellar.shared.processing.common.Residue;
import growthcraft.core.shared.definition.IMultiItemStacks;
import growthcraft.core.shared.fluids.FluidTest;
import growthcraft.core.shared.item.MultiItemStacks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class BrewingFallbackRecipe extends ProcessingRecipe implements IBrewingRecipe {
	private FluidStack inputFluidStack;

	public BrewingFallbackRecipe(@Nonnull FluidStack pInputFluid, 
			@Nonnull FluidStack pOutputFluid, int pTime, @Nullable Residue pResidue) {
		super(pOutputFluid, pTime, pResidue);
		this.inputFluidStack = pInputFluid;
	}

	@Override
	public IMultiItemStacks getInputItemStack() {
		return MultiItemStacks.EMPTY;
	}

	@Override
	public FluidStack getInputFluidStack() {
		return inputFluidStack;
	}

	@Override
	public boolean matchesRecipe(@Nullable FluidStack fluidStack, @Nullable ItemStack itemStack, boolean requiresLid) {
		if (fluidStack != null && itemStack != null) {
			if (!FluidTest.hasEnough(inputFluidStack, fluidStack))
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
		return true;
	}

	@Override
	public boolean isItemIngredient(@Nullable ItemStack stack) {
		return true;
	}
}
