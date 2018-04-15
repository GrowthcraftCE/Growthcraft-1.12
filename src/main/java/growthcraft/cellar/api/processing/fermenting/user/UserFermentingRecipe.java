package growthcraft.cellar.api.processing.fermenting.user;

import javax.annotation.Nonnull;

import growthcraft.core.shared.config.schema.FluidStackSchema;
import growthcraft.core.shared.config.schema.ICommentable;
import growthcraft.core.shared.config.schema.ItemKeySchema;
import growthcraft.core.shared.config.schema.MultiFluidStackSchema;

public class UserFermentingRecipe implements ICommentable
{
	public String comment = "";
	public ItemKeySchema item;
	public MultiFluidStackSchema input_fluid;
	public FluidStackSchema output_fluid;
	public int time;

	public UserFermentingRecipe(@Nonnull ItemKeySchema itemSchema, @Nonnull MultiFluidStackSchema inp_fluid, @Nonnull FluidStackSchema out_fluid, int t)
	{
		this.item = itemSchema;
		this.input_fluid = inp_fluid;
		this.output_fluid = out_fluid;
		this.time = t;
	}

	public UserFermentingRecipe() {}

	@Override
	public String toString()
	{
		return String.format("UserFermentingRecipe((`%s` + `%s`) / %d = `%s`)", item, input_fluid, time, output_fluid);
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
}
