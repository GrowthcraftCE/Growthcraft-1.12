package growthcraft.cellar.shared.processing.brewing.user;

import growthcraft.cellar.shared.config.schema.ResidueSchema;
import growthcraft.core.shared.config.schema.FluidStackSchema;
import growthcraft.core.shared.config.schema.ICommentable;
import growthcraft.core.shared.config.schema.ItemKeySchema;

public class UserBrewingRecipe implements ICommentable
{
	public String comment = "";
	public ItemKeySchema item;
	public FluidStackSchema input_fluid;
	public FluidStackSchema output_fluid;
	public ResidueSchema residue;
	public Boolean requiresLid;
	public int time;

	public UserBrewingRecipe(ItemKeySchema itm, FluidStackSchema inp, FluidStackSchema out, Boolean requiresLid, ResidueSchema res, int tm)
	{
		this.item = itm;
		this.input_fluid = inp;
		this.output_fluid = out;
		this.residue = res;
		this.requiresLid = requiresLid;
		this.time = tm;
	}

	public UserBrewingRecipe() {}

	@Override
	public String toString()
	{
		return String.format("UserBrewingRecipe(`%s` + `%s` / %d = `%s` & `%s` # `%s`)", item, input_fluid, time, output_fluid, residue, requiresLid?"Lid":"NoLid");
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
