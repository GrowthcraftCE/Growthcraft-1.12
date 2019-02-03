package growthcraft.cellar.shared.processing.fermenting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.shared.definition.IMultiFluidStacks;
import growthcraft.core.shared.definition.IMultiItemStacks;
import growthcraft.core.shared.fluids.FluidTest;
import growthcraft.core.shared.item.ItemTest;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class FermentationRecipe extends FermentationFallbackRecipe
{
	private final IMultiItemStacks fermentingItem;

	public FermentationRecipe(@Nonnull IMultiFluidStacks pInputFluidStack, @Nonnull IMultiItemStacks pFermentingItem, @Nonnull FluidStack pOutputFluidStack, int pTime)
	{
		super(pInputFluidStack, pOutputFluidStack, pTime);
		this.fermentingItem = pFermentingItem;
	}
	
	@Override
	public IMultiItemStacks getFermentingItemStack()
	{
		return fermentingItem;
	}


	@Override
	public boolean matchesRecipe(@Nullable FluidStack fluidStack, @Nullable ItemStack itemStack)
	{
		if (FluidTest.isValid(fluidStack) && ItemTest.isValid(itemStack))
		{
			if (FluidTest.hasEnough(getInputFluidStack(), fluidStack))
			{
				return ItemTest.hasEnough(fermentingItem, itemStack);
			}
		}
		return false;
	}

	@Override
	public boolean matchesIngredient(@Nullable FluidStack fluidStack)
	{
		return FluidTest.fluidMatches(getInputFluidStack(), fluidStack);
	}

	@Override
	public boolean matchesIngredient(@Nullable ItemStack stack)
	{
		return ItemTest.itemMatches(fermentingItem, stack);
	}

	@Override
	public boolean isItemIngredient(ItemStack stack) {
		if (stack != null) {
			if (fermentingItem.containsItemStack(stack))
				return true;
		}
		return false;
	}
	
	
}
