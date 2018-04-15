package growthcraft.cellar.api.processing.pressing.user;

import growthcraft.cellar.api.schema.ResidueSchema;
import growthcraft.core.shared.config.schema.FluidStackSchema;
import growthcraft.core.shared.config.schema.ICommentable;
import growthcraft.core.shared.config.schema.ItemKeySchema;

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