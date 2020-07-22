package growthcraft.cellar.shared.processing.pressing.user;

import growthcraft.core.shared.config.schema.ICommentable;

import java.util.ArrayList;
import java.util.List;

public class UserPressingRecipes implements ICommentable {
    public String comment = "";
    public List<UserPressingRecipe> data = new ArrayList<UserPressingRecipe>();

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String com) {
        this.comment = com;
    }
}
