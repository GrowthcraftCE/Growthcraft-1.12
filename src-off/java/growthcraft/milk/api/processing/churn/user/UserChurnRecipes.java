package growthcraft.milk.api.processing.churn.user;

import java.util.ArrayList;
import java.util.List;

import growthcraft.core.api.schema.ICommentable;

public class UserChurnRecipes implements ICommentable
{
	public String comment = "";
	public List<UserChurnRecipe> data = new ArrayList<UserChurnRecipe>();

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
