package growthcraft.core.api.schema;

import javax.annotation.Nonnull;

import growthcraft.core.api.definition.IFluidStackFactory;
import growthcraft.core.api.utils.StringUtils;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FluidStackSchema implements IFluidStackFactory, IValidatable, ICommentable
{
	public String comment;
	public String name;
	public int amount;

	public FluidStackSchema(@Nonnull String nm, int amt)
	{
		this.name = nm;
		this.amount = amt;
		this.comment = "";
	}

	public FluidStackSchema(@Nonnull FluidStack stack)
	{
		this.name = stack.getFluid().getName();
		this.amount = stack.amount;
		this.comment = stack.getLocalizedName();
	}

	public FluidStackSchema()
	{
		this.comment = "";
		this.amount = 1;
	}

	@Override
	public void setComment(@Nonnull String comm)
	{
		this.comment = comm;
	}

	@Override
	public String getComment()
	{
		return comment;
	}

	public Fluid getFluid()
	{
		return FluidRegistry.getFluid(name);
	}

	@Override
	public FluidStack asFluidStack(int a)
	{
		final Fluid fluid = getFluid();
		if (fluid == null) return null;
		return new FluidStack(fluid, a);
	}

	@Override
	public FluidStack asFluidStack()
	{
		return asFluidStack(amount);
	}

	@Override
	public String toString()
	{
		return String.format("Schema<FluidStack>(comment: '%s', name: '%s', amount: %d)",
			StringUtils.inspect(comment), name, amount);
	}

	@Override
	public boolean isValid()
	{
		return asFluidStack() != null;
	}

	@Override
	public boolean isInvalid()
	{
		return !isValid();
	}
}
