package growthcraft.core.shared.config.schema;

public class RangeSchema implements ICommentable {
    public String comment = "";
    public int min;
    public int max;

    public RangeSchema(int n, int x) {
        this.min = n;
        this.max = x;
    }

    public RangeSchema() {
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String com) {
        this.comment = com;
    }
}
