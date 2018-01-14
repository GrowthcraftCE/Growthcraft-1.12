package growthcraft.milk.api.processing.pancheon;

import growthcraft.core.api.fluids.FluidFormatString;
import net.minecraftforge.fluids.FluidStack;

public class PancheonRecipe
{
	private FluidStack inputFluid;
	private FluidStack topOutFluid;
	private FluidStack bottomOutFluid;
	// time in ticks
	private int time;

	public PancheonRecipe(FluidStack inputStack, FluidStack topOutput, FluidStack bottomOutput, int t)
	{
		this.inputFluid = inputStack;
		this.topOutFluid = topOutput;
		this.bottomOutFluid = bottomOutput;
		this.time = t;
	}

	public boolean isValidForRecipe(FluidStack stack)
	{
		if (stack == null) return false;
		if (!inputFluid.isFluidEqual(stack)) return false;
		if (stack.amount < inputFluid.amount) return false;
		return true;
	}

	public FluidStack getInputFluid()
	{
		return inputFluid;
	}

	public FluidStack getTopOutputFluid()
	{
		return topOutFluid;
	}

	public FluidStack getBottomOutputFluid()
	{
		return bottomOutFluid;
	}

	public int getTime()
	{
		return time;
	}

	public String toString()
	{
		return String.format("PancheonRecipe(`%s` / %d = `%s` & `%s`)", FluidFormatString.format(inputFluid), time, FluidFormatString.format(topOutFluid), FluidFormatString.format(bottomOutFluid));
	}
}
