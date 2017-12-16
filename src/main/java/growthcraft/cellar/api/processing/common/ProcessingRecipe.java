package growthcraft.cellar.api.processing.common;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class ProcessingRecipe {
	private final int time;
	private final Residue residue;
	private final FluidStack fluid;

	public ProcessingRecipe(FluidStack f, int t, Residue r)
	{
		this.fluid = f;
		this.time = t;
		this.residue = r;
	}

	public Residue getResidue()
	{
		return residue;
	}

	public boolean hasResidue()
	{
		return residue != null;
	}

	public int getTime()
	{
		return time;
	}

	public Fluid getFluid()
	{
		return fluid.getFluid();
	}

	public FluidStack getFluidStack()
	{
		return fluid;
	}

	public int getAmount()
	{
		return fluid.amount;
	}

	public FluidStack asFluidStack(int size)
	{
		final FluidStack result = fluid.copy();
		result.amount = size;
		return result;
	}

	public FluidStack asFluidStack()
	{
		return fluid.copy();
	}
}
