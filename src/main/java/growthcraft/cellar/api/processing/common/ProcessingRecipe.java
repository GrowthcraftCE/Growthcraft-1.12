package growthcraft.cellar.api.processing.common;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class ProcessingRecipe implements IProcessingRecipe {
	private final int time;
	private final Residue residue;
	private final FluidStack fluid;

	public ProcessingRecipe(FluidStack f, int t, Residue r)
	{
		this.fluid = f;
		this.time = t;
		this.residue = r;
	}

	@Override
	public Residue getResidue()
	{
		return residue;
	}

	@Override
	public boolean hasResidue()
	{
		return residue != null;
	}

	@Override
	public int getTime()
	{
		return time;
	}

	@Override
	public Fluid getFluid()
	{
		return fluid.getFluid();
	}

	@Override
	public FluidStack getFluidStack()
	{
		return fluid;
	}

	@Override
	public int getAmount()
	{
		return fluid.amount;
	}

	@Override
	public FluidStack asFluidStack(int size)
	{
		final FluidStack result = fluid.copy();
		result.amount = size;
		return result;
	}

	@Override
	public FluidStack asFluidStack()
	{
		return fluid.copy();
	}
}
