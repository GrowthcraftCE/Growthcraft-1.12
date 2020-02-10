package growthcraft.bees.common.lib.config.user;

import growthcraft.core.shared.config.schema.ICommentable;

import java.util.ArrayList;
import java.util.List;

public class UserBeesEntries implements ICommentable {
    public String comment = "";
    public List<UserBeeEntry> data = new ArrayList<UserBeeEntry>();

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String com) {
        this.comment = com;
    }
}
