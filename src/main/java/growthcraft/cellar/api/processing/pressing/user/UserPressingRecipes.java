package growthcraft.cellar.api.processing.pressing.user;

import java.util.ArrayList;
import java.util.List;

import growthcraft.core.shared.config.schema.ICommentable;

public class UserPressingRecipes implements ICommentable
{
	public String comment = "";
	public List<UserPressingRecipe> data = new ArrayList<UserPressingRecipe>();

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
