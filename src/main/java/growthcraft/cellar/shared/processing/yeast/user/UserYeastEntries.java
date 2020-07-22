package growthcraft.cellar.shared.processing.yeast.user;

import growthcraft.core.shared.config.schema.ICommentable;

import java.util.ArrayList;
import java.util.List;

public class UserYeastEntries implements ICommentable {
    public String comment = "";
    public List<UserYeastEntry> data = new ArrayList<UserYeastEntry>();

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String com) {
        this.comment = com;
    }
}
