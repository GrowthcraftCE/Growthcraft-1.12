package growthcraft.bees.common.lib.config.user;

import growthcraft.core.shared.config.schema.ICommentable;

import java.util.ArrayList;
import java.util.List;

public class UserFlowersEntries implements ICommentable {
    public String comment = "";
    public List<UserFlowerEntry> data = new ArrayList<UserFlowerEntry>();

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String com) {
        this.comment = com;
    }
}
