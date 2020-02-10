package growthcraft.cellar.shared.processing.heatsource.user;

import growthcraft.core.shared.config.schema.ICommentable;

import java.util.ArrayList;
import java.util.List;

public class UserHeatSourceEntries implements ICommentable {
    public String comment = "";
    public List<UserHeatSourceEntry> data = new ArrayList<UserHeatSourceEntry>();

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String com) {
        this.comment = com;
    }
}
