package growthcraft.bees.common.lib.config.user;

import java.util.ArrayList;
import java.util.List;

import growthcraft.core.shared.config.schema.ICommentable;

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
