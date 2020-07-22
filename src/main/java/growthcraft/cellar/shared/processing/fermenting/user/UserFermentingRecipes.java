package growthcraft.cellar.shared.processing.fermenting.user;

import growthcraft.core.shared.config.schema.ICommentable;

import java.util.ArrayList;
import java.util.List;

public class UserFermentingRecipes implements ICommentable {
    public String comment = "";
    public List<UserFermentingRecipe> data = new ArrayList<UserFermentingRecipe>();

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String com) {
        this.comment = com;
    }
}
