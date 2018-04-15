package growthcraft.cellar.common.lib.processing.fermenting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.shared.definition.IMultiFluidStacks;
import growthcraft.core.shared.definition.IMultiItemStacks;
import growthcraft.core.shared.fluids.FluidTest;
import growthcraft.core.shared.item.ItemTest;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class FermentationRecipe implements IFermentationRecipe
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

	@Override
	public IMultiFluidStacks getInputFluidStack()
	{
		return inputFluidStack;
	}

	@Override
	public FluidStack getOutputFluidStack()
	{
		return outputFluidStack;
	}

	@Override
	public IMultiItemStacks getFermentingItemStack()
	{
		return fermentingItem;
	}

	@Override
	public int getTime()
	{
		return time;
	}

	@Override
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

	@Override
	public boolean matchesIngredient(@Nullable FluidStack fluidStack)
	{
		return FluidTest.fluidMatches(inputFluidStack, fluidStack);
	}

	@Override
	public boolean matchesIngredient(@Nullable ItemStack stack)
	{
		return ItemTest.itemMatches(fermentingItem, stack);
	}
}
