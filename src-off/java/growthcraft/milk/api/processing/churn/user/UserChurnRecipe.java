package growthcraft.milk.api.processing.churn.user;

import java.util.ArrayList;
import java.util.List;

import growthcraft.core.api.schema.FluidStackSchema;
import growthcraft.core.api.schema.ICommentable;
import growthcraft.core.api.schema.ItemKeySchema;
import growthcraft.milk.api.processing.churn.ChurnRecipe;
import growthcraft.milk.api.processing.churn.IChurnRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class UserChurnRecipe implements ICommentable
{
	public String comment = "";
	public FluidStackSchema input_fluid;
	public FluidStackSchema output_fluid;
	public ItemKeySchema output_item;
	public int churns;

	public UserChurnRecipe(FluidStackSchema pInputFluid, FluidStackSchema pOutputFluid, ItemKeySchema pOutputItem, int pChurns)
	{
		this.output_item = pOutputItem;
		this.input_fluid = pInputFluid;
		this.output_fluid = pOutputFluid;
		this.churns = pChurns;
	}

	public UserChurnRecipe() {}

	@Override
	public String toString()
	{
		return String.format("UserChurnRecipe(`%s` / %d = `%s` & `%s`)", input_fluid, churns, output_fluid, output_item);
	}

	@Override
	public void setComment(String comm)
	{
		this.comment = comm;
	}

	@Override
	public String getComment()
	{
		return comment;
	}

	public List<IChurnRecipe> toChurnRecipes()
	{
		final List<IChurnRecipe> recipes = new ArrayList<IChurnRecipe>();
		final FluidStack inputFluidStack = input_fluid.asFluidStack();
		final FluidStack outputFluidStack = output_fluid != null ? output_fluid.asFluidStack() : null;

		if (output_item != null && output_item.isValid())
		{
			for (ItemStack stack : output_item.getItemStacks())
			{
				recipes.add(new ChurnRecipe(inputFluidStack, outputFluidStack, stack, churns));
				// only the first item is used, everything is dropped.
				break;
			}
		}
		else
		{
			recipes.add(new ChurnRecipe(inputFluidStack, outputFluidStack, null, churns));
		}
		return recipes;
	}
}