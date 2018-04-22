package growthcraft.milk.shared.processing.pancheon;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.milk.GrowthcraftMilk;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class PancheonRegistry
{
	private Map<Fluid, IPancheonRecipe> recipes = new HashMap<Fluid, IPancheonRecipe>();

	public void addRecipe(@Nonnull IPancheonRecipe recipe)
	{
		final Fluid fluid = recipe.getInputFluid().getFluid();
		if (recipes.containsKey(fluid))
		{
			GrowthcraftMilk.logger.warn("Overwriting existing pancheon recipe for {%s} with {%s}", recipe.getInputFluid(), recipe);
		}
		else
		{
			GrowthcraftMilk.logger.debug("Adding new pancheon recipe {%s}", recipe);
		}
		recipes.put(fluid, recipe);
	}

	public void addRecipe(@Nonnull FluidStack inputStack, @Nonnull FluidStack topOutput, @Nullable FluidStack bottomOutput, int time)
	{
		final Fluid fluid = inputStack.getFluid();
		if (fluid == null)
		{
			throw new IllegalArgumentException("The provided input fluid is invalid.");
		}

		final PancheonRecipe recipe = new PancheonRecipe(inputStack, topOutput, bottomOutput, time);
		addRecipe(recipe);
	}

	@Nullable
	public IPancheonRecipe getRecipe(FluidStack stack)
	{
		if (stack == null) return null;
		final Fluid fluid = stack.getFluid();
		if (fluid == null) return null;
		final IPancheonRecipe recipe = recipes.get(fluid);
		if (recipe != null)
		{
			if (recipe.isValidForRecipe(stack)) return recipe;
		}
		return null;
	}
}
