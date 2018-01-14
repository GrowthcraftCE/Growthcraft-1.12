package growthcraft.milk.api.processing.churn;

import growthcraft.core.api.fluids.FluidFormatString;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class ChurnRecipe
{
	private FluidStack inputFluid;
	private FluidStack outputFluid;
	private ItemStack outputItem;
	private int churns;

	public ChurnRecipe(FluidStack inFluid, FluidStack outFluid, ItemStack outItem, int ch)
	{
		this.inputFluid = inFluid;
		this.outputFluid = outFluid;
		this.outputItem = outItem;
		this.churns = ch;
	}

	public boolean isValidForRecipe(FluidStack stack)
	{
		if (stack == null) return false;
		if (!inputFluid.isFluidEqual(stack)) return false;
		if (stack.amount < inputFluid.amount) return false;
		return true;
	}

	public FluidStack getInputFluidStack()
	{
		return inputFluid;
	}

	public FluidStack getOutputFluidStack()
	{
		return outputFluid;
	}

	public ItemStack getOutputItemStack()
	{
		return outputItem;
	}

	public int getChurns()
	{
		return churns;
	}

	public String toString()
	{
		return String.format("ChurnRecipe(`%s` / %d = `%s` & `%s`)",
			FluidFormatString.format(inputFluid),
			churns,
			FluidFormatString.format(outputFluid),
			outputItem);
	}
}
