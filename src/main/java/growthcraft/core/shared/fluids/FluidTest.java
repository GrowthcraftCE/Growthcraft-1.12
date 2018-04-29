package growthcraft.core.shared.fluids;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.shared.CoreRegistry;
import growthcraft.core.shared.definition.IMultiFluidStacks;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluidTest {
	private FluidTest() {}

	/**
	 * Determines if a Fluid is invalid, this is a very simple null check.
	 *
	 * @param fluid - the fluid to check
	 * @return true, if the fluid is valid, false otherwise
	 */
	public static boolean isValid(@Nullable Fluid fluid)
	{
		return fluid != null;
	}

	/**
	 * Determines if a FluidStack is valid by checking:
	 *   if the stack is null, it is invalid
	 *   if the stack has a null fluid, it is invalid
	 *   if the stack has an amount less than or equal to 0, it is invalid
	 *
	 * @param stack - the fluid stack to check
	 * @return true, the stack is valid, false otherwise
	 */
	public static boolean isValid(@Nullable FluidStack stack)
	{
		if (stack == null) return false;
		if (stack.getFluid() == null) return false;
		if (stack.amount <= 0) return false;
		return true;
	}

	public static boolean hasTags(@Nullable Fluid fluid, FluidTag... tags)
	{
		if (FluidTest.isValid(fluid))
		{
			return CoreRegistry.instance().fluidDictionary().hasFluidTags(fluid, tags);
		}
		return false;
	}

	public static boolean hasTags(@Nullable FluidStack stack, FluidTag... tags)
	{
		if (FluidTest.isValid(stack))
		{
			return CoreRegistry.instance().fluidDictionary().hasFluidTags(stack.getFluid(), tags);
		}
		return false;
	}

	public static boolean areStacksEqual(@Nullable FluidStack expected, @Nullable FluidStack other)
	{
		if (expected == null)
		{
			return other == null;
		}
		else
		{
			if (other == null) return false;
			return expected.isFluidEqual(other);
		}
	}

	public static boolean fluidMatches(@Nullable FluidStack expected, @Nullable Fluid other)
	{
		if (expected == null)
		{
			return other == null;
		}
		return expected.getFluid() == other;
	}

	public static boolean fluidMatches(@Nullable FluidStack expected, @Nullable FluidStack other)
	{
		return areStacksEqual(expected, other);
	}

	public static boolean fluidMatches(@Nullable IMultiFluidStacks expected, @Nullable FluidStack other)
	{
		if (expected == null)
		{
			return other == null;
		}
		else
		{
			if (other == null) return false;
			return expected.containsFluidStack(other);
		}
	}

	public static boolean hasEnough(@Nullable FluidStack expected, @Nullable FluidStack actual)
	{
		if (expected == null) return actual == null;
		if (actual == null) return false;
		if (!expected.isFluidEqual(actual)) return false;
		if (actual.amount < expected.amount) return false;
		return true;
	}

	public static boolean hasEnough(@Nullable IMultiFluidStacks expected, @Nullable FluidStack actual)
	{
		if (expected == null) return actual == null;
		if (actual == null) return false;
		if (!expected.containsFluidStack(actual)) return false;
		if (actual.amount < expected.getAmount()) return false;
		return true;
	}

	public static boolean isValidAndExpected(@Nonnull FluidStack expected, @Nullable FluidStack stack)
	{
		if (isValid(stack))
		{
			if (expected.isFluidEqual(stack)) return true;
		}
		return false;
	}

	public static boolean isValidAndExpected(@Nonnull Fluid expected, @Nullable FluidStack stack)
	{
		if (isValid(stack))
		{
			if (stack.getFluid() == expected) return true;
		}
		return false;
	}

	@SuppressWarnings({"rawtypes"})
	public static boolean isValidAndExpected(@Nonnull List expectedFluids, @Nonnull List<FluidStack> givenFluids)
	{
		if (expectedFluids.size() != givenFluids.size()) return false;
		for (int i = 0; i < expectedFluids.size(); ++i)
		{
			final Object expected = expectedFluids.get(i);
			final FluidStack actual = givenFluids.get(i);
			if (expected != null)
			{
				if (!isValid(actual)) return false;
				if (expected instanceof IMultiFluidStacks)
				{
					if (!((IMultiFluidStacks)expected).containsFluidStack(actual)) return false;
				}
				else if (expected instanceof FluidStack)
				{
					if (!((FluidStack)expected).isFluidEqual(actual)) return false;
				}
				else
				{
					return false;
				}
			}
			else
			{
				if (actual != null) return false;
			}
		}
		return true;
	}

	@SuppressWarnings({"rawtypes"})
	public static boolean hasEnoughAndExpected(@Nonnull List expectedFluids, @Nonnull List<FluidStack> givenFluids)
	{
		if (expectedFluids.size() != givenFluids.size()) return false;
		for (int i = 0; i < expectedFluids.size(); ++i)
		{
			final Object expected = expectedFluids.get(i);
			final FluidStack actual = givenFluids.get(i);
			if (expected != null)
			{
				if (!isValid(actual)) return false;
				if (expected instanceof IMultiFluidStacks)
				{
					if (!hasEnough((IMultiFluidStacks)expected, actual)) return false;
				}
				else if (expected instanceof FluidStack)
				{
					if (!hasEnough((FluidStack)expected, actual)) return false;
				}
				else
				{
					return false;
				}
			}
			else
			{
				if (actual != null) return false;
			}
		}
		return true;
	}
}
