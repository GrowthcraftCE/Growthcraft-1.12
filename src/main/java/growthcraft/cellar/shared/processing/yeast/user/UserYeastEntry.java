package growthcraft.cellar.shared.processing.yeast.user;

import growthcraft.core.shared.config.schema.ICommentable;
import growthcraft.core.shared.config.schema.ItemKeySchema;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class UserYeastEntry implements ICommentable {
    public String comment = "";
    public ItemKeySchema item;
    public int weight;
    public List<String> biome_types;
    public List<String> biome_names;

    public UserYeastEntry(@Nonnull ItemKeySchema i, int w, @Nonnull List<String> biomeTypes, @Nonnull List<String> biomeNames) {
        this.item = i;
        this.weight = w;
        this.biome_types = biomeTypes;
        this.biome_names = biomeNames;
    }

    public UserYeastEntry(@Nonnull ItemKeySchema i, int w, @Nonnull List<String> biomeTypes) {
        this(i, w, biomeTypes, new ArrayList<String>());
    }

    public UserYeastEntry() {
    }

    @Override
    public String toString() {
        return String.format("UserYeastEntry(item: `%s`, weight: %d, biome_types: %s, biome_names: %s)", item, weight, biome_types, biome_names);
    }

    @Override
    public void setComment(String comm) {
        this.comment = comm;
    }

    @Override
    public String getComment() {
        return comment;
    }
}
