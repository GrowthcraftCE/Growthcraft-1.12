package growthcraft.cellar.api.processing.pressing.user;

import growthcraft.core.api.schema.FluidStackSchema;
import growthcraft.core.api.schema.ICommentable;
import growthcraft.core.api.schema.ItemKeySchema;
import growthcraft.core.api.schema.ResidueSchema;

public class UserPressingRecipe implements ICommentable
{
	public String comment = "";
	public ItemKeySchema item;
	public FluidStackSchema fluid;
	public ResidueSchema residue;
	public int time;

	public UserPressingRecipe(ItemKeySchema itm, FluidStackSchema fl, int tm, ResidueSchema res)
	{
		this.item = itm;
		this.fluid = fl;
		this.time = tm;
		this.residue = res;
	}

	public UserPressingRecipe() {}

	@Override
	public String toString()
	{
		return String.format("UserPressingRecipe(`%s` / %d = `%s` & `%s`)", item, time, fluid, residue);
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