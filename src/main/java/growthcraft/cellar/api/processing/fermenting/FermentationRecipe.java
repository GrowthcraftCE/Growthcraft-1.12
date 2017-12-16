package growthcraft.cellar.api.processing.fermenting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.api.definition.IMultiFluidStacks;
import growthcraft.core.api.definition.IMultiItemStacks;
import growthcraft.core.api.fluids.FluidTest;
import growthcraft.core.api.item.ItemTest;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class FermentationRecipe
{
	private final IMultiItemStacks fermentingItem;
	private final IMultiFluidStacks inputFluidStack;
	private final FluidStack outputFluidStack;
	private final int time;

	public FermentationRecipe(@Nonnull IMultiFluidStacks pInputFluidStack, @Nonnull IMultiItemStacks pFermentingItem, @Nonnull FluidStack pOutputFluidStack, int pTime)
	{
		this.fermentingItem = pFermentingItem;
		this.inputFluidStack = pInputFluidStack;
		this.outputFluidStack = pOutputFluidStack;
		this.time = pTime;
	}

	public IMultiFluidStacks getInputFluidStack()
	{
		return inputFluidStack;
	}

	public FluidStack getOutputFluidStack()
	{
		return outputFluidStack;
	}

	public IMultiItemStacks getFermentingItemStack()
	{
		return fermentingItem;
	}

	public int getTime()
	{
		return time;
	}

	public boolean matchesRecipe(@Nullable FluidStack fluidStack, @Nullable ItemStack itemStack)
	{
		if (FluidTest.isValid(fluidStack) && ItemTest.isValid(itemStack))
		{
			if (FluidTest.hasEnough(inputFluidStack, fluidStack))
			{
				return ItemTest.hasEnough(fermentingItem, itemStack);
			}
		}
		return false;
	}

	public boolean matchesIngredient(@Nullable FluidStack fluidStack)
	{
		return FluidTest.fluidMatches(inputFluidStack, fluidStack);
	}

	public boolean matchesIngredient(@Nullable ItemStack stack)
	{
		return ItemTest.itemMatches(fermentingItem, stack);
	}
}
