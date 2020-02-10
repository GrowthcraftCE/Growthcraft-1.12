package growthcraft.cellar.shared.processing.brewing.user;

import growthcraft.core.shared.config.schema.ICommentable;

import java.util.ArrayList;
import java.util.List;

public class UserBrewingRecipes implements ICommentable {
    public String comment = "";
    public List<UserBrewingRecipe> data = new ArrayList<UserBrewingRecipe>();

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String com) {
        this.comment = com;
    }
}
