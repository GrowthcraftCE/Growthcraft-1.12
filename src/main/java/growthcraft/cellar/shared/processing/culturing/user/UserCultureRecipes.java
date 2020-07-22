package growthcraft.cellar.shared.processing.culturing.user;

import growthcraft.core.shared.config.schema.ICommentable;

import java.util.ArrayList;
import java.util.List;

public class UserCultureRecipes implements ICommentable {
    public String comment = "";
    public List<UserCultureRecipe> data = new ArrayList<UserCultureRecipe>();

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String com) {
        this.comment = com;
    }
}
