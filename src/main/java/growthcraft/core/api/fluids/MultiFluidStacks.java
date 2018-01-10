package growthcraft.core.api.fluids;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.api.definition.IMultiFluidStacks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class MultiFluidStacks implements IMultiFluidStacks
{
	private List<FluidStack> fluidStacks;
	private transient List<ItemStack> fluidContainers;

	public MultiFluidStacks(@Nonnull List<FluidStack> stacks)
	{
		this.fluidStacks = stacks;
	}

	public MultiFluidStacks(@Nonnull FluidStack... stacks)
	{
		this(Arrays.asList(stacks));
	}

	public List<String> getNames()
	{
		final List<String> result = new ArrayList<String>();
		for (FluidStack stack : fluidStacks)
		{
			final Fluid fluid = stack.getFluid();
			if (fluid != null)
			{
				result.add(fluid.getName());
			}
		}
		return result;
	}

	@Override
	public int getAmount()
	{
		for (FluidStack stack : fluidStacks)
		{
			return stack.amount;
		}
		return 0;
	}

	@Override
	public List<FluidStack> getFluidStacks()
	{
		return fluidStacks;
	}

	@Override
	public boolean containsFluid(@Nullable Fluid expectedFluid)
	{
		if (FluidTest.isValid(expectedFluid))
		{
			for (FluidStack content : getFluidStacks())
			{
				if (content.getFluid() == expectedFluid) return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsFluidStack(@Nullable FluidStack stack)
	{
		if (!FluidTest.isValid(stack)) return false;
		for (FluidStack content : getFluidStacks())
		{
			if (content.isFluidEqual(stack)) return true;
		}
		return false;
	}

	@Override
	public List<ItemStack> getItemStacks()
	{
		if (fluidContainers == null)
		{
			fluidContainers = FluidUtils.getFluidContainers(getFluidStacks());
		}

		return fluidContainers;
	}
}
