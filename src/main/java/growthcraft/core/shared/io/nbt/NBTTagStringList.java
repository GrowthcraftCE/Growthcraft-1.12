package growthcraft.core.shared.io.nbt;

import java.util.Collection;

import javax.annotation.Nonnull;

import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

/**
 * Utility class for wrapping a NBTTagList for Strings
 */
public class NBTTagStringList {
    private NBTTagList parent;

    /**
     * Pass an existing String list, or an empty one in
     *
     * @param list - the tag list to use as the parent
     */
    public NBTTagStringList(@Nonnull NBTTagList list) {
        this.parent = list;
    }

    /**
     * Initializes the NBTTagStringList with a default taglist
     */
    public NBTTagStringList() {
        this(new NBTTagList());
    }

    /**
     * @param list - a string list
     */
    public NBTTagStringList(@Nonnull Collection<?> list) {
        this();
        for (Object obj : list) {
            add(obj.toString());
        }
    }

    /**
     * How many tags are present?
     *
     * @return number of tags
     */
    public int size() {
        return parent.tagCount();
    }

    /**
     * Adds a string to the existing list
     *
     * @param str - the string to add
     */
    public NBTTagStringList add(@Nonnull String str) {
        parent.appendTag(new NBTTagString(str));
        return this;
    }

    /**
     * Returns the String present at the given index
     *
     * @param index - index of the string wanted
     * @return the string at the index
     */
    public String get(int index) {
        return parent.getStringTagAt(index);
    }

    /**
     * Returns the parent NBTTagList
     *
     * @return parent list
     */
    public NBTTagList getTag() {
        return parent;
    }
}
