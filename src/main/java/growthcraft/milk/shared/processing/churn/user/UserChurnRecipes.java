package growthcraft.milk.shared.processing.churn.user;

import growthcraft.core.shared.config.schema.ICommentable;

import java.util.ArrayList;
import java.util.List;

public class UserChurnRecipes implements ICommentable {
    public String comment = "";
    public List<UserChurnRecipe> data = new ArrayList<UserChurnRecipe>();

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String com) {
        this.comment = com;
    }
}
