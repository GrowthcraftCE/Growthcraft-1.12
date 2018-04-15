package growthcraft.cellar.api.processing.fermenting.user;

import java.util.ArrayList;
import java.util.List;

import growthcraft.core.api.schema.ICommentable;

public class UserFermentingRecipes implements ICommentable
{
	public String comment = "";
	public List<UserFermentingRecipe> data = new ArrayList<UserFermentingRecipe>();

	@Override
	public String getComment()
	{
		return comment;
	}

	@Override
	public void setComment(String com)
	{
		this.comment = com;
	}
}
