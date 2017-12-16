package growthcraft.cellar.api.processing.culturing.user;

import growthcraft.core.api.schema.FluidStackSchema;
import growthcraft.core.api.schema.ICommentable;
import growthcraft.core.api.schema.ItemKeySchema;

public class UserCultureRecipe implements ICommentable
{
	public String comment = "";
	public ItemKeySchema output_item;
	public FluidStackSchema input_fluid;
	public float required_heat;
	public int time;

	public UserCultureRecipe(FluidStackSchema inp, ItemKeySchema out, float heat, int tm)
	{
		this.input_fluid = inp;
		this.output_item = out;
		this.required_heat = heat;
		this.time = tm;
	}

	public UserCultureRecipe() {}

	@Override
	public String toString()
	{
		return String.format("UserCultureRecipe(`%s` + %f / %d = `%s`)", input_fluid, required_heat, time, output_item);
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