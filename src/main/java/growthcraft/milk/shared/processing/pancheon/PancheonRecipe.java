package growthcraft.milk.shared.processing.pancheon;

import growthcraft.core.shared.fluids.FluidFormatString;
import net.minecraftforge.fluids.FluidStack;

public class PancheonRecipe implements IPancheonRecipe
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

	@Override
	public boolean isValidForRecipe(FluidStack stack)
	{
		if (stack == null) return false;
		if (!inputFluid.isFluidEqual(stack)) return false;
		if (stack.amount < inputFluid.amount) return false;
		return true;
	}

	@Override
	public FluidStack getInputFluid()
	{
		return inputFluid;
	}

	@Override
	public FluidStack getTopOutputFluid()
	{
		return topOutFluid;
	}

	@Override
	public FluidStack getBottomOutputFluid()
	{
		return bottomOutFluid;
	}

	@Override
	public int getTime()
	{
		return time;
	}

	@Override
	public String toString()
	{
		return String.format("PancheonRecipe(`%s` / %d = `%s` & `%s`)", FluidFormatString.format(inputFluid), time, FluidFormatString.format(topOutFluid), FluidFormatString.format(bottomOutFluid));
	}
}
